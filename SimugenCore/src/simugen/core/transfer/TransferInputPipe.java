package simugen.core.transfer;

import simugen.core.components.interfaces.Component;
import simugen.core.interfaces.Element;
import simugen.core.transfer.interfaces.InputPipe;
import simugen.core.transfer.interfaces.PipeData;

/**
 * {@link TransferInputPipe}s are implementations of the interface
 * {@link InputPipe} for the {@link PipeData} subclass
 * {@link ElementTransferData}. These {@link InputPipe}s receive
 * {@link Element}s wrapped in {@link ElementTransferData}.
 * 
 * @author Lorelei
 *
 */
public final class TransferInputPipe implements InputPipe<ElementTransferData> {
	private final Component owner;

	public TransferInputPipe(Component owner) {
		this.owner = owner;
	}
	
	@Override
	public void getPipeData(ElementTransferData pipeData) {
		this.owner.receiveData(pipeData);
	}

	@Override
	public boolean isReady(ElementTransferData pipeData) {
		return owner.canReceiveElement(pipeData);
	}

	@Override
	public Component getOwner() {
		return owner;
	}
}
