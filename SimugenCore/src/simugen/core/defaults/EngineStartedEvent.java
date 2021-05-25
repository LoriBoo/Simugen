package simugen.core.defaults;

import simugen.core.abstracts.AbstractEvent;

public class EngineStartedEvent extends AbstractEvent {
	public EngineStartedEvent(long time) {
		super(time);
	}
	
	@Override
	public String getLogMessage() {
		return "Engine Batching Started";
	}
}