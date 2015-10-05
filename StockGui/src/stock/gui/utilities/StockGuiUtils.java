package stock.gui.utilities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import simugen.core.rng.EmpiricalGenerator;

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
}
