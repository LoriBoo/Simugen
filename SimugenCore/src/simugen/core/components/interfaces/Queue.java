package simugen.core.components.interfaces;

import java.util.AbstractQueue;

import simugen.core.interfaces.Element;
import simugen.core.transfer.interfaces.SingleTransferInputPipe;
import simugen.core.transfer.interfaces.SingleTransferOutputPipe;

/**
 * Queues hold onto {@link Element}s until the downstream component is available
 * to receive an {@link Element}.<br>
 * <br>
 * <b>Subclasses should extend {@link AbstractQueue} in lieu of implementing
 * {@link Queue} directly</b>
 * 
 * @author Lorelei
 *
 */
public interface Queue
		extends Component, SingleTransferInputPipe, SingleTransferOutputPipe
{

	/**
	 * @return Whether or not the queue contains one or more element
	 */
	public boolean hasElement();
}
