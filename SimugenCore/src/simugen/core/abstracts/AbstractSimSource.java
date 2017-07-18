package simugen.core.abstracts;

import java.util.ArrayList;
import java.util.List;

import simugen.core.defaults.DefaultSimElement;
import simugen.core.interfaces.DataGenerator;
import simugen.core.interfaces.SimComponent;
import simugen.core.interfaces.SimElement;
import simugen.core.interfaces.SimEngine;
import simugen.core.interfaces.SimInputHook;
import simugen.core.interfaces.SimMessage;
import simugen.core.interfaces.SimSource;

abstract public class AbstractSimSource extends AbstractSimSingleOutputHook
		implements SimSource
{
	final protected DataGenerator<? extends Number> interarrivalRate;

	final int initialArrival;

	final int arrivals;

	int current = 0;

	protected SimInputHook downStreamHook = null;

	/**
	 * Create a new {@link SimSource} that generates {@link SimElement}s
	 * 
	 * @param interarrivalRate
	 *            The distribution that represents the interarrival rate for the
	 *            SimElements
	 * @param arrivals
	 *            How many arrivals for the {@link SimSource} to create
	 */
	public AbstractSimSource(DataGenerator<? extends Number> interarrivalRate,
			int arrivals)
	{
		this.initialArrival = 0;

		this.arrivals = arrivals;

		this.interarrivalRate = interarrivalRate;
	}

	@Override
	public List<SimMessage> doGetMessages(SimEngine engine, long tick)
	{
		if (current == arrivals)
		{
			return null;
		}

		final List<SimMessage> m = new ArrayList<>();

		// The arrival in model time.
		long arrival = interarrivalRate.getNext(engine.getNext()).longValue()
				+ engine.getMilliseconds();

		current++;

		final SimElement element = new DefaultSimElement();

		final SimComponent downstream = this.getOutputHook().getHookedTo()
				.getSimHookOwner();

		this.sendTransferRequestMessage(arrival, downstream, element);

		return m;
	}

	/**
	 * Sources can never receive an element.
	 */
	@Override
	public boolean canRecieveElement()
	{
		return false;
	}

	/**
	 * Do nothing.
	 */
	@Override
	public void doRecieveElement(SimElement element)
	{

	}

	/**
	 * Do nothing.
	 */
	@Override
	public void doRemoveElement(SimElement element)
	{

	}
}
