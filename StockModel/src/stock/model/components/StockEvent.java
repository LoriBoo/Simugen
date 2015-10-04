package stock.model.components;

import simugen.core.interfaces.LoggingStyle;
import simugen.core.interfaces.SimEvent;

public class StockEvent implements SimEvent
{
	double value;
	
	public StockEvent(double value)
	{
		this.value = value;
	}
	
	public double getValue()
	{
		return this.value;
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
