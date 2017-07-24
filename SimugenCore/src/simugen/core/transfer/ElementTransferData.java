package simugen.core.transfer;

import simugen.core.interfaces.Component;
import simugen.core.interfaces.Element;
import simugen.core.transfer.interfaces.PipeData;

public final class ElementTransferData implements PipeData<Element>
{
	private final Element element;

	private final long timestamp;

	private final Component sentFrom;

	private final Component sentTo;

	public ElementTransferData(Component sentFrom, Component sentTo,
			Element element, long timestamp)
	{
		this.sentFrom = sentFrom;

		this.sentTo = sentTo;

		this.element = element;

		this.timestamp = timestamp;
	}

	public Component getSentFrom()
	{
		return sentFrom;
	}

	public Component getSentTo()
	{
		return sentTo;
	}

	public Element getData()
	{
		return element;
	}

	@Override
	public long getTime()
	{
		return timestamp;
	}
}
