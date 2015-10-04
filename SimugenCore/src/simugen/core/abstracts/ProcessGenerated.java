package simugen.core.abstracts;

import simugen.core.interfaces.DataGenerator;
import simugen.core.interfaces.EventProcess;
import simugen.core.interfaces.SimEvent;

public abstract class ProcessGenerated<T> implements EventProcess
{
	protected DataGenerator<T> generator = null;
	
	public ProcessGenerated (DataGenerator<T> generator)
	{
		this.generator = generator;
	}
	
	abstract public SimEvent process(T next);
	
	@Override
	public SimEvent process(double d)
	{
		return process(generator.getNext(d));
	}

}
