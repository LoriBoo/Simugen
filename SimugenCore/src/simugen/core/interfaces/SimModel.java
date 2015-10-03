package simugen.core.interfaces;

public interface SimModel extends Readyable
{
	public void startUp();
	public void shutdown();
	public void onShutdown();
	public void addComponent(SimComponent comp);
	public SimEvent getNextEvent(SimEngine e);
}
