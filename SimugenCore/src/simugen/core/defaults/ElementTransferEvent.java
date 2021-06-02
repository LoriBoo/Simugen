package simugen.core.defaults;

import simugen.core.abstracts.AbstractEvent;
import simugen.core.components.interfaces.Component;
import simugen.core.interfaces.Element;

final public class ElementTransferEvent extends AbstractEvent
{
	private final Component from;

	private final Component to;

	private final Element element;

	public ElementTransferEvent(Element element, Component from, Component to,
			long time)
	{
		super(time);

		this.from = from;

		this.to = to;

		this.element = element;
	}

	@Override
	public String getLogMessage()
	{
		return super.getLogMessage().concat("\t[" + from.getLogID() + " --{"
				+ element.getLogID() + "}--> " + to.getLogID() + "]");
	}

	public Component getFromID()
	{
		return this.from;
	}

	public Component getToID()
	{
		return this.to;
	}

	public Element getElement()
	{
		return this.element;
	}
}
