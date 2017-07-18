package simugen.core.abstracts;

import simugen.core.interfaces.SimComponent;
import simugen.core.interfaces.SimMessage;

public abstract class AbstractSimMessage implements SimMessage
{
	protected final SimComponent from;

	protected final SimComponent to;

	protected final long time;

	public AbstractSimMessage(long time, SimComponent from, SimComponent to)
	{
		this.time = time;

		this.from = from;

		this.to = to;
	}

	@Override
	public SimComponent getSimComponentFrom()
	{
		return this.from;
	}

	@Override
	public SimComponent getSimComponentTo()
	{
		return this.to;
	}

	@Override
	public long getModelTime()
	{
		return time;
	}
}
