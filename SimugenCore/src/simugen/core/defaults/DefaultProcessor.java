package simugen.core.defaults;

import simugen.core.interfaces.EventProcess;
import simugen.core.interfaces.SimComponent;
import simugen.core.interfaces.SimEvent;

/**
 * Might come back later, but not sure it's in line with where I want to go now.
 * 
 * @author BASHH
 *
 */
@Deprecated
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
	public void setIsComplete(boolean complete)
	{

	}

	@Override
	public boolean canProduceEvent()
	{
		return false;
	}
}
