package simugen.core.components.abstracts;

import simugen.core.components.interfaces.Sink;
import simugen.core.defaults.DefaultElementSunkContext;
import simugen.core.defaults.ElementSunkEvent;
import simugen.core.interfaces.Element;
import simugen.core.interfaces.EngineTick;
import simugen.core.interfaces.Event;
import simugen.core.transfer.ElementTransferData;
import simugen.core.transfer.TransferInputPipe;

public abstract class AbstractSink extends AbstractComponent implements Sink
{
	private final TransferInputPipe inputPipe = new TransferInputPipe(this);

	private final DefaultElementSunkContext elementSunkContext = new DefaultElementSunkContext(
			this);

	public AbstractSink()
	{
		addProcessDataContext(ElementTransferData.class, elementSunkContext);
	}

	@Override
	public TransferInputPipe getTransferInputPipe()
	{
		return this.inputPipe;
	}

	@Override
	public void sinkElement(Element element, long time)
	{
		final Event evt = new ElementSunkEvent(element, time);

		super.events.add(evt);
	}

	/**
	 * Sinks have infinite capacity.
	 */
	@Override
	public int getElementCapacity()
	{
		return -1;
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
