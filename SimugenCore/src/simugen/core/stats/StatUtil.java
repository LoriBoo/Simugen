package simugen.core.stats;

import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.exception.MathIllegalStateException;

public class StatUtil {
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

	private static double getT_TestValue(double interval, int df) throws MathIllegalStateException {
		TDistribution tDistro = new TDistribution(df);

		double alpha = 1 - interval;

		return tDistro.inverseCumulativeProbability(1 - (alpha / 2));
	}

	private static double compute(double t, double s, double N) {
		return 2d * t * (s / Math.sqrt(N));
	}

	public static double getAverage(double[] values) {
		double sum = 0d;

		for (Double d : values) {
			sum += d;
		}

		return sum / Double.valueOf(values.length);
	}
}
