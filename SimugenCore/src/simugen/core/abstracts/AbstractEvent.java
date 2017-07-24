package simugen.core.abstracts;

import simugen.core.interfaces.Event;

public abstract class AbstractEvent implements Event
{
	private final long time;

	private String ID = null;

	public AbstractEvent(long time)
	{
		this.time = time;
	}

	@Override
	public long getTime()
	{
		return time;
	}

	@Override
	public void setLogID(String ID)
	{
		assert this.ID == null;

		this.ID = ID;
	}

	@Override
	public String getLogID()
	{
		return this.ID == null ? Event.super.getLogID() : ID;
	}
}
