package simugen.core.test;

import simugen.core.abstracts.AbstractSimModel;
import simugen.core.abstracts.ProcessGenerated;
import simugen.core.defaults.DefaultProcessor;
import simugen.core.defaults.StringEvent;
import simugen.core.interfaces.SimEvent;
import simugen.core.rng.EmpiricalGenerator;

public class TestModel extends AbstractSimModel
{

	@Override
	public void startUp()
	{
		final EmpiricalGenerator numberGen = new EmpiricalGenerator();
		
		numberGen.addValue(0.125);
		
		numberGen.addValue(0.5);
		
		numberGen.addValue(0.5);
		
		numberGen.addValue(0.5);
		
		numberGen.addValue(0.8);
		
		numberGen.computeProbabilities();

		DefaultProcessor comp = new DefaultProcessor();

		ProcessGenerated<Number> process = new ProcessGenerated<Number>(
				numberGen)
		{
			int run = 0;

			@Override
			public boolean isComplete()
			{
				return run == 10;
			}

			@Override
			public SimEvent process(Number next)
			{
				run++;
				return new StringEvent("Number generated: " + next);
			}
		};

		comp.setProcess(process);

		addComponent(comp);
	}

	@Override
	public void onShutdown()
	{
	}

}
