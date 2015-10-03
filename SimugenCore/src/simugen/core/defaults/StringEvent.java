package simugen.core.defaults;

import simugen.core.interfaces.LoggingStyle;
import simugen.core.interfaces.SimEvent;

public class StringEvent implements SimEvent
{
	final String message;
	
	public StringEvent(String message)
	{
		this.message = message;
	}
	
	@Override
	public String printEvent(LoggingStyle style)
	{
		return message;
	}

}
