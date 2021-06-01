package simugen.core.defaults;

import java.util.ArrayList;
import java.util.List;

import simugen.core.data.interfaces.EventListener;
import simugen.core.data.interfaces.EventPublisher;
import simugen.core.interfaces.Event;
import simugen.core.interfaces.TimeStampable;

public class DefaultEventPublisher implements EventPublisher {
	private List<EventListener<?>> listEventListeners = new ArrayList<>();

	private long epoch = 0L;

	@SuppressWarnings("unchecked")
	@Override
	public void publish(Event event) {
		for (EventListener<?> listener : listEventListeners) {
			if (listener.getEventType().isAssignableFrom(event.getClass())) {
				listener.getClass().cast(listener).listen(event);
			}
		}
	}

	@Override
	public void addEventListener(EventListener<?> eventListener) {
		if (TimeStampable.class.isAssignableFrom(eventListener.getClass())) {
			TimeStampable time = (TimeStampable) eventListener;

			time.setEpoch(epoch);
		}

		listEventListeners.add(eventListener);
	}

	@Override
	public void addAllListeners(List<EventListener<?>> listListeners) {
		for (EventListener<?> listen : listListeners) {
			addEventListener(listen);
		}
	}
}
