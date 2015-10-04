package stock.model.components;

import simugen.core.interfaces.SimEvent;
import simugen.core.interfaces.SimEventListener;

public class StockEventListener implements SimEventListener
{

	@Override
	public void processEvent(SimEvent event)
	{
		if (event instanceof StockEvent)
		{
			final StockEvent se = (StockEvent) event;
			// TODO data processing
			System.out.println("Event Listened; new value = " + se.getValue());
		}
	}

}
