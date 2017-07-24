package simugen.core.transfer;

import simugen.core.transfer.interfaces.InputPipe;
import simugen.core.transfer.interfaces.OutputPipe;
import simugen.core.transfer.interfaces.PipeUnion;

public final class TransferPipeUnion implements PipeUnion<ElementTransferData>
{
	private final TransferOutputPipe upstreamPipe;

	private final TransferInputPipe downstreamPipe;

	public TransferPipeUnion(TransferOutputPipe upstreamPipe,
			TransferInputPipe downstreamPipe)
	{
		this.upstreamPipe = upstreamPipe;

		this.downstreamPipe = downstreamPipe;
	}

	@Override
	public OutputPipe<ElementTransferData, ?> getUpstreamPipe()
	{
		return this.upstreamPipe;
	}

	@Override
	public InputPipe<ElementTransferData> getDownStreamPipe()
	{
		return this.downstreamPipe;
	}
}
