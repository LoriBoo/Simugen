package simugen.core.abstracts;

import simugen.core.components.interfaces.Sink;
import simugen.core.transfer.interfaces.PipeData;

public abstract class AbstractSinkContext<V extends PipeData<? extends Object>>
		extends AbstractDataContext<Sink, V>
{
	public AbstractSinkContext(Sink componentContext)
	{
		super(componentContext);
	}
}
