package simugen.core.abstracts;

import simugen.core.interfaces.LoggingStyle;
import simugen.core.interfaces.SimEvent;

/**
 * @formatter:off
 *
 *-----------------------
 * AbstractSimEvent class
 *-----------------------
 *
 *	
 * @formatter:on
 * @author BASHH
 *
 */
public abstract class AbstractSimEvent implements SimEvent {

	private final String message;

	private final long time;

	protected boolean processed = false;

	/**
	 * Every event contains a message to be displayed in the console, and a
	 * time-stamp. The time stamp is milliseconds from the epoch of the engine.
	 * 
	 * @param message
	 * @param time
	 */
	public AbstractSimEvent(String message, long time) {
		this.message = message;

		this.time = time;
	}

	@Override
	public String printEvent(LoggingStyle style) {
		return message;
	}

	/**
	 * 
	 */
	@Override
	public boolean processed() {
		return this.processed;
	}

	/**
	 * Set the event as having been processed
	 */
	@Override
	public void setProcessed() {
		this.processed = true;
	}

	/**
	 * Return the model time at which this event occurred.
	 */
	@Override
	public long getTime() {
		return this.time;
	}

	@Override
	@Deprecated
	public boolean response() {
		throw new IllegalAccessError("method 'response' is deprecated");
	}

	@Override
	@Deprecated
	public void setProcessedResponse(boolean processed, boolean response) {
		throw new IllegalAccessError("method 'setProcessedResponse' is deprecated");
	}
}
