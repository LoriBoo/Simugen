package simugen.core.components.interfaces;

import simugen.core.transfer.TransferInputPipe;
import simugen.core.transfer.TransferOutputPipe;
import simugen.core.transfer.interfaces.MultiTransferOutputPipe;
import simugen.core.transfer.interfaces.SingleTransferInputPipe;

/**
 * Routers have one {@link TransferInputPipe} as well as multiple
 * {@link TransferOutputPipe}s, and classes that implement this interface will
 * provide the methods for determining when and which {@link TransferOutputPipe}
 * to send the {@link Element} out of.
 * 
 * @author Lorelei
 *
 */

public interface Router extends Component, SingleTransferInputPipe, MultiTransferOutputPipe {

}
