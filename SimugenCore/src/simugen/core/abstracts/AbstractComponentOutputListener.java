package simugen.core.abstracts;

import java.util.ArrayList;
import java.util.List;

import simugen.core.data.interfaces.EventContextHandler;
import simugen.core.data.interfaces.EventListener;
import simugen.core.interfaces.Event;

/**
 * Abstract implementation of {@link EventListener}.
 * 
 * @author Lorelei
 *
 * @param <T>
 */

@Deprecated
public abstract class AbstractComponentOutputListener<T extends Event> implements EventListener<T> {
	protected List<EventContextHandler<T>> listEventContextHandlers = new ArrayList<>();;

	/**
	 * Default implementation of {@link #listen(Event)}; for each
	 * {@link EventContextHandler}, handle the event
	 */
	@Override
	public void listen(T event) {
		for (EventContextHandler<T> handler : listEventContextHandlers) {
			handler.handleEvent(event);
		}
	}

	/**
	 * 
	 * @param handler {@link EventContextHandler} for handling the listen event.
	 */
	public void addEventContextHandler(EventContextHandler<T> handler) {
		this.listEventContextHandlers.add(handler);
	}
}
