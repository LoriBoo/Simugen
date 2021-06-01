package simugen.core.transfer.interfaces;

import simugen.core.components.interfaces.Component;
import simugen.core.transfer.ElementTransferData;
import simugen.core.transfer.TransferOutputPipe;

/**
 * {@link SingleTransferOutputPipe}s have one {@link TransferOutputPipe}.
 * {@link TransferOutputPipe}s are used to transfer
 * {@link ElementTransferData}<br>
 * <br>
 * This interface does not extend {@link Component} currently, to allow advanced
 * users to create non-component Java {@link Object}s that can utilize the same
 * aspect. <b>This implementation is candidate for change.</b>
 * 
 * @author Lorelei
 *
 */
public interface SingleTransferOutputPipe {

	/**
	 * @return The one {@link TransferOutputPipe} that exists on this
	 *         {@link Object}.
	 */
	public TransferOutputPipe getTransferOutputPipe();
}
