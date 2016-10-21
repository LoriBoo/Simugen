package stock.model.components;

import simugen.core.abstracts.AbstractProcessResponseEvent;
import simugen.core.interfaces.LoggingStyle;

public class StockEvent extends AbstractProcessResponseEvent
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
		if (style.equals(LoggingStyle.DEBUG))
		{
			return "[" + this.getClass().getName()
					+ "] Stock growth simulated :" + growth;
		}
		return null;
	}

	@Override
	public long getTime()
	{
		// TODO Auto-generated method stub
		return 0;
	}
}
