package simugen.core.defaults;

import simugen.core.abstracts.AbstractSimEvent;

public class EngineBatchFinishEvent extends AbstractSimEvent {
	public EngineBatchFinishEvent(long time) {
		super("Engine batch completed.", time);
	}
}
