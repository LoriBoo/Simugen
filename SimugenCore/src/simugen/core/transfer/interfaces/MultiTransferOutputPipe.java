package simugen.core.transfer.interfaces;

import simugen.core.components.interfaces.Component;
import simugen.core.transfer.TransferOutputPipe;

/**
 * {@link MultiTransferOutputPipe} {@link Component}s have multiple
 * {@link TransferOutputPipe}s. Pipes are stored and referenced based on the
 * {@link Component} they connect to.
 * 
 * @author Lorelei
 *
 */
public interface MultiTransferOutputPipe
{

	/**
	 * @param connected
	 *            The component the {@link TransferOutputPipe} will be connected
	 *            to.
	 * @return The {@link TransferOutputPipe}.
	 */
	public TransferOutputPipe getTransferOutputPipe(Component connected);

	/**
	 * This method not only creates the new {@link TransferOutputPipe}, but
	 * returns it as well, for ease of use.
	 * 
	 * @param connected
	 *            The component the {@link TransferOutputPipe} will be connected
	 *            to.
	 * @return The {@link TransferOutputPipe}.
	 */
	public TransferOutputPipe addTransferOutputPipe(Component connected);
}
