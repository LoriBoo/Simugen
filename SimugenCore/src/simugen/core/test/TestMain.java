package simugen.core.test;

import java.util.Calendar;

import simugen.core.defaults.DefaultEngine;
import simugen.core.defaults.DefaultModelBuilder;
import simugen.core.interfaces.Engine;

public class TestMain
{
	public static void main(String[] args)
	{
		Engine engine = new DefaultEngine();

		Calendar calendar = Calendar.getInstance();

		calendar.set(2017, 0, 1, 10, 0, 0);

		engine.setEpoch(calendar.getTimeInMillis());

		//engine.setTimeStamper(TimeStamper.SHORT_DATE_AMPM);

		engine.setModelBuilder(new DefaultModelBuilder(TestNewModel.class));

		engine.setRuns(1);

		engine.start();

		System.out.println("Seed: " + engine.getSeed());
	}

}
