package simugen.core.components.abstracts;

import simugen.core.components.interfaces.Component;
import simugen.core.transfer.TransferOutputPipe;
import simugen.core.transfer.interfaces.SingleTransferOutputPipe;

/**
 * Abstract implementation of {@link SingleTransferOutputPipe}. <br>
 * <br>
 * Extends {@link AbstractComponent}, as the default implementation of
 * {@link SingleTransferOutputPipe} will be for extending
 * {@link Component}s.<br>
 * <br>
 * Users should first consider extending
 * {@link AbstractSingleOutputPipeComponent} or other potential abstract classes
 * in lieu of implementing the interfaces directly, unless it's absolutely
 * necessary to create a non-{@link Component} Java {@link Object} that utilizes
 * the functionality. <br>
 * <br>
 * Abstract classes related to this one: <br>
 * <li>{@link AbstractSingleInMultiOutPipeComponent}</li>
 * <li>{@link AbstractSingleInputPipeComponent}</li>
 * <li>{@link AbstractSingleInSingleOutPipeComponent}</li>
 * 
 * @author Lorelei
 *
 */
public abstract class AbstractSingleOutputPipeComponent
		extends AbstractComponent implements SingleTransferOutputPipe
{
	private final TransferOutputPipe outputPipe = new TransferOutputPipe();

	@Override
	public TransferOutputPipe getTransferOutputPipe()
	{
		return outputPipe;
	}
}
