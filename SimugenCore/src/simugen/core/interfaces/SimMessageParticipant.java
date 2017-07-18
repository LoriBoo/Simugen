package simugen.core.interfaces;

import java.util.List;

public interface SimMessageParticipant
{
	/**
	 * Returns null if there is no message to be sent.
	 * 
	 * @param engine
	 * @return
	 */
	public List<SimMessage> getMessages(SimEngine engine, long tick);
	
	/**
	 * Components that receive messages are not guaranteed to react to them.
	 * 
	 * @param message
	 */
	public void receiveMessage(SimMessage message);
}
