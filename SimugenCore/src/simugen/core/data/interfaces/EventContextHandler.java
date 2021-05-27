package simugen.core.data.interfaces;

import simugen.core.interfaces.Event;

public interface EventContextHandler<T extends Event> {
	public void handleEvent(T event);
}
