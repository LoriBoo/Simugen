package simugen.core.defaults;

import java.util.ArrayList;
import java.util.List;

import simugen.core.interfaces.SimEvent;
import simugen.core.interfaces.SimTimeController;

public class DefaultSimTimeController implements SimTimeController
{
	@Override
	public List<SimEvent> process(SimEvent... events)
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
			
			e.setProcessedResponse(true, evt.contains(e));
		}

		return evt;
	}

}
