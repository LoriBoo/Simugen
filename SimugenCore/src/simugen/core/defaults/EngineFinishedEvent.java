package simugen.core.defaults;

import simugen.core.abstracts.AbstractEvent;

final public class EngineFinishedEvent extends AbstractEvent
{
	public EngineFinishedEvent(long time)
	{
		super(time);
	}

	@Override
	public String getLogMessage()
	{
		return "Engine Batching Finished";
	}
}
