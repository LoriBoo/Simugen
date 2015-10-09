package stock.model.components;

import simugen.core.abstracts.ProcessGenerated;
import simugen.core.interfaces.DataGenerator;
import simugen.core.interfaces.SimEvent;

public class StockProcess extends ProcessGenerated<Number>
{
	private double value;

	public StockProcess(DataGenerator<Number> generator, Double initial)
	{
		super(generator);

		value = initial;
	}

	@Override
	public boolean isComplete()
	{
		return true;
	}

	@Override
	public SimEvent process(Number next)
	{
		double delta = next.doubleValue() * value;

		value += delta;

		return new StockEvent(value);
	}

}
