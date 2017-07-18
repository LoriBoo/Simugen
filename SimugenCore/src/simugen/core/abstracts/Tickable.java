package simugen.core.abstracts;

public abstract class Tickable
{
	private long tick = -1L;

	protected void setTick(long tick)
	{
		assert tick > this.tick;

		this.tick = tick;
	}

	protected long getCurrentTick()
	{
		return tick;
	}

	protected boolean canDoGetMessages(long tick)
	{
		if (this.tick == tick)
			return false;

		this.tick = tick;

		return true;
	}
}
