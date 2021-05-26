package simugen.core.transfer.interfaces;

import simugen.core.components.interfaces.Component;
import simugen.core.transfer.TransferOutputPipe;

public interface MultiTransferOutputPipe {
	public TransferOutputPipe getTransferOutputPipe(Component connected);

	public TransferOutputPipe addTransferOutputPipe(Component connected);
}
