package simugen.core.transfer.interfaces;

import simugen.core.components.abstracts.AbstractSingleInputPipeComponent;
import simugen.core.components.interfaces.Component;
import simugen.core.interfaces.Element;
import simugen.core.transfer.ElementTransferData;
import simugen.core.transfer.TransferInputPipe;

/**
 * {@link Component}s which implement {@link SingleTransferInputPipe} have one
 * {@link TransferInputPipe} available to receive {@link Element}s via wrapped
 * class {@link ElementTransferData}.<br>
 * <br>
 * <b>Subclasses should extend {@link AbstractSingleInputPipeComponent} instead
 * of implementing {@link SingleTransferInputPipe} directly.</b>
 * 
 * @author Lorelei
 *
 */
public interface SingleTransferInputPipe {
	/**
	 * 
	 * @return The {@link TransferInputPipe} for this {@link Component}.
	 */
	public TransferInputPipe getTransferInputPipe();

	/**
	 * Classes that implement {@link SingleTransferInputPipe} will implement this
	 * method to process receiving {@link ElementTransferData} from upstream
	 * {@link Component}.
	 * 
	 * @param data
	 */
	public void receiveElementTransferData(ElementTransferData data);
}
