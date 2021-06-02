package simugen.core.defaults;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.math3.random.MersenneTwister;

import simugen.core.data.defaults.DefaultEventPublisher;
import simugen.core.data.interfaces.EventListener;
import simugen.core.data.interfaces.EventPublisher;
import simugen.core.interfaces.Engine;
import simugen.core.interfaces.EngineTick;
import simugen.core.interfaces.Event;
import simugen.core.interfaces.Model;
import simugen.core.interfaces.ModelBuilder;

/**
 * Default implementation of {@link Engine}. Utilizes the
 * {@link MersenneTwister} random number generator to generate seeds, and to
 * generate new random numbers {0,1} for generating variates throughout the
 * {@link Model}.<br>
 * <br>
 * End users should be able to utilize {@link DefaultEngine} without needing to
 * build their own {@link Engine}; however the capability is there if needed.
 * <b>Note:</b> There is no abstract implementation of {@link Engine} and
 * therefore end users will need to take care that their implementation of
 * {@link Engine} is sound.
 * 
 * @author Lorelei
 * 
 */
final public class DefaultEngine implements Engine
{
	private boolean forceStop;

	private MersenneTwister doubleGenerator;

	private long seed;

	private List<EventListener<?>> listListeners = new ArrayList<>();

	private EventPublisher publisher;

	private long epoch = 0L;

	private boolean running = false;

	private int runs = 0;

	private int currentRun = 0;

	private long milliseconds = 0L;

	private ModelBuilder modelBuilder;

	private Model runningModel;

	private String outputLocation = "C:/Output/db/";

	private int batch = 0;

	/**
	 * Start the {@link Engine} with a new random seed.
	 * 
	 * @see #start(long) start(long).
	 */
	@Override
	public void start()
	{
		start(new MersenneTwister().nextLong());
	}

	private void setBatchNumber()
	{
		String location = this.getOutputLocation() + "Batch X/";

		String loc = null;

		File p = new File(location);

		int i = 1;

		do
		{
			loc = location.replace("X", String.valueOf(i));

			p = new File(loc);

			i++;

			if (i == Integer.MAX_VALUE)
			{
				throw (new IllegalStateException(
						"Too many Batch folders exist"));
			}
		} while (p.isDirectory());

		p.mkdir();

		this.batch = i - 1;
	}

	@Override
	public void setEpoch(long epoch)
	{
		this.epoch = epoch;
	}

	@Override
	public long getEpoch()
	{
		return this.epoch;
	}

