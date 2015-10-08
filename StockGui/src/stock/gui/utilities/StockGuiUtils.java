package stock.gui.utilities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import simugen.core.rng.EmpiricalGenerator;
import stock.scraper.builder.StockCompany;

public class StockGuiUtils
{
	public static EmpiricalGenerator createDistribution(
			Map<Date, BigDecimal> historical)
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
			EmpiricalGenerator generator, Map<Date, BigDecimal> percentages,
			Entry<Date, BigDecimal> newEntry)
	{
		int index = percentages.entrySet().size();

		int lastIndex = index - 1;

		Date lastDate = percentages.keySet().toArray(new Date[0])[lastIndex];

		Date nuwDate = newEntry.getKey();

		BigDecimal last = company.getHistorical().get(lastDate);

		BigDecimal nuw = newEntry.getValue();

		BigDecimal percentChange = nuw.subtract(last).divide(last,
				RoundingMode.HALF_UP);

		percentages.put(nuwDate, percentChange);

		generator.addValue(percentChange.doubleValue());
	}
}
