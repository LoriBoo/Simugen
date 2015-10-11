package stock.gui.utilities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.math.MathException;

import simugen.core.defaults.DefaultSimEngine;
import simugen.core.interfaces.LoggingStyle;
import simugen.core.interfaces.SimEngine;
import simugen.core.interfaces.SimModel;
import simugen.core.rng.EmpiricalGenerator;
import simugen.core.stats.StatUtil;
import stock.model.StockModel;
import stock.model.components.StockEventListener;
import stock.model.data.StockData;
import stock.scraper.builder.StockCompany;

public class StockGuiUtils
{
	public static EmpiricalGenerator createDistribution(
			Map<Long, BigDecimal> historical)
	{
		EmpiricalGenerator generator = new EmpiricalGenerator();

		for (BigDecimal dec : historical.values())
		{
			generator.addValue(dec.doubleValue());
		}

		generator.computeProbabilities();

		return generator;
	}

	/**
	 * This method takes a new value <b>newEntry</b> and does the following:<br>
	 * <br>
	 * Computes the percentage change from the previous date to the current date
	 * <br>
	 * Adds the percent change to the map <b>percentages</b><br>
	 * <br>
	 * Adds the percent change as a double to the EmpiricalGenerator,
	 * <b>generator</b>
	 * 
	 * @param company
	 * @param generator
	 * @param percentages
	 * @param newEntry
	 */
	public static void addEntry(StockCompany company,
			EmpiricalGenerator generator, Map<Long, BigDecimal> percentages,
			Entry<Long, BigDecimal> newEntry)
	{
		int index = percentages.entrySet().size();

		int lastIndex = index - 1;

		Date lastDate = new Date(
				percentages.keySet().toArray(new Long[0])[lastIndex]);

		Date nuwDate = new Date(newEntry.getKey());

		BigDecimal last = company.getHistorical().get(lastDate);

		BigDecimal nuw = newEntry.getValue();

		BigDecimal percentChange = nuw.subtract(last).divide(last,
				RoundingMode.HALF_UP);

		percentages.put(nuwDate.getTime(), percentChange);

		generator.addValue(percentChange.doubleValue());
	}

	public static int numberOfReps(StockModel model, double startValue, int samples, int days,
			int maxRuns, double confidence) throws MathException
	{
		SimEngine engine;

		Date start = Calendar.getInstance().getTime();

		StockData data = new StockData(start, startValue);
		
		model.setComputations(days);
		
		StockEventListener listener = new StockEventListener(data);

		for (int i = 0; i < samples; i++)
		{
			data.reset();
			
			SimModel internal = model.getCopy();

			engine = new DefaultSimEngine();

			engine.setLoggingStyle(LoggingStyle.SUPRESS);

			engine.setRuns(samples);

			engine.addEventListener(listener);

			engine.setModel(internal);

			engine.start();
		}

		double x[] = new double[days];

		for (int i = 0; i < days-1; i++)
		{
			x[i] = data.getMeanDailyGrowth(i);
		}

		double xBar = data.getTotalMeanDailyGrowth();

		double sumSquares = 0;

		for (double xi : x)
		{
			sumSquares += Math.pow(xi - xBar, 2);
		}

		double s = Math.sqrt((1d / ((double) (x.length - 1))) * sumSquares);

		return StatUtil.numberReps(confidence, s, maxRuns);
	}
}
