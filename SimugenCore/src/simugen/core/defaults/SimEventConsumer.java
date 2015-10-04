package simugen.core.defaults;

import java.util.function.Consumer;

import simugen.core.interfaces.SimEvent;
import simugen.core.interfaces.SimEventListener;

public class SimEventConsumer implements Consumer<SimEventListener>
{
	private final SimEvent event;
	
	public SimEventConsumer(SimEvent event)
	{
		this.event = event;
	}
	
	@Override
	public void accept(SimEventListener t)
	{
		t.processEvent(event);
	}

}
