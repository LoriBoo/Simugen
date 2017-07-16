package simugen.core.test;

import java.util.concurrent.TimeUnit;

import simugen.core.abstracts.AbstractSimModel;
import simugen.core.abstracts.ProcessGenerated;
import simugen.core.abstracts.TimeSimEvent;
import simugen.core.defaults.DefaultProcessor;
import simugen.core.defaults.DefaultSimTimeController;
import simugen.core.defaults.StringEvent;
import simugen.core.interfaces.SimEvent;
import simugen.core.interfaces.SimModel;
import simugen.core.rng.TriangularNumberGenerator;

public class TestTimeModel extends AbstractSimModel
{

	@Override
	public void startUp()
	{
		final TriangularNumberGenerator rng = new TriangularNumberGenerator(
				0.25, 0.5, 1.0);

		final DefaultSimTimeController timeController = new DefaultSimTimeController();

		final DefaultProcessor comp = new DefaultProcessor();

		final ProcessGenerated<Number> process = new ProcessGenerated<Number>(
				rng)
		{
			final StringEvent msg = new StringEvent("Time Event Completed");
			
			final TimeSimEvent event = new TimeSimEvent(TimeUnit.DAYS, msg);

			@Override
			public boolean isComplete()
			{
				System.out.println(event.processed());
				return event.processed();
			}

			@Override
			public SimEvent process(Number next)
			{
				if(!event.isTimeSet())
				{
					event.setTime(next.doubleValue());
				}
				
				timeController.process(event);
				return event;
			}
		};

		comp.setProcess(process);

		addComponent(comp);
	}

	@Override
	public void onShutdown()
	{

	}

	@Override
	public SimModel getCopy()
	{
		return this;
	}

}
