package simugen.core.components.abstracts;

import simugen.core.components.interfaces.Component;
import simugen.core.components.interfaces.Router;
import simugen.core.defaults.NullEngineTick;
import simugen.core.interfaces.Element;
import simugen.core.interfaces.EngineTick;
import simugen.core.transfer.ElementTransferData;
import simugen.core.transfer.TransferOutputPipe;

/**
 * Abstract implementation of {@link Router}.<br>
 * <br>
 * <b>Subclasses should extend {@link AbstractRouter} instead of implementing
 * {@link Router} directly.</b>
 * 
 * @author Lorelei
 *
 */
public abstract class AbstractRouter
		extends AbstractSingleInMultiOutPipeComponent implements Router
{

	private ElementTransferData pipeData;

	private TransferOutputPipe transferOut;

	/**
	 * Some {@link Component}s are allowed to generate events even on the same
	 * engine tick. Setting the <b>current</b> {@link EngineTick} to a new
	 * {@link NullEngineTick} allows super classes to generate new events if
	 * they can.
	 */
	@Override
	public void getEvents(EngineTick tick)
	{
		current = new NullEngineTick();

		super.getEvents(tick);
	}

	/**
	 * Routers are pass through {@link Component}s. They can receive
	 * {@link Element}s as long as there is a downstream component that is
	 * available to receive an {@link Element}.
	 * 
	 * @return <b>True</b> if the {@link Router} can receive an {@link Element},
	 *         <b>False</b> otherwise.
	 */
	@Override
	public boolean canReceiveElement(ElementTransferData pipeData)
	{
		boolean can = false;

		// For every TranferOutputPipe that this Component has, find one that is
		// available to receive. On the first available, break out of the for
		// loop, no
		// need to check other components.
		for (Component c : mapPipes.keySet())
		{
			if (c.canReceiveElement(pipeData))
			{
				can = true;

				this.transferOut = mapPipes.get(c);

				this.pipeData = pipeData;

				break;
			}
		}

		return can;
	}

	@Override
	public void receiveElementTransferData(ElementTransferData data)
	{
		this.pipeData = data;
	}

	/**
	 * Method {@link #getElementCapacity()} is unused by {@link Component} type
	 * {@link Router}; {@link #canReceiveElement(ElementTransferData)} is true
	 * if there exists a downstream {@link Component} available to receive.
	 */
	@Override
	public int getElementCapacity()
	{
		return 0;
	}

	@Override
	protected boolean canGenerate()
	{
		return !(pipeData == null);
	}

	@Override
	protected void generateEvents(EngineTick tick)
	{
		if (pipeData != null && transferOut != null)
		{
			long time = tick.getEventTime(0);

			if (time < pipeData.getTime())
			{
				time = pipeData.getTime();
			}

			final Component to = transferOut.getUnion().getDownStreamPipe()
					.getOwner();

			final ElementTransferData eData = new ElementTransferData(this, to,
					pipeData.getData(), time);

			if (transferOut.canSendPipeData(eData))
			{
				super.events.add(transferOut.sendPipeData(eData));
			}

			// After we've used the data, and the TransferOutputPipe, set them
			// to null, so
			// no extra events are generated.
			pipeData = null;

			transferOut = null;
		}
	}
}
