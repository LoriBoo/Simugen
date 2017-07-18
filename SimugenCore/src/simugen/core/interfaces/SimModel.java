package simugen.core.interfaces;

import java.util.List;

public interface SimModel
{
	public void startUp();
	public void shutdown();
	public void onShutdown();
	public void addComponent(SimComponent components);
	public List<SimMessage> getMessages(SimEngine engine, long tick);
//	public List<SimEventListener> getListeners();
//	public void addListener(SimEventListener e);
	public SimModel getCopy();
	public boolean isReady();
}
