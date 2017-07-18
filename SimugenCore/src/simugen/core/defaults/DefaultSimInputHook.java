package simugen.core.defaults;

import simugen.core.interfaces.SimComponent;
import simugen.core.interfaces.SimInputHook;

public class DefaultSimInputHook implements SimInputHook
{
	protected final SimComponent owner;
	
	public DefaultSimInputHook(SimComponent owner)
	{
		this.owner = owner;
	}
	
	@Override
	public SimComponent getSimHookOwner()
	{
		return owner;
	}

}
