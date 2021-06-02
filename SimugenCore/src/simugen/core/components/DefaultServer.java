package simugen.core.components;

import java.util.concurrent.TimeUnit;

import simugen.core.components.abstracts.AbstractServer;
import simugen.core.interfaces.DataGenerator;

final public class DefaultServer extends AbstractServer
{
	public DefaultServer(DataGenerator<Number> serverTime, TimeUnit timeUnit)
	{
		super(serverTime, timeUnit);
	}
}
