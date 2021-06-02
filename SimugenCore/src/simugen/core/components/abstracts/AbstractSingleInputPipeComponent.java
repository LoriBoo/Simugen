package simugen.core.components.abstracts;

import simugen.core.components.interfaces.Component;
import simugen.core.defaults.DefaultElementTransferDataContext;
import simugen.core.transfer.ElementTransferData;
import simugen.core.transfer.TransferInputPipe;
import simugen.core.transfer.interfaces.SingleTransferInputPipe;
import simugen.core.transfer.interfaces.SingleTransferOutputPipe;

/**
 * Abstract implementation of {@link SingleTransferInputPipe}. <br>
 * <br>
 * Extends {@link AbstractComponent}, as the default implementation of
 * {@link SingleTransferOutputPipe} will be for extending
 * {@link Component}s.<br>
 * <br>
 * Users should first consider extending
 * {@link AbstractSingleInputPipeComponent} or other potential abstract classes
 * in lieu of implementing the interfaces directly, unless it's absolutely
 * necessary to create a non-{@link Component} Java {@link Object} that utilizes
 * the functionality. <br>
 * <br>
 * Abstract classes related to this one: <br>
 * <li>{@link AbstractSingleInMultiOutPipeComponent}</li>
 * <li>{@link AbstractSingleInSingleOutPipeComponent}</li>
 * <li>{@link AbstractSingleOutputPipeComponent}</li>
 * 
 * @author Lorelei
 *
 */
public abstract class AbstractSingleInputPipeComponent extends AbstractComponent
		implements SingleTransferInputPipe
{
	private final TransferInputPipe inputPipe = new TransferInputPipe(this);

	private final DefaultElementTransferDataContext<SingleTransferInputPipe> dataContext = new DefaultElementTransferDataContext<>(
			this);

	public AbstractSingleInputPipeComponent()
	{
		addProcessDataContext(ElementTransferData.class, dataContext);
	}

	@Override
	public TransferInputPipe getTransferInputPipe()
	{
		return inputPipe;
	}
}
