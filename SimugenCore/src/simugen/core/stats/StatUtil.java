package simugen.core.stats;

import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.exception.MathIllegalStateException;

/**
 * Basic statistics utilities.
 * 
 * @author Lorelei
 *
 */
public class StatUtil {
	/**
	 * Number of replications test.<br>
	 * <br>
	 * I wrote this when I had a better understanding of probability and statistics
	 * and I'm not quite sure what everything is now, and I can't easily find
	 * information on this test on wikipedia.
	 * 
	 * @param CI      Confidence Interval
	 * @param s       Standard deviation of the sample
	 * @param maxRuns The upper bound of the number of reps test so that it doesn't
	 *                go on to infinity.
	 * @return The number of reps required to get the desired Confidence Interval
	 * @throws MathIllegalStateException
	 */
	public static int numberReps(double CI, double s, int maxRuns) throws MathIllegalStateException {
		int N = 2;
		int df = N - 1;
		double t = getT_TestValue(CI, df);

		double alpha = 1 - CI;

		while (compute(t, s, N) >= alpha && N < maxRuns) {
			N++;
			df++;
			t = getT_TestValue(CI, df);
		}
		return N;
	}

	/**
	 * Get the T test value for the following parameters.
	 * 
	 * @param CI - Confidence Interval
	 * @param df - Degrees of freedom
	 * @return
	 * @throws MathIllegalStateException
	 */
	private static double getT_TestValue(double CI, int df) throws MathIllegalStateException {
		TDistribution tDistro = new TDistribution(df);

		double alpha = 1 - CI;

		return tDistro.inverseCumulativeProbability(1 - (alpha / 2));
	}

	/**
	 * Compute the alpha (1 - CI) from the following parameters.
	 * 
	 * @param t T value from the T test of the desired CI and the degrees of
	 *          freedom.
	 * @param s Standard deviation of the sample.
	 * @param N Number of runs.
	 * @return
	 */
	private static double compute(double t, double s, double N) {
		return 2d * t * (s / Math.sqrt(N));
	}

	/**
	 * @param values Array of values to be averaged.
	 * @return Average of the array.
	 */
	public static double getAverage(double[] values) {
		double sum = 0d;

		for (Double d : values) {
			sum += d;
		}

		return sum / Double.valueOf(values.length);
	}
}
