package stock.model.test;

import simugen.core.defaults.DefaultSimEngine;
import simugen.core.interfaces.LoggingStyle;
import simugen.core.interfaces.SimEngine;
import simugen.core.rng.EmpiricalGenerator;
import stock.model.StockModel;

public class TestStockMain
{

	public static void main(String[] args)
	{
		StockModel model;

		EmpiricalGenerator generator = new EmpiricalGenerator();

		double values[] =
		{ 0.030, -0.010, 0.004, 0.003, -0.004, 0.011, 0.001, -0.010, -0.032,
				-0.007, -0.016, -0.016, -0.001, -0.013, -0.016, 0.013, 0.017,
				-0.013, 0.011 };
		
		for(double d : values)
		{
			generator.addValue(d);
		}
		
		generator.computeProbabilities();
		
		model = new StockModel(generator, "Huntington Ingalls");
		
		model.setInitialValue(106.94);
		
		model.setComputations(100);
		
		SimEngine engine = new DefaultSimEngine();
		
		engine.setLoggingStyle(LoggingStyle.DATA);
		
		engine.setModel(model);
		
		engine.start();
	}

}
