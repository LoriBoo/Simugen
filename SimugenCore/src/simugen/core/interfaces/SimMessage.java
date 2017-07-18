package simugen.core.interfaces;

/**
 * A message to be sent from components.
 * @author BASHH
 *
 */
public interface SimMessage
{
	/**
	 * A log friendly string of the message.
	 * 
	 * Used by {@link SimEngine} to console log.
	 * 
	 * @return
	 */
	public String getLogMessage();
	
	public SimComponent getSimComponentFrom();
	
	public SimComponent getSimComponentTo();
	
	public long getModelTime();
}
