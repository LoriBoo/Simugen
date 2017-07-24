package simugen.core.components.abstracts;

import simugen.core.components.interfaces.Server;
import simugen.core.defaults.DefaultServeElementContext;
import simugen.core.defaults.ServerCompletedEvent;
import simugen.core.enums.TimeUnit;
import simugen.core.interfaces.Component;
import simugen.core.interfaces.DataGenerator;
import simugen.core.interfaces.EngineTick;
import simugen.core.interfaces.Event;
import simugen.core.transfer.ElementTransferData;
import simugen.core.transfer.TransferInputPipe;
import simugen.core.transfer.TransferOutputPipe;

public abstract class AbstractServer extends AbstractComponent implements Server
{
	protected ElementTransferData currentData = null;

	protected final TransferInputPipe inputPipe = new TransferInputPipe(this);

	protected final TransferOutputPipe outputPipe = new TransferOutputPipe();

	// private Event completedEvent = null;

	private final DataGenerator<Number> serverTime;

	private final TimeUnit timeUnit;

	private long busyUntil = -1L;

	private boolean busy = false;

	public AbstractServer(DataGenerator<Number> serverTime, TimeUnit timeUnit)
	{
		this.serverTime = serverTime;

		this.timeUnit = timeUnit;

		final DefaultServeElementContext context = new DefaultServeElementContext(
				this);

		addProcessDataContext(ElementTransferData.class, context);
	}

	@Override
	public int getElementCapacity()
	{
		return -1;
	}

	@Override
	public boolean canReceiveElement(ElementTransferData pipeData)
	{
		final long time = pipeData.getTime();

		busy = time < busyUntil;

		return !busy;
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

	@Override
	protected void generateEvents(EngineTick tick)
	{
		// final long tickTime = tick.getEventTime(0);

		// If we have current data, generate a completion event.
		if (currentData != null && !busy)
		{
			final double rand = tick.getNextRand();

			final double raw = serverTime.getNext(rand).doubleValue();

			final long duration = timeUnit.getMillis(raw);

			final long time = duration + currentData.getTime();

			busyUntil = time;

			busy = true;

			final Event completedEvent = new ServerCompletedEvent(
					this.getLogID(), currentData.getData().getLogID(), time);

			super.events.add(completedEvent);
		}

		// If this tick time is greater than or equal to busyUntil, then check
		// if we can send the data
		if (currentData != null && outputPipe.canSendPipeData(currentData))
		{
			// If there's a completed event, then there better be current
			// data.
			assert currentData != null;

			long time = busyUntil;

			// If the completion event happened at a new engine tick, the
			// server sends the element at that engine tick's start time.
			if (time < tick.getEventTime(0))
			{
				time = tick.getEventTime(0);
			}

			final Component to = outputPipe.getUnion().getDownStreamPipe()
					.getOwner();

			final ElementTransferData data = new ElementTransferData(this, to,
					currentData.getData(), time);

			super.events.add(outputPipe.sendPipeData(data));

			currentData = null;

			// completedEvent = null;
		}
	}

	@Override
	protected boolean canGenerate()
	{
		return currentData != null && !busy;
	}

	@Override
	public void serveElement(ElementTransferData data)
	{
		this.currentData = data;
	}
}
