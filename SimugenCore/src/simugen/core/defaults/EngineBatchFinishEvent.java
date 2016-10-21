package simugen.core.defaults;

import simugen.core.abstracts.AbstractProcessResponseEvent;
import simugen.core.interfaces.LoggingStyle;

public class EngineBatchFinishEvent extends AbstractProcessResponseEvent
{

	@Override
	public String printEvent(LoggingStyle style)
	{
		return "Engine batching complete.";
	}

	@Override
	public long getTime()
	{
		// TODO Auto-generated method stub
		return 0;
	}
}
