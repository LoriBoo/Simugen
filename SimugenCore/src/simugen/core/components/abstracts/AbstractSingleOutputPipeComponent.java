package simugen.core.components.abstracts;

import simugen.core.transfer.TransferOutputPipe;
import simugen.core.transfer.interfaces.SingleTransferOutputPipe;

public abstract class AbstractSingleOutputPipeComponent
		extends AbstractComponent implements SingleTransferOutputPipe
{
	private final TransferOutputPipe outputPipe = new TransferOutputPipe();

	@Override
	public TransferOutputPipe getTransferOutputPipe()
	{
		return outputPipe;
	}
}
