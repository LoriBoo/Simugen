package simugen.core.defaults;

import simugen.core.abstracts.AbstractComponentOutputListener;

@Deprecated
public class OLD_DefaultElementDurationListener extends AbstractComponentOutputListener<ElementTransferEvent> {

	public OLD_DefaultElementDurationListener(DefaultComponentDurationContextHandler handler) {
		addEventContextHandler(handler);
	}

	@Override
	public Class<ElementTransferEvent> getEventType() {
		return ElementTransferEvent.class;
	}
}
