package simugen.core.abstracts;

import java.util.ArrayList;
import java.util.List;

import simugen.core.interfaces.SimComponent;
import simugen.core.interfaces.SimEngine;
import simugen.core.interfaces.SimMessage;
import simugen.core.interfaces.SimModel;
import simugen.core.messages.ModelFinishedEvent;

public abstract class AbstractSimModel implements SimModel
{
	private List<SimComponent> components = new ArrayList<>();

	// private List<SimEventListener> listeners = new ArrayList<>();

	// private final SimController controller = new DefaultSimController();

	boolean complete = false;

	@Override
	public void shutdown()
	{
		complete = true;
		onShutdown();
	}

	@Override
	public void addComponent(SimComponent components)
	{
		this.components.add(components);
	}

	@Override
	public List<SimMessage> getMessages(SimEngine engine, long tick)
	{
		final List<SimMessage> messages = new ArrayList<>();

		// If the model has completed return a ModelFinishedEvent
		if (complete)
		{
			messages.add(
					new ModelFinishedEvent(engine.getMilliseconds(), this));

			return messages;
		}

		for (SimComponent c : components)
		{
			final List<SimMessage> m = c.getMessages(engine, tick);

			if (m != null)
			{
				messages.addAll(m);
			}
		}

		return messages.isEmpty() ? null : messages;
	}

}
