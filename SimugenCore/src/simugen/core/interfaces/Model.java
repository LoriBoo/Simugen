package simugen.core.interfaces;

import java.util.List;

import simugen.core.components.interfaces.Component;

public interface Model
{
	public void startUp();

	public void shutdown();

	public void onShutdown();
	
	public void onStartup();

	public void addComponent(Component components);

	public List<Event> getEvents(EngineTick tick);

	// public List<SimMessage> getMessages(SimEngine engine, long tick);

	// public List<SimEventListener> getListeners();

	// public void addListener(SimEventListener e);

	public boolean isReady();
}
