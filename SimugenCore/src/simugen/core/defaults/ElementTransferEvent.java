package simugen.core.defaults;

import simugen.core.abstracts.AbstractEvent;
import simugen.core.interfaces.Component;
import simugen.core.interfaces.Element;

public class ElementTransferEvent extends AbstractEvent
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

}
