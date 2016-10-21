package simugen.core.interfaces;

import java.util.List;

public interface SimTimeController
{
	public List<SimEvent> process(SimEvent... events);
}
