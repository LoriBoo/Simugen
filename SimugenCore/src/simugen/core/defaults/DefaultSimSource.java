package simugen.core.defaults;

import simugen.core.abstracts.AbstractSimSource;
import simugen.core.interfaces.DataGenerator;
import simugen.core.interfaces.SimMessage;

public class DefaultSimSource extends AbstractSimSource
{
	public DefaultSimSource(DataGenerator<? extends Number> interarrivalRate,
			int arrivals)
	{
		super(interarrivalRate, arrivals);
	}

	@Override
	public void doRecieveMessage(SimMessage message)
	{

	}
}
