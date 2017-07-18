package simugen.core.messages;

import simugen.core.abstracts.AbstractSimMessage;
import simugen.core.interfaces.SimModel;

public class ModelFinishedEvent extends AbstractSimMessage
{
	private final SimModel model;

	public ModelFinishedEvent(long time, SimModel model)
	{
		super(time, null, null);

		this.model = model;
	}

	@Override
	public String getLogMessage()
	{
		return this.model.getClass().getSimpleName()
				.concat(": Model completed.");
	}

}
