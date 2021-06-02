package simugen.core.components.abstracts;

import java.util.HashMap;
import java.util.Map;

import simugen.core.components.interfaces.Component;
import simugen.core.defaults.DefaultElementTransferDataContext;
import simugen.core.transfer.ElementTransferData;
import simugen.core.transfer.TransferInputPipe;
import simugen.core.transfer.TransferOutputPipe;
import simugen.core.transfer.interfaces.MultiTransferOutputPipe;
import simugen.core.transfer.interfaces.SingleTransferInputPipe;

/**
 * Abstract implementation of a component that has both one single
 * {@link TransferInputPipe} and multiple {@link TransferOutputPipe}. Implements
 * both {@link SingleTransferInputPipe} and {@link MultiTransferOutputPipe} <br>
 * <br>
 * Extends {@link AbstractComponent}, as the default implementation will be for
 * extending {@link Component}s.<br>
 * <br>
 * Users should first consider extending
 * {@link AbstractSingleInMultiOutPipeComponent} or other potential abstract
 * classes in lieu of implementing the interfaces directly, unless it's
 * absolutely necessary to create a non-{@link Component} Java {@link Object}
 * that utilizes the functionality. <br>
 * <br>
 * Abstract classes related to this one: <br>
 * <li>{@link AbstractSingleInSingleOutPipeComponent}</li>
 * <li>{@link AbstractSingleInputPipeComponent}</li>
 * <li>{@link AbstractSingleOutputPipeComponent}</li>
 * 
 * @author Lorelei
 *
 */
public abstract class AbstractSingleInMultiOutPipeComponent
		extends AbstractComponent
		implements MultiTransferOutputPipe, SingleTransferInputPipe
{

	protected Map<Component, TransferOutputPipe> mapPipes = new HashMap<>();

	private final TransferInputPipe inputPipe = new TransferInputPipe(this);

	private final DefaultElementTransferDataContext<AbstractSingleInMultiOutPipeComponent> dataContext = new DefaultElementTransferDataContext<>(
			this);

	public AbstractSingleInMultiOutPipeComponent()
	{
		addProcessDataContext(ElementTransferData.class, dataContext);
	}

	@Override
	public TransferInputPipe getTransferInputPipe()
	{
		return inputPipe;
	}

	@Override
	public TransferOutputPipe addTransferOutputPipe(Component connected)
	{
		mapPipes.put(connected, new TransferOutputPipe());

		return mapPipes.get(connected);
	}

	@Override
	public TransferOutputPipe getTransferOutputPipe(Component connected)
	{
		return mapPipes.get(connected);
	}

}
