package simugen.core.components.interfaces;

import simugen.core.components.abstracts.AbstractSource;
import simugen.core.interfaces.Element;
import simugen.core.transfer.interfaces.SingleTransferOutputPipe;

/**
 * Sources generate {@link Element}s to traverse the system.<br>
 * <br>
 * <b>Subclasses should extend {@link AbstractSource} in lieu of implementing
 * {@link Source} directly.</b>
 * 
 * @author Lorelei
 *
 */
public interface Source extends Component, SingleTransferOutputPipe {

}
