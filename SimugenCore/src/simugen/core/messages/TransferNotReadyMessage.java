package simugen.core.messages;

import simugen.core.abstracts.AbstractSimMessage;
import simugen.core.interfaces.SimComponent;
import simugen.core.interfaces.SimElement;

public class TransferNotReadyMessage extends AbstractSimMessage
{
	private final SimElement element;

	public TransferNotReadyMessage(long time, SimElement element,
			SimComponent from, SimComponent to)
	{
		super(time, from, to);

		this.element = element;
	}

	public SimElement getSimElement()
	{
		return this.element;
	}

	@Override
	public String getLogMessage()
	{
		return this.getSimComponentFrom().getClass().getSimpleName()
				.concat(" sent ")
				.concat(this.getSimComponentTo().getClass().getSimpleName())
				.concat(" ").concat(this.getClass().getSimpleName());
	}
}
