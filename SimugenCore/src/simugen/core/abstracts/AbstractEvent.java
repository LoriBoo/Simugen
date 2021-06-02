package simugen.core.abstracts;

import simugen.core.interfaces.Event;

/**
 * Abstract implementation of {@link Event}
 * 
 * @author Lorelei
 *
 */
public abstract class AbstractEvent implements Event
{
	private final long time;

	private String ID = null;

	private boolean consumed = false;

	private long modelSeed;

	/**
	 * 
	 * @param time
	 *            The time in milliseconds from model start at which this
	 *            {@link Event} occurred.<br>
	 *            Represents model time, not real time.
	 */
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

	@Override
	public void consume()
	{
		consumed = true;
	}

	@Override
	public boolean isConsumed()
	{
		return consumed;
	}

	@Override
	public long getModelSeed()
	{
		return modelSeed;
	}

	@Override
	public void setModelSeed(long modelSeed)
	{
		this.modelSeed = modelSeed;
	}
}
