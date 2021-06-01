package simugen.core.components.abstracts;

import simugen.core.components.interfaces.Sink;
import simugen.core.defaults.ElementSunkEvent;
import simugen.core.interfaces.EngineTick;
import simugen.core.interfaces.Event;
import simugen.core.transfer.ElementTransferData;

public abstract class AbstractSink extends AbstractSingleInputPipeComponent
		implements Sink
{
	/**
	 * Sinks have infinite capacity.
	 */
	@Override
	public int getElementCapacity()
	{
		return -1;
	}

	@Override
	public void receiveElementTransferData(ElementTransferData data)
	{
		final Event evt = new ElementSunkEvent(data.getData(), data.getTime());

		super.events.add(evt);
	}

	/**
	 * Sinks don't generate events based on new random numbers.
	 */
	@Override
	protected void generateEvents(EngineTick tick)
	{

	}

	@Override
	protected boolean canGenerate()
	{
		return false;
	}
}
