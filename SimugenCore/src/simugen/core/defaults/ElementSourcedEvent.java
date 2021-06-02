package simugen.core.defaults;

import simugen.core.abstracts.AbstractEvent;
import simugen.core.components.interfaces.Source;
import simugen.core.data.interfaces.EventListener;
import simugen.core.interfaces.Element;
import simugen.core.interfaces.Model;

/**
 * {@link ElementSourcedEvent}: Event in which an {@link Element} is introduced
 * to the model from a {@link Source}.
 * 
 * @author Lorelei
 *
 */
public final class ElementSourcedEvent extends AbstractEvent
{
	private final Element element;

	/**
	 * @param element
	 *            The {@link Element} that is being sourced.
	 * @param time
	 *            The {@link Model} time in milliseconds in which the
	 *            {@link Element} was sourced.
	 */
	public ElementSourcedEvent(Element element, long time)
	{
		super(time);

		this.element = element;
	}

	/**
	 * @return The element being sourced.
	 */
	public Element getElement()
	{
		return this.element;
	}

	/**
	 * @return The message to be logged by logging {@link EventListener}s.
	 */
	@Override
	public String getLogMessage()
	{
		return super.getLogMessage().concat("\t[")
				.concat(this.element.getLogID()).concat(" sourced]");
	}
}
