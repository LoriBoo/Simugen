package simugen.core.data.defaults;

import java.util.ArrayList;
import java.util.List;

import simugen.core.data.interfaces.EventListener;
import simugen.core.data.interfaces.EventPublisher;
import simugen.core.interfaces.Event;

/**
 * Default implementation of {@link EventPublisher}.
 * 
 * @author Lorelei
 *
 */
final public class DefaultEventPublisher implements EventPublisher
{
	private List<EventListener<?>> listEventListeners = new ArrayList<>();

	@SuppressWarnings("unchecked")
	@Override
	public void publish(Event event)
	{
		for (EventListener<? extends Event> listener : listEventListeners)
		{
			if (listener.getEventType().isAssignableFrom(event.getClass()))
			{

				// This is dirty but thanks to generics it appears necessary. If
				// we don't cast
				// like this, the listen event doesn't accept the supertype
				// Event.
				//
				// It should since <? extends Event> but Java doesn't like that
				// apparently.
				// If someone can figure out why this isn't working that way,
				// and why casting
				// 'listener' to the same class as 'listener' does, please email
				// me.
				//
				// lorelei.joslin@gmail.com
				listener.getClass().cast(listener).listen(event);
			}
		}
	}

	@Override
	public void addEventListener(EventListener<?> eventListener)
	{
		listEventListeners.add(eventListener);
	}

	@Override
	public void addAllListeners(List<EventListener<?>> listListeners)
	{
		for (EventListener<?> listen : listListeners)
		{
			addEventListener(listen);
		}
	}
}
