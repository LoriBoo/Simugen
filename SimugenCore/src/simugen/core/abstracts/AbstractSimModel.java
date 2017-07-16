package simugen.core.abstracts;

import java.util.ArrayList;
import java.util.List;

import simugen.core.defaults.DefaultSimController;
import simugen.core.defaults.ModelFinishedEvent;
import simugen.core.interfaces.SimComponent;
import simugen.core.interfaces.SimController;
import simugen.core.interfaces.SimEngine;
import simugen.core.interfaces.SimEvent;
import simugen.core.interfaces.SimEventListener;
import simugen.core.interfaces.SimModel;

public abstract class AbstractSimModel implements SimModel
{
	private List<SimComponent> components = new ArrayList<>();

	private List<SimEventListener> listeners = new ArrayList<>();

	private final SimController controller = new DefaultSimController();

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

	/**
	 * {@link #getNextEvents(SimEngine)} checks for next events based on the
	 * next tick from the engine.
	 * 
	 * AbstractSimModel delegates to the controller assigned, to produce events.
	 */
	@Override
	public List<SimEvent> getNextEvents(SimEngine e)
	{
		final List<SimEvent> events = new ArrayList<>();

		final long modelTime = e.getMilliseconds();

		// If the model has completed return a ModelFinishedEvent
		if (complete || allComplete())
		{

			events.add(new ModelFinishedEvent(modelTime));

			return events;
		}

		double next = e.getNext();

		return controller.getNextEvents(next, components);
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
