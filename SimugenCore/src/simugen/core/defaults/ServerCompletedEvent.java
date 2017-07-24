package simugen.core.defaults;

import simugen.core.abstracts.AbstractEvent;

public class ServerCompletedEvent extends AbstractEvent
{
	private final String serverID;

	private final String elementID;

	public ServerCompletedEvent(String serverID, String elementID, long time)
	{
		super(time);

		this.serverID = serverID;

		this.elementID = elementID;
	}

	@Override
	public String getLogID()
	{
		return super.getLogID() + "\t[" + serverID + " served " + elementID
				+ "]";
	}
}
