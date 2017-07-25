package simugen.core.transfer.interfaces;

import simugen.core.transfer.ElementTransferData;
import simugen.core.transfer.TransferInputPipe;

public interface SingleTransferInputPipe
{
	public TransferInputPipe getTransferInputPipe();

	public void receiveElement(ElementTransferData data);
}
