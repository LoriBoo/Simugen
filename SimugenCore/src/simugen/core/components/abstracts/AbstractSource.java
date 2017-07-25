package simugen.core.components.abstracts;

import simugen.core.components.interfaces.Source;
import simugen.core.defaults.ElementSourcedEvent;
import simugen.core.interfaces.Component;
import simugen.core.interfaces.DataGenerator;
import simugen.core.interfaces.EngineTick;
import simugen.core.transfer.ElementTransferData;
import simugen.core.transfer.TransferOutputPipe;

public abstract class AbstractSource extends AbstractSingleOutputPipeComponent
		implements Source
{
	protected final DataGenerator<ElementSourcedEvent> elementGenerator;

	protected final int arrivals;

	protected int sourced = 0;

	protected String ID = null;

	public AbstractSource(DataGenerator<ElementSourcedEvent> elementGenerator,
			int arrivals)
	{
		this.elementGenerator = elementGenerator;

		this.arrivals = arrivals;
	}

	/**
	 * Generate a new event for this engine tick.
	 * 
	 * Return null if there are no new events to create.
	 * 
	 * @param tick
	 * @return
	 */
	protected void generateEvents(EngineTick tick)
	{
		if (sourced < arrivals)
		{
			final TransferOutputPipe outputPipe = getTransferOutputPipe();

			final ElementSourcedEvent event = elementGenerator.getNext(tick);

			super.events.add(event);

			sourced++;

			final Component to = getTransferOutputPipe().getUnion()
					.getDownStreamPipe().getOwner();

			final ElementTransferData data = new ElementTransferData(this, to,
					event.getElement(), event.getTime());

			// Sources generate new elements, and send them on their way if they
			// can. If elements cannot flow, they exit the system abnormally.
			if (outputPipe.canSendPipeData(data))
			{
				super.events.add(outputPipe.sendPipeData(data));
			}
		}
	}

	/**
	 * Sources have no capacity.
	 */
	@Override
	public int getElementCapacity()
	{
		return 0;
	}
}
