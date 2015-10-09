package stock.model.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.nebula.visualization.xygraph.dataprovider.CircularBufferDataProvider;
import org.eclipse.nebula.visualization.xygraph.dataprovider.IDataProvider;

public class StockData
{
	private final Map<Long, List<Double>> $map = new LinkedHashMap<>();

	private Date current;

	private final Date start;

	public StockData(Date start)
	{
		this.start = new Date(start.getTime());

		this.current = new Date(start.getTime());
	}

	public void reset()
	{
		current.setTime(start.getTime());
	}

	public void addValue(double value)
	{
		addDataPoint(current, value);

		current.setTime(current.getTime() + 86400000L);
	}

	private void addDataPoint(Date date, double value)
	{
		if (!$map.containsKey(date.getTime()))
		{
			$map.put(date.getTime(), new ArrayList<Double>());
		}

		$map.get(date.getTime()).add(value);
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
}
