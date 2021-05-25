package simugen.core.data.interfaces;

import java.util.List;

import simugen.core.interfaces.Event;

public interface EventPublisher {

	void publish(Event event);

	void addEventListener(EventListener eventListener);

	void addAllListeners(List<EventListener> listListeners);

}
