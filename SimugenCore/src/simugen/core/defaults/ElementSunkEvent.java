package simugen.core.defaults;

import simugen.core.abstracts.AbstractEvent;
import simugen.core.interfaces.Element;

public final class ElementSunkEvent extends AbstractEvent
{
	private final Element element;

	public ElementSunkEvent(Element element, long time)
	{
		super(time);

		this.element = element;
	}

	public Element getSunkElement()
	{
		return this.element;
	}

	@Override
	public String getLogMessage()
	{
		return super.getLogMessage().concat("\t\t[")
				.concat(this.element.getLogID()).concat(" sunk]");
	}

}
