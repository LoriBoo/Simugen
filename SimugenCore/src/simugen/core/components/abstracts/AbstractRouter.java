package simugen.core.components.abstracts;

import simugen.core.components.interfaces.Component;
import simugen.core.components.interfaces.Router;
import simugen.core.defaults.NullEngineTick;
import simugen.core.interfaces.EngineTick;
import simugen.core.transfer.ElementTransferData;
import simugen.core.transfer.TransferOutputPipe;

public abstract class AbstractRouter extends AbstractSingleInMultiOutPipeComponent implements Router {

	private ElementTransferData pipeData;

	private TransferOutputPipe transferOut;

	/**
	 * Like Queues, if we have a spot, we have a spot.
	 */
	@Override
	public void getEvents(EngineTick tick) {
		current = new NullEngineTick();

		super.getEvents(tick);
	}

	@Override
	public boolean canReceiveElement(ElementTransferData pipeData) {
		boolean can = false;

		for (Component c : mapPipes.keySet()) {
			if (c.canReceiveElement(pipeData)) {
				can = true;

				this.transferOut = mapPipes.get(c);
				
				this.pipeData = pipeData;
			}
		}

		return can;
	}

	@Override
	public void receiveElement(ElementTransferData data) {
		this.pipeData = data;
	}

	@Override
	public int getElementCapacity() {
		return 0;
	}

	@Override
	protected boolean canGenerate() {
		return !(pipeData == null);
	}

	@Override
	protected void generateEvents(EngineTick tick) {
		if (!(pipeData == null)) {			
			long time = tick.getEventTime(0);

			if (time < pipeData.getTime()) {
				time = pipeData.getTime();
			}

			final Component to = transferOut.getUnion().getDownStreamPipe().getOwner();

			final ElementTransferData eData = new ElementTransferData(this, to, pipeData.getData(), time);

			if (transferOut.canSendPipeData(eData)) {
				super.events.add(transferOut.sendPipeData(eData));
			}

			pipeData = null;
		}
	}
}
