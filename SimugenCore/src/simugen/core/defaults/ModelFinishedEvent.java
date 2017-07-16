package simugen.core.defaults;

import simugen.core.abstracts.AbstractSimEvent;

public class ModelFinishedEvent extends AbstractSimEvent
{
	public ModelFinishedEvent(long time)
	{
		super("Model completed successfully.", time);
	}

}
