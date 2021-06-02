package simugen.core.interfaces;

import java.util.Calendar;
import java.util.List;

import simugen.core.abstracts.AbstractModel;
import simugen.core.components.interfaces.Component;
import simugen.core.components.interfaces.Queue;
import simugen.core.components.interfaces.Server;
import simugen.core.components.interfaces.Sink;
import simugen.core.components.interfaces.Source;
import simugen.core.data.interfaces.EventListener;

/**
 * The {@link Model} represents a model of a process. Contains
 * {@link Component}s and {@link EventListener}s. {@link Component}s are linked
 * together to form the model.<br>
 * <br>
 * A basic example: <br>
 * <br>
 * A {@link Source} connected to a {@link Queue}, connected to a {@link Server},
 * connected to a {@link Sink} is a very simple model, and often is used as "The
 * Coffee Shop Model" where the {@link Source} generates Customer
 * {@link Element}s, the {@link Queue} holds Customers until the {@link Server}
 * (Barista) is ready for them, and the Barista holds the Customer for a certain
 * amount of time, then the Customer leaves the {@link Model} via the
 * {@link Sink}. <br>
 * <br>
 * <b>Subclasses should extend {@link AbstractModel} in lieu of implementing
 * {@link Model} directly.
 * 
 * @author Lorelei
 *
 */
public interface Model
{
	/**
	 * Starts the model. Passes some data from the {@link Engine} for record
	 * keeping. <br>
	 * <br>
	 * <b>This may be changed to just pass the {@link Engine} itself, in lieu of
	 * these specific parameters</b>
	 * 
	 * @param run
	 *            The current run, zero-indexed.
	 * @param seed
	 *            The seed for the current run. Passed to the {@link Model} for
	 *            record keeping, and should not be utilized for any random data
	 *            generation, which should all be done via
	 *            {@link Engine#getNextRand()}
	 * @param epoch
	 *            The Epoch of the {@link Engine}, in milliseconds from the Java
	 *            {@link Calendar} Epoch of January 1st 1970, 00:00:00.000
	 *            (Gregorian).
	 */
	public void startUp(int run, long seed, long epoch);

	/**
	 * Method for cleaning up of resources.
	 */
	public void shutdown();

	/**
	 * Called by {@link #shutdown()}, for subclasses to have their own resource
	 * cleanup.
	 */
	public void onShutdown();

	/**
	 * Called by {@link #startUp(int, long, long)}, for subclasses to
	 * instantiate resources, and build the model.<br>
	 * <br>
	 * This method is where the model should actually be built, E.g. connecting
	 * {@link Component}s, and adding {@link Component}s via
	 * {@link #addComponent(Component)} and adding {@link EventListener}s via
	 * {@link #addListener(EventListener)}.
	 */
	public void onStartup();

	/**
	 * @param component
	 *            The {@link Component} to be added to the model. The
	 *            {@link Engine} will only cycle through {@link Component}s
	 *            added in this manner.
	 */
	public void addComponent(Component component);

	/**
	 * 
	 * @param tick
	 *            The current {@link EngineTick} of the {@link Engine}.
	 * @return {@link List} of {@link Event}s that the model has generated;
	 *         {@link Event}s generally are generated by the {@link Component}s
	 *         of the {@link Model}.
	 */
	public List<Event> getEvents(EngineTick tick);

	/**
	 * @return <b>True</b> if {@link #startUp(int, long, long)} has been called.
	 *         <b>False</b> otherwise.
	 */
	public boolean isReady();

	/**
	 * @return {@link List} of {@link EventListener}s that have been added via
	 *         {@link #addListener(EventListener)}. Called by the
	 *         {@link Engine}.
	 */
	public List<EventListener<?>> getListeners();

	/**
	 * @param listener
	 *            The {@link EventListener} to be added to the {@link Model}.
	 */
	public void addListener(EventListener<?> listener);

	/**
	 * @param location
	 *            The output location used by {@link EventListener}s to output
	 *            data, e.g. like outputting to a common Database.
	 */
	public void setOutputLocation(String location);

	/**
	 * @return The output location used by {@link EventListener}s to output
	 *         data, e.g. like outputting to a common Database.
	 */
	public String getOutputLocation();
}
