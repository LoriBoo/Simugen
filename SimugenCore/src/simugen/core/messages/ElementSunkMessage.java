package simugen.core.messages;

import simugen.core.abstracts.AbstractSimMessage;
import simugen.core.interfaces.SimElement;
import simugen.core.interfaces.SimSink;

public class ElementSunkMessage extends AbstractSimMessage
{
	private final SimElement element;

	public ElementSunkMessage(long time, SimSink sink, SimElement element)
	{
		super(time, sink, null);

		this.element = element;
	}

	@Override
	public String getLogMessage()
	{
		final String message = this.from.getClass().getSimpleName()
				+ ": Element " + element.toString() + " was sunk.";

		return message;
	}

}
