package simugen.core.components.abstracts;

import simugen.core.interfaces.Component;
import simugen.core.interfaces.DataContext;
import simugen.core.transfer.ElementTransferData;
import simugen.core.transfer.TransferInputPipe;
import simugen.core.transfer.TransferOutputPipe;
import simugen.core.transfer.interfaces.SingleTransferInputPipe;
import simugen.core.transfer.interfaces.SingleTransferOutputPipe;

public abstract class AbstractSingleInSingleOutPipeComponent
		extends AbstractComponent
		implements SingleTransferInputPipe, SingleTransferOutputPipe
{
	private final TransferInputPipe inputPipe = new TransferInputPipe(this);

	private final TransferOutputPipe outputPipe = new TransferOutputPipe();

	private final DataContext<Component, ElementTransferData> dataContext = new DataContext<Component, ElementTransferData>()
	{
		@Override
		public void processData(ElementTransferData data)
		{
			AbstractSingleInSingleOutPipeComponent.this.receiveElement(data);
		}
	};

	public AbstractSingleInSingleOutPipeComponent()
	{
		addProcessDataContext(ElementTransferData.class, dataContext);
	}

	@Override
	public TransferInputPipe getTransferInputPipe()
	{
		return inputPipe;
	}

	@Override
	public TransferOutputPipe getTransferOutputPipe()
	{
		return outputPipe;
	}
}
