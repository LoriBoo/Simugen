package simugen.core.defaults;

import simugen.core.abstracts.AbstractProcessResponseEvent;
import simugen.core.interfaces.LoggingStyle;

/**
 * This class is old and really misunderstands the concept of events
 * 
 * @author BASHH
 *
 */
@Deprecated
public class StringEvent extends AbstractProcessResponseEvent {
	final String message;

	public StringEvent(String message) {
		this.message = message;
	}

	@Override
	public String printEvent(LoggingStyle style) {
		return message;
	}

	@Override
	public long getTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setProcessed() {
		// TODO Auto-generated method stub

	}
}
