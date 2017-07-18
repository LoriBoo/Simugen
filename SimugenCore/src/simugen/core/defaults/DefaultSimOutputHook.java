package simugen.core.defaults;

import simugen.core.interfaces.SimInputHook;
import simugen.core.interfaces.SimOutputHook;

public class DefaultSimOutputHook implements SimOutputHook
{
	protected SimInputHook downStreemHook = null;

	@Override
	public void hookTo(SimInputHook downStreamHook)
	{
		this.downStreemHook = downStreamHook;
	}

	@Override
	public SimInputHook getHookedTo()
	{
		assert downStreemHook != null;
		
		return downStreemHook;
	}

}
