package simugen.core.stats;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.TDistribution;
import org.apache.commons.math.distribution.TDistributionImpl;

public class StatUtil
{
	public int numberReps(double CI, double s, int maxRuns)
			throws MathException
	{
		int N = 2;
		int df = N - 1;
		double t = getT_TestValue(CI, df);

		while (minimalDifference(compute(t, s, N), CI) == false && N < maxRuns)
		{
			N++;
			df++;
			t = getT_TestValue(CI, df);
		}
		return N;
	}

	private double getT_TestValue(double interval, int df) throws MathException
	{
		TDistribution tDistro = new TDistributionImpl(df);

		double alpha = 1 - interval;

		return tDistro.inverseCumulativeProbability(1 - (alpha / 2));
	}

	private boolean minimalDifference(double d0, double d1)
	{
		double diff = Math.abs(d0 - d1);

		return diff <= 1e-6;
	}

	private double compute(double t, double s, double N)
	{
		return 2d * t * (s / Math.sqrt(N));
	}
}
