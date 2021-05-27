package simugen.core.abstracts;

import simugen.core.components.interfaces.Component;
import simugen.core.data.interfaces.EventContextHandler;
import simugen.core.defaults.ElementTransferEvent;

public abstract class AbstractDurationContextHandler implements EventContextHandler<ElementTransferEvent> {
	protected Component component;

	protected long duration;

	private long begin;

	public AbstractDurationContextHandler(Component component) {
		this.component = component;
	}

	@Override
	public void handleEvent(ElementTransferEvent event) {
		if (event.getToID().equals(component)) {
			begin = event.getTime();
		} else if (event.getFromID().equals(component)) {
			duration = event.getTime() - begin;
			doHandleEvent(event);
		}
	}

	protected abstract void doHandleEvent(ElementTransferEvent event);
}
