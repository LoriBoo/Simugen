package simugen.core.defaults;

import simugen.core.abstracts.AbstractEvent;

public class PublishSeedEvent extends AbstractEvent {

	private long seed;

	public PublishSeedEvent(long seed) {
		super(0);

		this.seed = seed;
	}

	@Override
	public String getLogMessage() {
		// TODO Auto-generated method stub
		return "Seed: " + seed;
	}
}
