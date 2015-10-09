package simugen.core.interfaces;

import java.util.List;

public interface SimModel extends Readyable
{
	public void startUp();
	public void shutdown();
	public void onShutdown();
	public void addComponent(SimComponent comp);
	public SimEvent getNextEvent(SimEngine e);
	public List<SimEventListener> getListeners();
	public void addListener(SimEventListener e);
	public SimModel getCopy();
}
