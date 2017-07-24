package simugen.core.defaults;

import simugen.core.abstracts.AbstractSinkContext;
import simugen.core.components.interfaces.Sink;
import simugen.core.transfer.ElementTransferData;

public class DefaultElementSunkContext extends AbstractSinkContext<ElementTransferData>
{

	public DefaultElementSunkContext(Sink componentContext)
	{
		super(componentContext);
	}

	@Override
	protected void doProcessData(Sink componentContext, ElementTransferData data)
	{
		componentContext.sinkElement(data.getData(), data.getTime());
	}
}
