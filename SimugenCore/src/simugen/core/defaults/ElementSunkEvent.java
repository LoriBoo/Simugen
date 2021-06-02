package simugen.core.defaults;

import simugen.core.abstracts.AbstractEvent;
import simugen.core.components.interfaces.Sink;
import simugen.core.data.interfaces.EventListener;
import simugen.core.interfaces.Element;
import simugen.core.interfaces.Event;
import simugen.core.interfaces.Model;

/**
 * {@link ElementSunkEvent}: The {@link Event} generated when an {@link Element}
 * is removed from the model via a {@link Sink}.
 * 
 * @author Lorelei
 *
 */
public final class ElementSunkEvent extends AbstractEvent
{
	private final Element element;

	/**
	 * @param element
	 *            The {@link Element} that was sunk.
	 * @param time
	 *            The {@link Model} time in which the {@link Element} was sunk.
	 */
	public ElementSunkEvent(Element element, long time)
	{
		super(time);

		this.element = element;
	}

	/**
	 * @return The element that was sunk.
	 */
	public Element getSunkElement()
	{
		return this.element;
	}

	/**
	 * @return The loggable message to be used by logging
	 *         {@link EventListener}s.
	 */
	@Override
	public String getLogMessage()
	{
		return super.getLogMessage().concat("\t\t[")
				.concat(this.element.getLogID()).concat(" sunk]");
	}

}
