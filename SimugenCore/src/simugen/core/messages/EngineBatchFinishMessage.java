package simugen.core.messages;

import simugen.core.abstracts.AbstractSimMessage;
import simugen.core.interfaces.SimEngine;

public class EngineBatchFinishMessage extends AbstractSimMessage
{
	private final SimEngine engine;

	public EngineBatchFinishMessage(SimEngine engine, long time)
	{
		super(time, null, null);

		this.engine = engine;
	}

	@Override
	public String getLogMessage()
	{
		final String message = this.engine.getClass().getSimpleName()
				.concat(": Engine batching finished.");

		return message;
	}

}
