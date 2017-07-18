package simugen.core.abstracts;

import simugen.core.defaults.DefaultSimOutputHook;
import simugen.core.interfaces.SimComponent;
import simugen.core.interfaces.SimOutputHook;
import simugen.core.interfaces.SimSingleOutputHook;

public abstract class AbstractSimSingleOutputHook
		extends AbstractElementTransferProtocol
		implements SimSingleOutputHook, SimComponent
{
	protected final SimOutputHook outputHook;

	public AbstractSimSingleOutputHook()
	{
		this.outputHook = new DefaultSimOutputHook();
	}

	@Override
	public SimOutputHook getOutputHook()
	{
		return this.outputHook;
	}

}
