package simugen.core.abstracts;

import java.util.ArrayList;
import java.util.List;

import simugen.core.defaults.ModelFinishedEvent;
import simugen.core.interfaces.SimComponent;
import simugen.core.interfaces.SimEngine;
import simugen.core.interfaces.SimEvent;
import simugen.core.interfaces.SimModel;

public abstract class AbstractSimModel implements SimModel
{
	private List<SimComponent> components = new ArrayList<>();
	
	boolean complete = false;

	@Override
	public void shutdown()
	{
		complete = true;
		onShutdown();
	}

	@Override
	public void addComponent(SimComponent comp)
	{
		components.add(comp);
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
			SimEvent evt = c.process(e.getNext());
			
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
