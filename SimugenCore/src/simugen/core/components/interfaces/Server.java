package simugen.core.components.interfaces;

import simugen.core.components.abstracts.AbstractServer;
import simugen.core.interfaces.Element;
import simugen.core.transfer.interfaces.SingleTransferInputPipe;
import simugen.core.transfer.interfaces.SingleTransferOutputPipe;

/**
 * Servers hold {@link Element}s for a determinate amount of time.<br>
 * <br>
 * <b>Subclasses should extend {@link AbstractServer} in lieu of implementing
 * {@link Server} directly.</b>
 * 
 * @author Lorelei
 *
 */
public interface Server
		extends Component, SingleTransferInputPipe, SingleTransferOutputPipe
{

}
