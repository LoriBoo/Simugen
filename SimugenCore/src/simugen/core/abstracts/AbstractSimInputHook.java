package simugen.core.abstracts;

import simugen.core.interfaces.SimComponent;
import simugen.core.interfaces.SimInputHook;

public abstract class AbstractSimInputHook implements SimInputHook
{
	@Override
	public SimComponent getSimHookOwner()
	{
		assert this instanceof SimComponent;
		
		return SimComponent.class.cast(this);
	}

}