	/**
	 * Starts the {@link Engine} with a defined seed.<br>
	 * <br>
	 * Operations that happen:
	 * <li>{@link #publisher} gets initialized</li>
	 * <li>Publish new {@link ModelRunStartedEvent}</li>
	 * <li>Add all {@link EventListener}s from the {@link Engine} to the
	 * {@link #publisher}, including a {@link DefaultConsoleListener}</li>
	 * <li>Publish an {@link EngineStartedEvent}</li>
	 * <li>Build the model with {@link #modelBuilder}</li>
	 * <li>Set the {@link Model} output location</li>
	 * <li>Start the {@link Model}</li>
	 * <li>Add all {@link EventListener}s from the {@link Model} to the
	 * {@link #publisher}</li>
	 * <li>Loop through all {@link EngineTick}s until the {@link Model} is
	 * complete</li>
	 * <li>Publish {@link Event}s as they occur</li>
	 * <li>Publish {@link EngineFinishedEvent} when batching is done.</li>
	 */
	@Override
	public void start(long seed)
	{
		for (currentRun = 0; currentRun < runs; currentRun++)
		{
			setBatchNumber();

			publisher = new DefaultEventPublisher();

			publisher.publish(
					new ModelRunStartedEvent(milliseconds, currentRun));

			publisher.addAllListeners(listListeners);

			publisher.addEventListener(new DefaultConsoleListener());

			if (epoch == 0L)
			{
				setEpoch(Calendar.getInstance().getTimeInMillis());
			}

			publisher.publish(new EngineStartedEvent(milliseconds));

			this.seed = seed;

			try
			{
				this.runningModel = this.modelBuilder.buildModel();
			}
			catch (Exception e)
			{
				publisher.publish(new EngineErrorEvent(milliseconds,
						"Problem Building model!"));

				throw new IllegalStateException("Problem building model.");
			}

			if (runningModel == null)
			{
				publisher.publish(new EngineErrorEvent(milliseconds,
						"Model has not been set!"));

				throw new IllegalStateException("Model has not been set");
			}
			else if (!runningModel.isReady())
			{
				publisher.publish(new EngineErrorEvent(milliseconds,
						"Model has not readied up!"));

				throw new IllegalStateException("Model has not readied up");
			}

			this.running = true;

			doubleGenerator = new MersenneTwister(seed);

			forceStop = false;

			runningModel.setOutputLocation(
					getOutputLocation() + "Batch " + batch + "/");

			runningModel.startUp(this.currentRun, this.seed, this.epoch);

			publisher.addAllListeners(runningModel.getListeners());

			// Keep getting events until there are none left, or the engine has
			// been
			// halted by the user.
			boolean dirty = false;

			boolean complete = false;

			final List<Event> eventList = new ArrayList<>();

			while (complete == false)
			{
				EngineTick tick = new DefaultEngineTick(this);

				final List<Event> getEvents = runningModel.getEvents(tick);

				if (getEvents != null)
				{
					eventList.addAll(getEvents);
				}

				if (eventList.isEmpty())
				{
					eventList.add(new ModelFinishedEvent(milliseconds));
				}

				long time = -1L;

				do
				{
					final List<Event> copy = new ArrayList<>(eventList);

					// Sort the list in order of model time
					copy.sort(new EventSorter());

					for (Event e : copy)
					{
						if (time == -1L)
						{
							time = e.getTime();
						}

						if (e.getTime() == time)
						{

							e.setModelSeed(seed);

							publisher.publish(e);

							e.consume();

							if (e instanceof ModelFinishedEvent)
							{
								complete = true;
							}

							eventList.remove(e);
						}
					}

					milliseconds = time;

					tick.setCurrentTime(milliseconds);

					final List<Event> newEvents = runningModel.getEvents(tick);

					// If we got new messages based on messages sent, the engine
					// is
					// dirty and needs to process the messages
					dirty = newEvents != null;

					if (newEvents != null)
					{
						eventList.addAll(newEvents);
					}

				} while (dirty && !forceStop && !complete);
			}

			if (forceStop)
			{
				publisher.publish(new EngineErrorEvent(milliseconds,
						"Engine was forcibly stopped."));
			}

			running = false;
		}

		publisher.publish(new EngineFinishedEvent(milliseconds));
	}

	@Override
	public void stop()
	{
		forceStop = true;
	}

	@Override
	public double getNextRand()
	{
		return doubleGenerator.nextDouble();
	}

	@Override
	public void publishSeed()
	{
		publisher.publish(new PublishSeedEvent(this.getSeed()));
	}

	@Override
	public long getSeed()
	{
		return seed;
	}

	@Override
	public boolean isRunning()
	{
		return running;
	}

	@Override
	public void setRuns(int runs)
	{
		this.runs = runs;
	}

	@Override
	public long getMilliseconds()
	{
		return milliseconds;
	}

	@Override
	public void setModelBuilder(ModelBuilder builder)
	{
		this.modelBuilder = builder;
	}

	@Override
	public void addListener(EventListener<?> listener)
	{
		listListeners.add(listener);
	}

	@Override
	public String getOutputLocation()
	{
		return this.outputLocation;
	}

	@Override
	public void setOutputLocation(String location)
	{
		this.outputLocation = location;
	}
}
