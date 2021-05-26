package simugen.core.transfer.interfaces;

import simugen.core.components.interfaces.Component;
import simugen.core.transfer.TransferInputPipe;
import simugen.core.transfer.TransferOutputPipe;

public interface MultiTransferOutputPipe {
	public TransferOutputPipe getTransferOutputPipe(Component connected);

	public void addTransferOutputPipe(Component connected, TransferInputPipe pipe);
}
