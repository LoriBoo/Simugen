package simugen.core.interfaces;

import java.util.List;

public interface SimController
{
	@Deprecated
	public List<SimEvent> process(SimEngine e, SimEvent... events);
	
	public List<SimEvent> getNextEvents(double next, List<SimComponent> components);
}
