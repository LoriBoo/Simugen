package simugen.core.defaults;

import java.util.List;

import simugen.core.interfaces.EngineTick;
import simugen.core.interfaces.Event;

public class NullEngineTick implements EngineTick
{
	/**
	 * NullEngineTick will not resolve true for anything.
	 */
	@Override
	public boolean equals(Object obj)
	{
		return false;
	}

	@Override
	public double getNextRand()
	{
		throw new IllegalStateException();
	}

	@Override
	public long getEventTime(long duration)
	{
		throw new IllegalStateException();
	}

	@Override
	public void addEvent(Event event)
	{
		throw new IllegalStateException();
	}

	@Override
	public List<Event> getEvents()
	{
		throw new IllegalStateException();
	}

	@Override
	public void addAllEvents(List<Event> listEvents)
	{
		throw new IllegalStateException();
	}

	@Override
	public void setCurrentTime(long milliseconds)
	{
		throw new IllegalStateException();
	}
}
