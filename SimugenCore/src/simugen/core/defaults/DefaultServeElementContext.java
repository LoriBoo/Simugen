package simugen.core.defaults;

import simugen.core.abstracts.AbstractServerContext;
import simugen.core.components.interfaces.Server;
import simugen.core.transfer.ElementTransferData;

public class DefaultServeElementContext
		extends AbstractServerContext<ElementTransferData>
{

	public DefaultServeElementContext(Server componentContext)
	{
		super(componentContext);
	}

	@Override
	protected void doProcessData(Server componentContext,
			ElementTransferData data)
	{
		componentContext.serveElement(data);
	}

}
