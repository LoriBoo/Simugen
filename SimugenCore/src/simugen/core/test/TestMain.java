package simugen.core.test;

import simugen.core.defaults.DefaultEngine;
import simugen.core.defaults.DefaultModelBuilder;
import simugen.core.interfaces.Engine;

public class TestMain
{
	public static void main(String[] args)
	{
		Engine engine = new DefaultEngine();

		engine.setModelBuilder(new DefaultModelBuilder(TestNewModel.class));

		engine.setRuns(1);

		engine.start();

		System.out.println("Seed: " + engine.getSeed());
	}

}
