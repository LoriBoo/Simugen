package simugen.core.test;

import simugen.core.defaults.DefaultSimEngine;
import simugen.core.interfaces.LoggingStyle;
import simugen.core.interfaces.SimEngine;
import simugen.core.interfaces.SimModel;

public class TestMain
{

	public static void main(String[] args)
	{
		SimModel model = new TestTimeModel();
		
		SimEngine engine = new DefaultSimEngine();
		
		engine.setLoggingStyle(LoggingStyle.DATA);
		
		engine.setModel(model);
		
		engine.setRuns(1);
		
		engine.start();
		
		System.out.println("Seed: " + engine.getSeed());
	}

}
