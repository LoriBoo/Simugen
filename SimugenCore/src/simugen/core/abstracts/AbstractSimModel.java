package simugen.core.abstracts;

import java.util.ArrayList;
import java.util.List;

import simugen.core.defaults.ModelFinishedEvent;
import simugen.core.interfaces.SimComponent;
import simugen.core.interfaces.SimEngine;
import simugen.core.interfaces.SimEvent;
import simugen.core.interfaces.SimEventListener;
import simugen.core.interfaces.SimModel;

public abstract class AbstractSimModel implements SimModel
{
	private List<SimComponent> components = new ArrayList<>();
	
	private List<SimEventListener> listeners = new ArrayList<>();
	
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
		if(comp instanceof SimEventListener)
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
	public SimEvent getNextEvent(SimEngine e)
	{
		if(complete || allComplete())
		{
			return new ModelFinishedEvent();
		}
		
		for(SimComponent c : components)
		{
			double next = e.getNext();
			
			SimEvent evt = c.process(next);
			
			if(evt != null)
			{
				return evt;
			}
		}
		
		return null;
	}
	
	private boolean allComplete()
	{
		for(SimComponent c : components)
		{
			if(!c.isComplete())
			{
				return false;
			}
		}
		
		return true;
	}

	@Override
	public boolean isReady()
	{
		for(SimComponent c : components)
		{
			if(!c.isReady())
			{
				return false;
			}
		}
		
		return true;
	}

}
