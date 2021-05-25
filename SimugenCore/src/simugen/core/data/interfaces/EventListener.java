package simugen.core.data.interfaces;

import simugen.core.interfaces.Event;

public interface EventListener {
	public Class<? extends Event> getEventType();

	public void listen(Event event);
}
