package simugen.core.defaults;

import simugen.core.abstracts.AbstractQueueContext;
import simugen.core.components.interfaces.Queue;
import simugen.core.transfer.ElementTransferData;

public class DefaultElementQueuedContext
		extends AbstractQueueContext<ElementTransferData>
{
	public DefaultElementQueuedContext(Queue componentContext)
	{
		super(componentContext);
	}

	@Override
	protected void doProcessData(Queue componentContext, ElementTransferData data)
	{
		componentContext.queueElement(data);
	}
}
