package simugen.core.abstracts;

import simugen.core.defaults.DefaultSimInputHook;
import simugen.core.interfaces.SimComponent;
import simugen.core.interfaces.SimInputHook;
import simugen.core.interfaces.SimSingleInputHook;

public abstract class AbstractSimSingleInputHook extends AbstractElementTransferProtocol
		implements SimSingleInputHook, SimComponent
{
	protected final SimInputHook inputHook;

	public AbstractSimSingleInputHook()
	{
		this.inputHook = new DefaultSimInputHook(this);
		
		this.owner = this;
	}

	@Override
	public SimInputHook getInputHook()
	{
		return this.inputHook;
	}
}
