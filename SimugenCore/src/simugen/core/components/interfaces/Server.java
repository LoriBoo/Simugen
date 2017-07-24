package simugen.core.components.interfaces;

import simugen.core.interfaces.Component;
import simugen.core.transfer.ElementTransferData;
import simugen.core.transfer.interfaces.SingleTransferInputPipe;
import simugen.core.transfer.interfaces.SingleTransferOutputPipe;

public interface Server
		extends Component, SingleTransferInputPipe, SingleTransferOutputPipe
{
	void serveElement(ElementTransferData data);
}
