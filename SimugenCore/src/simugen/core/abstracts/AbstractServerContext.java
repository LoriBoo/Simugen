package simugen.core.abstracts;

import simugen.core.components.interfaces.Server;
import simugen.core.transfer.interfaces.PipeData;

public abstract class AbstractServerContext<V extends PipeData<? extends Object>>
		extends AbstractDataContext<Server, V>
{

	public AbstractServerContext(Server componentContext)
	{
		super(componentContext);
	}

}
