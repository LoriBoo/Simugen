package simugen.core.rng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import simugen.core.interfaces.DataGenerator;

public class EmpiricalGenerator implements DataGenerator<Number>, Serializable
{
	private static final long serialVersionUID = 1L;

	private ArrayList<Double> values = new ArrayList<>();

	private ArrayList<Double> probabilities = new ArrayList<>();

	private Map<Double, Double> mapValueToProbabilities = new HashMap<>();

	private Map<Double, Double> cdf = new HashMap<>();

	private boolean ready = false;

	public void addValue(double d)
	{
		values.add(d);

		ready = false;
	}

	public void computeProbabilities()
	{
		double n = values.size();

		double prob;

		@SuppressWarnings("unchecked")
		ArrayList<Double> buffer = (ArrayList<Double>) values.getClass()
				.cast(values.clone());

		buffer.sort(new Comparator<Double>()
		{
			@Override
			public int compare(Double o1, Double o2)
			{
				int count1 = getNumberOf(o1);
				int count2 = getNumberOf(o2);

				return Integer.compare(count1, count2);
			}
		});

		values = buffer;

		for (double d : values)
		{
			if (mapValueToProbabilities.containsKey(d))
			{
				continue;
			}

			double m = getNumberOf(d);

			prob = m / n;

			mapValueToProbabilities.put(d, prob);
		}

		createCDF();

		ready = true;
	}

	private void createCDF()
	{
		double last = 0;

		for (Double value : mapValueToProbabilities.keySet()
				.toArray(new Double[0]))
		{
			last += mapValueToProbabilities.get(value);

			mapValueToProbabilities.put(value, last);

			cdf.put(last, value);
		}

		probabilities.clear();

		probabilities.addAll(mapValueToProbabilities.values());
	}

	private int getNumberOf(double d)
	{
		int count = 0;
		for (double val : values)
		{
			if (Double.valueOf(val).equals(d))
			{
				count++;
			}
		}
		return count;
	}

	// If the probability of a number is 0.25,
	// and the passed probability, d, is <=0.25, it's valid.
	@Override
	public Number getNext(double d)
	{
		assert ready;
		
		for (Double prob : probabilities)
		{
			if (d <= prob)
			{
				return cdf.get(prob);
			}
		}

		throw new IllegalStateException("Could not find a valid number!");
	}
}
