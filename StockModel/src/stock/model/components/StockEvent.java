package stock.model.components;

import simugen.core.interfaces.LoggingStyle;
import simugen.core.interfaces.SimEvent;

public class StockEvent implements SimEvent
{
	double growth;
	
	public StockEvent(double growth)
	{
		this.growth = growth;
	}
	
	public double getGrowth()
	{
		return this.growth;
	}
	
	@Override
	public String printEvent(LoggingStyle style)
	{
		if(style.equals(LoggingStyle.DEBUG))
		{
			return "[" + this.getClass().getName() + "] Stock growth simulated :" + growth;
		}
		return null;
	}

}
