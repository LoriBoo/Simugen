package simugen.core.defaults;

import java.util.ArrayList;
import java.util.List;

import simugen.core.abstracts.AbstractComponentOutputListener;
import simugen.core.data.interfaces.EventContextHandler;
import simugen.core.interfaces.Event;

public class DefaultElementDurationListener extends AbstractComponentOutputListener {
	private List<EventContextHandler<ElementTransferEvent>> listEventContextHandlers = new ArrayList<>();;

	public DefaultElementDurationListener(DefaultComponentDurationContextHandler handler) {
		addEventContextHandler(handler);
	}

	@Override
	public Class<? extends Event> getEventType() {
		return ElementTransferEvent.class;
	}

	@Override
	public void listen(Event event) {
		ElementTransferEvent ev = (ElementTransferEvent) event;

		for (EventContextHandler<ElementTransferEvent> handler : listEventContextHandlers) {
			handler.handleEvent(ev);
		}
	}

	public void addEventContextHandler(EventContextHandler<ElementTransferEvent> handler) {
		this.listEventContextHandlers.add(handler);
	}
}
