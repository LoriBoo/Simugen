package simugen.core.data.interfaces;

import simugen.core.interfaces.Engine;
import simugen.core.interfaces.Event;

/**
 * An {@link EventListener} is added to a list of {@link EventListener}s in an
 * {@link Engine}, where each {@link EventListener}'s {@link #listen(Event)}
 * method is called after each event is published by the {@link EventPublisher}.
 * 
 * @author Lorelei
 *
 * @param <T> extends type {@link Event}, represents the type of {@link Event}
 *            that this {@link EventListener} listens for.
 */
public interface EventListener<T extends Event> {
	public Class<T> getEventType();

	/**
	 * Classes that implement this interface will override this method to do what it
	 * will with the information provided by the {@link Event}
	 * 
	 * @param event The {@link Event} that just got published.
	 */
	public void listen(T event);
}
