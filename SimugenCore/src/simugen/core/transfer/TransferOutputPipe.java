package simugen.core.transfer;

import simugen.core.components.interfaces.Component;
import simugen.core.defaults.ElementTransferEvent;
import simugen.core.interfaces.Element;
import simugen.core.interfaces.Event;
import simugen.core.transfer.interfaces.OutputPipe;
import simugen.core.transfer.interfaces.PipeUnion;

/**
 * {@link TransferOutputPipe}s send {@link Element}s from one {@link Component}
 * to another, by being unioned to a {@link TransferInputPipe}.
 * 
 * @author Lorelei
 *
 */
public final class TransferOutputPipe
		implements OutputPipe<ElementTransferData, TransferInputPipe>
{
	protected TransferPipeUnion pipeUnion = null;

	@Override
	public boolean canSendPipeData(ElementTransferData pipeData)
	{
		return pipeUnion.getDownStreamPipe().isReady(pipeData);
	}

	@Override
	public Event sendPipeData(ElementTransferData pipeData)
	{
		final Event event = new ElementTransferEvent(pipeData.getData(),
				pipeData.getSentFrom(), pipeData.getSentTo(),
				pipeData.getTime());

		pipeUnion.getDownStreamPipe().getPipeData(pipeData);

		return event;
	}

	@Override
	public void union(TransferInputPipe transferInputPipe)
	{
		assert this.pipeUnion == null;

		assert transferInputPipe instanceof TransferInputPipe;

		pipeUnion = new TransferPipeUnion(this, transferInputPipe);
	}

	@Override
	public PipeUnion<ElementTransferData> getUnion()
	{
		return pipeUnion;
	}

}
