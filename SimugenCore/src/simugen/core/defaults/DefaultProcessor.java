package simugen.core.defaults;

import simugen.core.interfaces.EventProcess;
import simugen.core.interfaces.SimComponent;
import simugen.core.interfaces.SimEvent;

public class DefaultProcessor implements SimComponent
{
	EventProcess process = null;

	public void setProcess(EventProcess run)
	{
		process = run;
	}

	@Override
	public boolean isReady()
	{
		return process != null;
	}

	@Override
	public SimEvent process(double d)
	{
		return process.process(d);
	}

	@Override
	public boolean isComplete()
	{
		return process.isComplete();
	}

	/**
	 * Default Processor has no bearing on the results of the processed event.
	 * Subclasses can override.
	 */
	@Override
	public void response(boolean complete)
	{

	}
}
