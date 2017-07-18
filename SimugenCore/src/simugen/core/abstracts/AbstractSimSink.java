package simugen.core.abstracts;

import java.util.ArrayList;
import java.util.List;

import simugen.core.interfaces.SimElement;
import simugen.core.interfaces.SimEngine;
import simugen.core.interfaces.SimMessage;
import simugen.core.interfaces.SimSink;
import simugen.core.messages.ElementSunkMessage;

public abstract class AbstractSimSink extends AbstractSimSingleInputHook
		implements SimSink
{

	private final List<SimElement> buffer = new ArrayList<>();

	/**
	 * If there are any elements in the buffer to sink, create sunk messages.
	 * 
	 * Sinks don't care about what tick of the engine we're on, if there're
	 * elements to sink, they will sink
	 */
	@Override
	public List<SimMessage> doGetMessages(SimEngine engine, long tick)
	{
		final List<SimMessage> messages = new ArrayList<>();

		for (SimElement e : buffer)
		{
			messages.add(
					new ElementSunkMessage(engine.getMilliseconds(), this, e));
		}

		buffer.clear();

		return messages.isEmpty() ? null : messages;
	}

	/**
	 * Sinks don't care about what tick we're on. Always allow "doGetMessages"
	 * to run.
	 */
	@Override
	protected boolean canDoGetMessages(long tick)
	{
		return true;
	}

	/**
	 * Populate the buffer
	 */
	@Override
	public void doRecieveElement(SimElement element)
	{
		buffer.add(element);
	}

	/**
	 * Do nothing.
	 */
	@Override
	public void doRemoveElement(SimElement element)
	{

	}

	/**
	 * Sinks can always receive elements
	 */
	@Override
	public boolean canRecieveElement()
	{
		return true;
	}
}
