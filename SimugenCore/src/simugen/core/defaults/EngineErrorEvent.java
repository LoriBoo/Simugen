package simugen.core.defaults;

import simugen.core.abstracts.AbstractEvent;

final public class EngineErrorEvent extends AbstractEvent
{
	private String message;

	public EngineErrorEvent(long time, String message)
	{
		super(time);
		this.message = message;
	}

	@Override
	public String getLogMessage()
	{
		return "<<Error>> " + message;
	}
}
