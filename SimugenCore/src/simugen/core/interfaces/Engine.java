package simugen.core.interfaces;

import java.util.Calendar;

import simugen.core.components.interfaces.Component;
import simugen.core.components.interfaces.Server;
import simugen.core.data.interfaces.EventListener;
import simugen.core.defaults.PublishSeedEvent;

/**
 * The {@link Engine} is the mechanism for running {@link Model}s.
 * 
 * @author Lorelei
 *
 */
public interface Engine {
	public void setModelBuilder(ModelBuilder builder);

	/**
	 * Starts the {@link Model}. Uses the internal {@link #getSeed()} method to
	 * generate a random seed for this run of the {@link Model}
	 */
	public void start();

	/**
	 * Starts the model with a determined seed, in lieu of generating a new random
	 * seed.
	 * 
	 * @param seed
	 */
	public void start(long seed);

	/**
	 * Stops the {@link Model}.
	 */
	public void stop();

	/**
	 * Sets the Epoch. Model time starts at 0, and the Epoch determines what 0
	 * means, in real time and date.
	 * 
	 * @param epoch The model Epoch in milliseconds from the Java {@link Calendar}'s
	 *              Epoch, <i>January 1, 197000:00:00.000 GMT (Gregorian).</i>
	 */
	public void setEpoch(long epoch);

	/**
	 * Return the Epoch in milliseconds from the Java {@link Calendar}'s Epoch,
	 * <i>January 1, 197000:00:00.000 GMT (Gregorian).</i>
	 * 
	 * @return
	 */
	public long getEpoch();

	/**
	 * @return The {@link Engine}'s next random double value. {@link EngineTick}s
	 *         wrap this method and allow access to it, for {@link Component}s of
	 *         the {@link Model} so they can determine if and when they can generate
	 *         {@link Event}s.
	 */
	public double getNextRand();

	/**
	 * @return The next random seed for this run of the {@link Model}. Called by
	 *         {@link #start()}.
	 */
	public long getSeed();

	/**
	 * @return <b>True</b> if the {@link Model} is running. <b>False</b> otherwise
	 */
	public boolean isRunning();

	/**
	 * @param runs The amount of runs for this batch of the {@link Engine}.
	 */
	public void setRuns(int runs);

	/**
	 * The current time of the {@link Model} from start = 0. This is the last time
	 * at which {@link Event}s were generated. Used for generating new
	 * {@link Event}s; {@link Component}s that have a duration such as
	 * {@link Server}s will generate {@link Event}s with a time where that time is
	 * the current time from {@link #getMilliseconds()} + the duration
	 * 
	 * @return the current {@link Model} time.
	 */
	public long getMilliseconds();

	/**
	 * Publishes a {@link PublishSeedEvent}. Exposes the seed of the {@link Model}
	 * run to {@link EventListener}s. Intended for displaying in consoles, and for
	 * data collection.
	 */
	public void publishSeed();

	/**
	 * Adds an {@link EventListener} to the {@link Engine}. {@link EventListener}s
	 * handle listening to published {@link Event}s.
	 * 
	 * @param listener The {@link EventListener} to add to the {@link Engine}.
	 */
	public void addListener(EventListener<?> listener);

	/**
	 * @return The set output location for the {@link Model} run data.
	 */
	public String getOutputLocation();

	/**
	 * @param location Sets the output location for the {@link Model} run data.
	 */
	public void setOutputLocation(String location);
}
