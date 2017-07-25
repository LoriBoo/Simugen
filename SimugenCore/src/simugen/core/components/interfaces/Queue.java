package simugen.core.components.interfaces;

import simugen.core.interfaces.Component;
import simugen.core.transfer.interfaces.SingleTransferInputPipe;
import simugen.core.transfer.interfaces.SingleTransferOutputPipe;

public interface Queue
		extends Component, SingleTransferInputPipe, SingleTransferOutputPipe
{

}
