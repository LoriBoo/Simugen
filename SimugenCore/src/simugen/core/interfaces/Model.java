package simugen.core.interfaces;

import java.util.List;

import simugen.core.components.interfaces.Component;
import simugen.core.data.interfaces.EventListener;

public interface Model
{
	public void startUp(int run, long seed);

	public void shutdown();

	public void onShutdown();
	
	public void onStartup();

	public void addComponent(Component components);

	public List<Event> getEvents(EngineTick tick);

	public boolean isReady();

	public List<EventListener> getListeners();

	public void addListener(EventListener listener);
	
	public void setOutputLocation(String location);
	
	public String getOutputLocation();
}
