package simugen.core.defaults;

import simugen.core.abstracts.AbstractEvent;

public class ModelRunStartedEvent extends AbstractEvent {

	private int modelRun;

	public ModelRunStartedEvent(long time, int modelRun) {
		super(time);

		this.modelRun = modelRun;
	}

	@Override
	public String getLogMessage() {
		return "Model Run (" + modelRun + ") Started";
	}
}
