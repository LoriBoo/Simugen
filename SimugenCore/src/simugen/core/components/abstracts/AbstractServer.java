package simugen.core.components.abstracts;

import java.util.concurrent.TimeUnit;

import simugen.core.components.interfaces.Component;
import simugen.core.components.interfaces.Server;
import simugen.core.defaults.ServerCompletedEvent;
import simugen.core.interfaces.DataGenerator;
import simugen.core.interfaces.EngineTick;
import simugen.core.interfaces.Event;
import simugen.core.transfer.ElementTransferData;
import simugen.core.transfer.TransferOutputPipe;

public abstract class AbstractServer extends AbstractSingleInSingleOutPipeComponent implements Server {
	protected ElementTransferData currentData = null;

	private Event completedEvent = null;

	private final DataGenerator<Number> serverTime;

	private final TimeUnit timeUnit;

	public AbstractServer(DataGenerator<Number> serverTime, TimeUnit timeUnit) {
		this.serverTime = serverTime;

		this.timeUnit = timeUnit;
	}

	/**
	 * Since the server overrides {@link #canGenerate()}, this method is redundant.
	 * But for good form does let other components know if it has any capacity.
	 */
	@Override
	public int getElementCapacity() {
		return currentData == null ? 1 : 0;
	}

	@Override
	public boolean canReceiveElement(ElementTransferData pipeData) {
		return completedEvent == null && currentData == null;
	}

	/**
	 * Server generateEvents has two separate components to it; If there is current
	 * data, but no completion event has been created, then the component creates a
	 * {@link ServerCompletedEvent}. This event is stored as a field and the next
	 * tick of generateEvents cannot fire until the event is consumed.
	 * 
	 * Once the event is consumed, the Server checks for and passes the component
	 * downstream if possible.
	 */
	@Override
	protected void generateEvents(EngineTick tick) {
		final TransferOutputPipe outputPipe = getTransferOutputPipe();

		// If we have current data, generate a completion event.
		if (currentData != null && completedEvent == null) {
			final double rand = tick.getNextRand();

			final double raw = serverTime.getNext(rand).doubleValue();

			final long duration = timeUnit.toMillis((long) raw);

			// The time at which this event fires is the duration of the server
			// + the timestamp at which the element was received
			final long time = duration + currentData.getTime();

			completedEvent = new ServerCompletedEvent(this.getLogID(), currentData.getData().getLogID(), time);

			super.events.add(completedEvent);
		}

		// If this tick time is greater than or equal to busyUntil, then check
		// if we can send the data
		if (currentData != null && completedEvent.isConsumed() && outputPipe.canSendPipeData(currentData)) {
			final long time = tick.getEventTime(0);

			final Component to = outputPipe.getUnion().getDownStreamPipe().getOwner();

			final ElementTransferData data = new ElementTransferData(this, to, currentData.getData(), time);

			super.events.add(outputPipe.sendPipeData(data));

			currentData = null;

			completedEvent = null;
		}
	}

	/**
	 * The server component can only generate new events if there either is no data,
	 * or the completed event was consumed.
	 * 
	 * Events are consumed by the engine, which means that the event has actually
	 * "occurred" and the Component can continue with its processes
	 */
	@Override
	protected boolean canGenerate() {
		return currentData != null || (completedEvent == null ? false : completedEvent.isConsumed());
	}

	@Override
	public void receiveElement(ElementTransferData data) {
		this.currentData = data;
	}
}
