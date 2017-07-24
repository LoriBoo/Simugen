package simugen.core.components.abstracts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simugen.core.defaults.NullEngineTick;
import simugen.core.interfaces.Component;
import simugen.core.interfaces.DataContext;
import simugen.core.interfaces.EngineTick;
import simugen.core.interfaces.Event;
import simugen.core.transfer.ElementTransferData;
import simugen.core.transfer.interfaces.PipeData;

public abstract class AbstractComponent implements Component
{
	private final Map<Class<?>, DataContext<?, ? extends PipeData<?>>> mapDataContext = new HashMap<>();

	private String ID = null;

	protected EngineTick current = new NullEngineTick();

	protected final List<Event> events = new ArrayList<>();

	@Override
	public void addProcessDataContext(Class<?> dataClass,
			DataContext<?, ? extends PipeData<?>> dataContext)
	{
		mapDataContext.put(dataClass, dataContext);
	}

	// This is dirty, and I can't find a way to do it cleaner; BUT. This should
	// always work, because there're a lot of checks along the way.
	@SuppressWarnings("unchecked")
	@Override
	public void receiveData(PipeData<?> data)
	{
		final DataContext<?, PipeData<?>> context = (DataContext<?, PipeData<?>>) mapDataContext
				.get(data.getClass());

		if (context != null)
		{
			context.processData(data);
		}
	}

	@Override
	public void setLogID(String ID)
	{
		// Only set once.
		assert ID == null;

		this.ID = ID;
	}

	@Override
	public String getLogID()
	{
		return this.ID == null ? Component.super.getLogID() : ID;
	}

	@Override
	public void getEvents(EngineTick tick)
	{
		if (!current.equals(tick) && canGenerate())
		{
			current = tick;

			generateEvents(tick);
		}

		tick.addAllEvents(events);

		events.clear();
	}

	@Override
	public boolean canReceiveElement(ElementTransferData pipeData)
	{
		return this.getElementCapacity() != 0;
	}

	protected abstract void generateEvents(EngineTick tick);

	protected abstract boolean canGenerate();
}
