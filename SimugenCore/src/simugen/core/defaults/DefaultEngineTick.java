package simugen.core.defaults;

import java.util.ArrayList;
import java.util.List;

import simugen.core.interfaces.Engine;
import simugen.core.interfaces.EngineTick;
import simugen.core.interfaces.Event;

public final class DefaultEngineTick implements EngineTick
{
	private final Engine engine;

	private long currentTime = 0L;

	private final List<Event> events = new ArrayList<>();

	public DefaultEngineTick(Engine engine)
	{
		this.engine = engine;

		this.currentTime = engine.getMilliseconds();
	}

	@Override
	public double getNextRand()
	{
		return engine.getNext();
	}

	@Override
	public long getEventTime(long duration)
	{
		final long eventTime = currentTime + duration;

		return eventTime;
	}

	@Override
	public void addEvent(Event event)
	{
		assert event != null;

		this.events.add(event);
	}

	@Override
	public void addAllEvents(List<Event> listEvents)
	{
		assert listEvents != null;
		assert !listEvents.isEmpty();

		this.events.addAll(listEvents);
	}

	@Override
	public List<Event> getEvents()
	{
		final List<Event> events = this.events.isEmpty() ? null
				: new ArrayList<>(this.events);

		this.events.clear();

		return events;
	}

	@Override
	public void setCurrentTime(long milliseconds)
	{
		this.currentTime = milliseconds;
	}
}
