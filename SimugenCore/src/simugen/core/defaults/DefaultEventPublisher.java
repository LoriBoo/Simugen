package simugen.core.defaults;

import java.util.ArrayList;
import java.util.List;

import simugen.core.data.interfaces.EventListener;
import simugen.core.data.interfaces.EventPublisher;
import simugen.core.interfaces.Event;

public class DefaultEventPublisher implements EventPublisher {
	private List<EventListener> listEventListeners = new ArrayList<EventListener>();

	@Override
	public void publish(Event event) {
		for (EventListener listener : listEventListeners) {
			if (listener.getEventType().isAssignableFrom(event.getClass())) {
				listener.listen(event);
			}
		}
	}

	@Override
	public void addEventListener(EventListener eventListener) {
		listEventListeners.add(eventListener);
	}

	@Override
	public void addAllListeners(List<EventListener> listListeners) {
		listEventListeners.addAll(listListeners);
	}
}
