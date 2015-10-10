package stock.model.components;

import simugen.core.interfaces.SimEvent;
import simugen.core.interfaces.SimEventListener;
import stock.model.data.StockData;

public class StockEventListener implements SimEventListener
{

	private final StockData data;
	
	public StockEventListener(StockData data)
	{
		this.data = data;
	}
	
	@Override
	public void processEvent(SimEvent event)
	{
		if (event instanceof StockEvent)
		{
			final StockEvent se = (StockEvent) event;
			
			data.addValue(se.getValue(), se.growth);
		}
	}

}
