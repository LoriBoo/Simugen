package simugen.core.abstracts;

import simugen.core.components.interfaces.Queue;
import simugen.core.transfer.interfaces.PipeData;

public abstract class AbstractQueueContext<V extends PipeData<? extends Object>>
		extends AbstractDataContext<Queue, V>
{

	public AbstractQueueContext(Queue componentContext)
	{
		super(componentContext);
	}

}
