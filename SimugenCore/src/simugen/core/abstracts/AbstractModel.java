package simugen.core.abstracts;

import java.util.ArrayList;
import java.util.List;

import simugen.core.components.interfaces.Component;
import simugen.core.data.interfaces.EventListener;
import simugen.core.interfaces.EngineTick;
import simugen.core.interfaces.Event;
import simugen.core.interfaces.Model;

public abstract class AbstractModel implements Model
{
	private List<Component> components = new ArrayList<>();

	private List<EventListener<?>> listListeners = new ArrayList<>();

	private String outputLocation = null;

	boolean complete = false;

	protected int run = -1;

	protected long seed = 0;

	protected long epoch;

	@Override
	public void startUp(int run, long seed, long epoch)
	{
		complete = false;
		this.seed = seed;
		this.run = run;
		this.epoch = epoch;

		onStartup();
	}

	@Override
	public void shutdown()
	{
		complete = true;
		this.seed = 0;
		onShutdown();
	}

	@Override
	public void addComponent(Component components)
	{
		this.components.add(components);
	}

	@Override
	public List<Event> getEvents(EngineTick tick)
	{
		for (Component c : components)
		{
			c.getEvents(tick);
		}

		return tick.getEvents();
	}

	@Override
	public List<EventListener<?>> getListeners()
	{
		return listListeners;
	}

	@Override
	public void addListener(EventListener<?> listener)
	{
		listListeners.add(listener);
	}

	@Override
	public void setOutputLocation(String location)
	{
		this.outputLocation = location;
	}

	@Override
	public String getOutputLocation()
	{
		return this.outputLocation;
	}
}
