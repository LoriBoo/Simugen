package simugen.core.components;

import simugen.core.components.abstracts.AbstractServer;
import simugen.core.enums.TimeUnit;
import simugen.core.interfaces.DataGenerator;

public class DefaultServer extends AbstractServer
{
	public DefaultServer(DataGenerator<Number> serverTime, TimeUnit timeUnit)
	{
		super(serverTime, timeUnit);
	}
}
