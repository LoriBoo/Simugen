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
	
	private List<EventListener> listListeners = new ArrayList<>();
	
	boolean complete = false;

	@Override
	public void startUp()
	{
		complete = false;
		onStartup();
	}

	@Override
	public void shutdown()
	{
		complete = true;
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
	public List<EventListener> getListeners() {
		return listListeners;
	}
	
	@Override
	public void addListener(EventListener listener) {
		listListeners.add(listener);
	}
}
