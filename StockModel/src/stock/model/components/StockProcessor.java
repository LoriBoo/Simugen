package stock.model.components;

import simugen.core.defaults.DefaultProcessor;
import simugen.core.interfaces.SimEvent;

public class StockProcessor extends DefaultProcessor
{
	int runs = 0;
	
	int hasRun = 0;
	
	public StockProcessor(int runs)
	{
		this.runs = runs;
	}
	
	@Override
	public boolean isComplete()
	{
		return hasRun == runs;
	}
	
	@Override
	public SimEvent process(double d)
	{
		hasRun++;
		return super.process(d);
	}
}
