package simugen.core.components.abstracts;

import simugen.core.interfaces.Component;
import simugen.core.interfaces.DataContext;
import simugen.core.transfer.ElementTransferData;
import simugen.core.transfer.TransferInputPipe;
import simugen.core.transfer.interfaces.SingleTransferInputPipe;

public abstract class AbstractSingleInputPipeComponent extends AbstractComponent
		implements SingleTransferInputPipe
{
	private final TransferInputPipe inputPipe = new TransferInputPipe(this);

	private final DataContext<Component, ElementTransferData> dataContext = new DataContext<Component, ElementTransferData>()
	{
		@Override
		public void processData(ElementTransferData data)
		{
			AbstractSingleInputPipeComponent.this.receiveElement(data);
		}
	};

	public AbstractSingleInputPipeComponent()
	{
		addProcessDataContext(ElementTransferData.class, dataContext);
	}

	@Override
	public TransferInputPipe getTransferInputPipe()
	{
		return inputPipe;
	}
}
