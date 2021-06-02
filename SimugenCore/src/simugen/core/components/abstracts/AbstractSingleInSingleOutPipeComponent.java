package simugen.core.components.abstracts;

import simugen.core.components.interfaces.Component;

import simugen.core.interfaces.DataContext;
import simugen.core.transfer.ElementTransferData;
import simugen.core.transfer.TransferInputPipe;
import simugen.core.transfer.TransferOutputPipe;
import simugen.core.transfer.interfaces.SingleTransferInputPipe;
import simugen.core.transfer.interfaces.SingleTransferOutputPipe;

/**
 * Abstract implementation of a component that has both one single
 * {@link TransferInputPipe} and one single {@link TransferOutputPipe}.
 * Implements both {@link SingleTransferInputPipe} and
 * {@link SingleTransferOutputPipe} <br>
 * <br>
 * Extends {@link AbstractComponent}, as the default implementation will be for
 * extending {@link Component}s.<br>
 * <br>
 * Users should first consider extending
 * {@link AbstractSingleInSingleOutPipeComponent} or other potential abstract
 * classes in lieu of implementing the interfaces directly, unless it's
 * absolutely necessary to create a non-{@link Component} Java {@link Object}
 * that utilizes the functionality. <br>
 * <br>
 * Abstract classes related to this one: <br>
 * <li>{@link AbstractSingleInMultiOutPipeComponent}</li>
 * <li>{@link AbstractSingleInputPipeComponent}</li>
 * <li>{@link AbstractSingleOutputPipeComponent}</li>
 * 
 * @author Lorelei
 *
 */
public abstract class AbstractSingleInSingleOutPipeComponent
		extends AbstractComponent
		implements SingleTransferInputPipe, SingleTransferOutputPipe
{
	private final TransferInputPipe inputPipe = new TransferInputPipe(this);

	private final TransferOutputPipe outputPipe = new TransferOutputPipe();

	private final DataContext<Component, ElementTransferData> dataContext = new DataContext<Component, ElementTransferData>()
	{
		@Override
		public void processData(ElementTransferData data)
		{
			AbstractSingleInSingleOutPipeComponent.this
					.receiveElementTransferData(data);
		}
	};

	public AbstractSingleInSingleOutPipeComponent()
	{
		addProcessDataContext(ElementTransferData.class, dataContext);
	}

	@Override
	public TransferInputPipe getTransferInputPipe()
	{
		return inputPipe;
	}

	@Override
	public TransferOutputPipe getTransferOutputPipe()
	{
		return outputPipe;
	}
}
