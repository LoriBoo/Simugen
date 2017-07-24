package simugen.core.components.interfaces;

import simugen.core.interfaces.Component;
import simugen.core.interfaces.Element;
import simugen.core.transfer.interfaces.SingleTransferInputPipe;

/**
 * Sinks remove {@link Element}s from the system.
 * 
 * @author BASHH
 *
 */
public interface Sink extends Component, SingleTransferInputPipe
{
	/**
	 * Remove the element from the system.
	 * 
	 * @param element
	 */
	public void sinkElement(Element element, long time);
}
