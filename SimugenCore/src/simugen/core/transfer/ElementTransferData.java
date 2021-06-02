package simugen.core.transfer;

import simugen.core.components.interfaces.Component;
import simugen.core.interfaces.Element;
import simugen.core.interfaces.Model;
import simugen.core.transfer.interfaces.PipeData;

/**
 * Transfer data for when an {@link Element} is transfered from one
 * {@link Component} to another.
 * 
 * @author Lorelei
 *
 */
public final class ElementTransferData implements PipeData<Element>
{
	private final Element element;

	private final long timestamp;

	private final Component sentFrom;

	private final Component sentTo;

	/**
	 * @param sentFrom
	 *            The component that sent the {@link Element}.
	 * @param sentTo
	 *            The component receiving.
	 * @param element
	 *            The {@link Element} being transfered.
	 * @param timestamp
	 *            The {@link Model} time in milliseconds in which the transfer
	 *            occurred
	 */
	public ElementTransferData(Component sentFrom, Component sentTo,
			Element element, long timestamp)
	{
		this.sentFrom = sentFrom;

		this.sentTo = sentTo;

		this.element = element;

		this.timestamp = timestamp;
	}

	/**
	 * @return The {@link Component} that sent the {@link Element}.
	 */
	public Component getSentFrom()
	{
		return sentFrom;
	}

	/**
	 * @return The {@link Component} that received the {@link Element}.
	 */
	public Component getSentTo()
	{
		return sentTo;
	}

	/**
	 * @return The {@link Element} being transfered.
	 */
	@Override
	public Element getData()
	{
		return element;
	}

	/**
	 * @return The {@link Model} time in which the transfer occurred
	 */
	@Override
	public long getTime()
	{
		return timestamp;
	}
}
