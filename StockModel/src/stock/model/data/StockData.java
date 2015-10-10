package stock.model.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.nebula.visualization.xygraph.dataprovider.CircularBufferDataProvider;
import org.eclipse.nebula.visualization.xygraph.dataprovider.IDataProvider;

import simugen.core.stats.StatUtil;

public class StockData
{
	private final Map<Long, List<Double>> $map = new LinkedHashMap<>();

	private final Map<Long, List<Double>> $mapGrowth = new LinkedHashMap<>();

	private Date current;

	private final Date start;
	
	private final double initialValue;
	
	private double currentValue;

	public StockData(Date start, double initialValue)
	{
		this.start = new Date(start.getTime());

		this.current = new Date(start.getTime());
		
		this.initialValue = initialValue;
		
		this.currentValue = initialValue;
	}

	public void reset()
	{
		current.setTime(start.getTime());
		
		currentValue = initialValue;
	}

	public void addGrowth(double growth)
	{
		currentValue += (growth * currentValue);
		
		addDataPoint(current, currentValue, growth);

		current.setTime(current.getTime() + 86400000L);
	}

	private void addDataPoint(Date date, double value, double growth)
	{
		if (!$map.containsKey(date.getTime()))
		{
			$map.put(date.getTime(), new ArrayList<Double>());
			$mapGrowth.put(date.getTime(), new ArrayList<Double>());
		}

		$map.get(date.getTime()).add(value);
		$mapGrowth.get(date.getTime()).add(growth);
	}

	public IDataProvider getMinBuffer()
	{
		CircularBufferDataProvider buffer = new CircularBufferDataProvider(
				true);

		buffer.setBufferSize($map.size());

		buffer.setXAxisDateEnabled(true);

		buffer.setConcatenate_data(true);

		Set<Long> dates = $map.keySet();

		for (Long date : dates)
		{
			double val = Collections.min($map.get(date));

			buffer.setCurrentYData(val, date);
		}

		return buffer;
	}

	public IDataProvider getMaximumBuffer()
	{
		CircularBufferDataProvider buffer = new CircularBufferDataProvider(
				true);

		buffer.setBufferSize($map.size());

		buffer.setXAxisDateEnabled(true);

		buffer.setConcatenate_data(true);

		Set<Long> dates = $map.keySet();

		for (Long date : dates)
		{
			double val = Collections.max($map.get(date));

			buffer.setCurrentYData(val, date);
		}

		return buffer;
	}

	public IDataProvider getMedianBuffer()
	{
		CircularBufferDataProvider buffer = new CircularBufferDataProvider(
				true);

		buffer.setBufferSize($map.size());

		buffer.setXAxisDateEnabled(true);

		buffer.setConcatenate_data(true);

		Set<Long> dates = $map.keySet();

		for (Long date : dates)
		{
			double val = getMedian($map.get(date));

			buffer.setCurrentYData(val, date);
		}

		return buffer;
	}

	private double getMedian(List<Double> list)
	{
		if (list.size() % 2 == 1)
		{
			return list.get(list.size() / 2);
		}

		int index1 = list.size() / 2;

		int index2 = index1 - 1;

		double val1 = list.get(index1);

		double val2 = list.get(index2);

		return ((val1 + val2) / 2);
	}

	public double getMeanDailyGrowth(int day)
	{
		Long dateList[] = $mapGrowth.keySet()
				.toArray(new Long[$mapGrowth.keySet().size()]);

		Long date = dateList[day];

		Double values[] = $mapGrowth.get(date)
				.toArray(new Double[$mapGrowth.get(date).size()]);

		return StatUtil.getAverage(ArrayUtils.toPrimitive(values));
	}

	public double getTotalMeanDailyGrowth()
	{
		double acrossDate[] = new double[$mapGrowth.keySet().size()];

		Long dateList[] = $mapGrowth.keySet()
				.toArray(new Long[$mapGrowth.keySet().size()]);

		for (int i = 0; i < $mapGrowth.keySet().size(); i++)
		{
			Long l = dateList[i];

			Double values[] = $mapGrowth.get(l)
					.toArray(new Double[$mapGrowth.get(l).size()]);

			acrossDate[i] = StatUtil.getAverage(ArrayUtils.toPrimitive(values));
		}

		return StatUtil.getAverage(acrossDate);
	}
}
