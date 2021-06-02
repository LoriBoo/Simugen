package simugen.core.data.interfaces;

import java.util.List;

import simugen.core.data.defaults.DefaultEventPublisher;
import simugen.core.interfaces.EngineTick;
import simugen.core.interfaces.Event;
import simugen.core.interfaces.Model;

/**
 * The {@link EventPublisher} stores all the {@link EventListener}s in the
 * {@link Model}, and on each {@link EngineTick} it publishes all events to each
 * {@link EventListener} so they can process the {@link Event} as needed.<br>
 * <br>
 * There is no abstract implementation of this interface, only a provided
 * {@link DefaultEventPublisher}. There should be no reason to implement a
 * custom {@link EventPublisher} however advanced users may do so as they
 * please.
 * 
 * @author Lorelei
 *
 */
public interface EventPublisher
{
	/**
	 * @param event
	 *            {@link Event} to be published to {@link EventListener}s.
	 */
	public void publish(Event event);

	/**
	 * @param eventListener
	 *            The {@link EventListener} to be added to the list in the
	 *            {@link EventPublisher}.
	 */
	public void addEventListener(EventListener<?> eventListener);

	/**
	 * @param listListeners
	 *            List of {@link EventListener}s to be added to the list in the
	 *            {@link EventPublisher}.
	 */
	public void addAllListeners(List<EventListener<?>> listListeners);

}
