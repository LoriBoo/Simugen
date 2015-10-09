package simugen.core.defaults;

import simugen.core.interfaces.LoggingStyle;
import simugen.core.interfaces.SimEvent;

public class EngineBatchFinishEvent implements SimEvent
{

	@Override
	public String printEvent(LoggingStyle style)
	{
		return "Engine batching complete.";
	}

}
