package simugen.core.transfer;

import simugen.core.defaults.ElementTransferEvent;
import simugen.core.interfaces.Event;
import simugen.core.transfer.interfaces.OutputPipe;
import simugen.core.transfer.interfaces.PipeUnion;

public final class TransferOutputPipe
		implements OutputPipe<ElementTransferData, TransferInputPipe>
{
	protected TransferPipeUnion pipeUnion = null;

	/**
	 * Check if we can send pipe data.
	 */
	@Override
	public boolean canSendPipeData(ElementTransferData pipeData)
	{
		return pipeUnion.getDownStreamPipe().isReady(pipeData);
	}

	/**
	 * Send the {@link ElementTransferData} to the transferInputPipe
	 */
	@Override
	public Event sendPipeData(ElementTransferData pipeData)
	{
		final Event event = new ElementTransferEvent(pipeData.getData(),
				pipeData.getSentFrom(), pipeData.getSentTo(),
				pipeData.getTime());

		pipeUnion.getDownStreamPipe().getPipeData(pipeData);

		return event;
	}

	/**
	 * Union the passed transferInputPipe to this TransferOutputPipe.
	 * 
	 * @throws AssertionError
	 *             if set more than once.
	 */
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
