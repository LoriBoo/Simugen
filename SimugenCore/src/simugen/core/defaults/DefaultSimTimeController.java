package simugen.core.defaults;

import java.util.ArrayList;
import java.util.List;

import simugen.core.interfaces.SimComponent;
import simugen.core.interfaces.SimController;
import simugen.core.interfaces.SimEngine;
import simugen.core.interfaces.SimEvent;

/**
 * Deprecated, not sure how I want to do this.
 * 
 * @author BASHH
 *
 */
@Deprecated
public class DefaultSimTimeController implements SimController
{
	@Override
	@Deprecated
	public List<SimEvent> process(SimEngine ee, SimEvent... events)
	{
		long time = -1L;

		List<SimEvent> evt = new ArrayList<>();

		for (SimEvent e : events)
		{
			if (e.getTime() < time || time == -1L)
			{
				time = e.getTime();
			}
		}

		for (SimEvent e : events)
		{
			if(e.getTime() <= time)
			{
				evt.add(e);
			}
			
//			e.setProcessedResponse(true, evt.contains(e));
			
		}

		return evt;
	}

	@Override
	public List<SimEvent> getNextEvents(double next,
			List<SimComponent> components)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
