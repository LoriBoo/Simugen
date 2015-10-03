package simugen.core.rng;

import simugen.core.interfaces.DataGenerator;

public class TriangularNumberGenerator implements DataGenerator<Number>
{
	private double min = 0;

	private double max = 0;

	private double mode = 0;

	private double Fc = 0;

	public TriangularNumberGenerator(double min, double mode, double max)
	{
		this.min = min;
		this.max = max;
		this.mode = mode;
		Fc = (mode-min) / (max-min);
	}
	
	@Override
	public boolean isReady()
	{
		return (min != max && min != mode && min < mode && mode < max);
	}

	@Override
	public Number getNext(double d)
	{
		Number number = null;
		if (d < Fc)
		{
			number = min + Math.sqrt(d * (max - min) * (mode - min));
		}
		else
		{
			number = max - Math.sqrt((1 - d) * (max - min) * (max - mode));
		}
		return number;
	}

}
