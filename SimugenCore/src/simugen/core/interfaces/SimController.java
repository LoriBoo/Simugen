package simugen.core.interfaces;

import java.util.List;

public interface SimController
{
	public List<SimMessage> getMessages(SimEngine engine, List<SimComponent> components);
}
