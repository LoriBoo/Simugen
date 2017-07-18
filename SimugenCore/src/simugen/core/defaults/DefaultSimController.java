package simugen.core.defaults;

import java.util.List;

import simugen.core.interfaces.SimComponent;
import simugen.core.interfaces.SimController;
import simugen.core.interfaces.SimEngine;
import simugen.core.interfaces.SimMessage;

/**
 * Under Construction.
 * 
 * @author BASHH
 *
 */
public class DefaultSimController implements SimController
{

	@Override
	public List<SimMessage> getMessages(SimEngine engine,
			List<SimComponent> components)
	{
		for (SimComponent c : components)
		{
			
		}

		return null;
	}
}
