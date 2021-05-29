package simugen.core.defaults;

import java.util.ArrayList;
import java.util.List;

import simugen.core.data.interfaces.EventListener;
import simugen.core.data.interfaces.EventPublisher;
import simugen.core.interfaces.Event;
import simugen.core.interfaces.TimeStampable;

public class DefaultEventPublisher implements EventPublisher {
	private List<EventListener> listEventListeners = new ArrayList<EventListener>();

	private long epoch = 0L;

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
		for (EventListener listen : listListeners) {
			addEventListener(listen);

			if (TimeStampable.class.isAssignableFrom(listen.getClass())) {
				TimeStampable time = (TimeStampable) listen;

				time.setEpoch(epoch);
			}
		}
	}
}
