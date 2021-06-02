package simugen.core.interfaces;

import simugen.core.components.interfaces.Component;
import simugen.core.data.interfaces.EventPublisher;

/**
 * An {@link Event} represents <b>something</b> that happens in a {@link Model}.
 * In general, anything that has access to get an {@link Event} to the
 * {@link EventPublisher} can generate an {@link Event}. <br>
 * <br>
 * {@link Component}s are most likely candidates for generating {@link Event}s.
 * 
 * @author Lorelei
 *
 */
public interface Event extends LoggableID
{

	/**
	 * @return The time, in milliseconds from model start that this event was
	 *         created.<br>
	 *         Represents model time, not real time.
	 */
	public long getTime();

	/**
	 * @return The default implementation returns the {@link #getLogID()} for
	 *         this {@link Event}.
	 */
	default String getLogMessage()
	{
		return this.getLogID();
	}

	/**
	 * Allows for cleanup of resources.
	 */
	public void consume();

	/**
	 * 
	 * @return <b>True</b> or <b>False</b> whether or not the {@link Event} has
	 *         been consumed; <br>
	 *         see: {@link #consume()}.
	 */
	public boolean isConsumed();

	/**
	 * <b>>>This method a candidate for deletion<<</b>
	 * 
	 * @return The seed for the current {@link Model} run.
	 */
	public long getModelSeed();

	/**
	 * {@link Engine}s should implement passing the model seed to
	 * {@link Event}s.<br>
	 * <br>
	 * <b>>>This method a candidate for deletion<<</b>
	 * 
	 * @param modelSeed
	 *            The seed for the current {@link Model} run.
	 */
	public void setModelSeed(long modelSeed);
}
