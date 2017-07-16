package simugen.core.defaults;

import java.util.List;

import simugen.core.interfaces.SimComponent;
import simugen.core.interfaces.SimController;
import simugen.core.interfaces.SimEngine;
import simugen.core.interfaces.SimEvent;

/**
 * Under Construction. 
 * 
 * @author BASHH
 *
 */
public class DefaultSimController implements SimController
{

	@Override
	@Deprecated
	public List<SimEvent> process(SimEngine e, SimEvent... events)
	{
		return null;
	}

	@Override
	public List<SimEvent> getNextEvents(double next,
			List<SimComponent> components)
	{
		SimEvent first = null;

		for (SimComponent c : components)
		{
			if (c.canProduceEvent())
			{
				SimEvent newEvt = c.process(next);

				if (first != null)
				{
					if (first.getTime() > newEvt.getTime())
					{
						first = newEvt;
					}
				}
			}
		}

		return null;
	}
}
