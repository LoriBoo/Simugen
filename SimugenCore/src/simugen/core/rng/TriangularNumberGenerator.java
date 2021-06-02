package simugen.core.rng;

import simugen.core.interfaces.DataGenerator;
import simugen.core.interfaces.EngineTick;

/**
 * Generates a variate based on a simple triangular distribution. <br>
 * <br>
 * A triangular distribution uses the min, max, and mode to generate a random
 * variate. Note that Mode is not necessarily the median, it is just the number
 * that appears the most in the set.
 * 
 * @author Lorelei
 * @see <a href=
 *      "https://en.wikipedia.org/wiki/Triangular_distribution">Wikipedia -
 *      Triangular Distribution</a>
 */
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
		Fc = (mode - min) / (max - min);
	}

	@Override
	public Number getNext(EngineTick tick)
	{
		assert min < max;
		assert min < mode;
		assert mode < max;

		double d = tick.getNextRand();

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
