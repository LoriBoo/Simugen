package simugen.core.components.interfaces;

import simugen.core.interfaces.Element;
import simugen.core.transfer.interfaces.SingleTransferInputPipe;
import simugen.core.transfer.interfaces.SingleTransferOutputPipe;

/**
 * Servers hold {@link Element}s for a determinate amount of time.
 * 
 * @author Lorelei
 *
 */
public interface Server extends Component, SingleTransferInputPipe, SingleTransferOutputPipe {

}
