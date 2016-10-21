package simugen.core.abstracts;

import java.util.ArrayList;
import java.util.List;

import simugen.core.defaults.DefaultSimTimeController;
import simugen.core.defaults.ModelFinishedEvent;
import simugen.core.interfaces.SimComponent;
import simugen.core.interfaces.SimEngine;
import simugen.core.interfaces.SimEvent;
import simugen.core.interfaces.SimEventListener;
import simugen.core.interfaces.SimModel;
import simugen.core.interfaces.SimTimeController;

public abstract class AbstractSimModel implements SimModel
{
	private List<SimComponent> components = new ArrayList<>();

	private List<SimEventListener> listeners = new ArrayList<>();

	private final SimTimeController controller = new DefaultSimTimeController();

	boolean complete = false;

	@Override
	public void shutdown()
	{
		complete = true;
		onShutdown();
	}

	@Override
	public void addListener(SimEventListener e)
	{
		listeners.add(e);
	}

	@Override
	public void addComponent(SimComponent comp)
	{
		components.add(comp);
		if (comp instanceof SimEventListener)
		{
			listeners.add((SimEventListener) comp);
		}
	}

	@Override
	public List<SimEventListener> getListeners()
	{
		return listeners;
	}

	@Override
	public List<SimEvent> getNextEvents(SimEngine e)
	{
		final List<SimEvent> events = new ArrayList<>();
		
		if (complete || allComplete())
		{
			events.add(new ModelFinishedEvent());
			
			return events;
		}

		double next = e.getNext();

		final List<SimEvent> list = new ArrayList<>();

		for (SimComponent c : components)
		{
			list.add(c.process(next));
		}

		return controller.process(list.toArray(new TimeSimEvent[list.size()]));
	}

	private boolean allComplete()
	{
		for (SimComponent c : components)
		{
			if (!c.isComplete())
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean isReady()
	{
		for (SimComponent c : components)
		{
			if (!c.isReady())
			{
				return false;
			}
		}

		return true;
	}

}
