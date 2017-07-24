package simugen.core.abstracts;

import simugen.core.interfaces.Component;
import simugen.core.interfaces.EngineTick;

public abstract class AbstractTickable
{
	protected EngineTick currentTick = null;

	/**
	 * For all {@link Component}s, they need to check if they can generate new
	 * random events based on what {@link EngineTick} the system is on.
	 * 
	 * @param tick
	 * @return
	 */
	protected boolean canGenerate(EngineTick tick)
	{
		if (currentTick == null || tick != currentTick)
		{
			this.currentTick = tick;
			return true;
		}

		return false;
	}
}
