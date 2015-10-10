package stock.model.components;

import simugen.core.interfaces.LoggingStyle;
import simugen.core.interfaces.SimEvent;

public class StockEvent implements SimEvent
{
	double value;
	
	double growth;
	
	public StockEvent(double value, double growth)
	{
		this.value = value;
		
		this.growth = growth;
	}
	
	public double getValue()
	{
		return this.value;
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
			return "[" + this.getClass().getName() + "] Stock value simulated at :" + value;
		}
		return null;
	}

}
