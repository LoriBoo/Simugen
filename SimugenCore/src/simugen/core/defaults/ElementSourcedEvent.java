package simugen.core.defaults;

import simugen.core.abstracts.AbstractEvent;
import simugen.core.interfaces.Element;

public final class ElementSourcedEvent extends AbstractEvent
{
	private final Element element;

	public ElementSourcedEvent(Element element, long time)
	{
		super(time);

		this.element = element;
	}

	public Element getElement()
	{
		return this.element;
	}

	@Override
	public String getLogMessage()
	{
		return super.getLogMessage().concat("\t[")
				.concat(this.element.getLogID()).concat(" sourced]");
	}
}
