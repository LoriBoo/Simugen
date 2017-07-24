package simugen.core.defaults;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.random.MersenneTwister;

import simugen.core.interfaces.Engine;
import simugen.core.interfaces.EngineTick;
import simugen.core.interfaces.Event;
import simugen.core.interfaces.LoggingStyle;
import simugen.core.interfaces.Model;
import simugen.core.interfaces.ModelBuilder;

public class DefaultEngine implements Engine
{
	// private SimModel runningModel;

	private PrintStream streamOut;

	private boolean forceStop;

	private MersenneTwister doubleGenerator;

	private long seed;

	// private List<SimEventListener> listListeners = new ArrayList<>();

	private boolean running = false;

	private int runs = 0;

	private long milliseconds = 0L;

	private ModelBuilder modelBuilder;

	private Model runningModel;

	public void start()
	{
		printEngine(milliseconds, "Engine batching started.");

		for (int i = 0; i < runs; i++)
		{
			printEngine(milliseconds, "Run " + i + " Started");

			start(new MersenneTwister().nextLong());
		}

		printEngine(milliseconds, "Engine batching finished.");
	}

	public void start(long seed)
	{
		this.seed = seed;

		try
		{
			this.runningModel = this.modelBuilder.buildModel();
		}
		catch (Exception e)
		{
			printEngineErr(milliseconds, "Problem building model.");

			throw new IllegalStateException("Problem building model.");
		}

		if (runningModel == null)
		{
			printEngineErr(milliseconds, "Model has not been set!");

			throw new IllegalStateException("Model has not been set");
		}
		else if (!runningModel.isReady())
		{
			printEngineErr(milliseconds, "Model has not readied up!");

			throw new IllegalStateException("Model has not readied up");
		}

		this.running = true;

		doubleGenerator = new MersenneTwister(seed);

		forceStop = false;

		runningModel.startUp();

		// listListeners.addAll(runningModel.getListeners());

		// List<SimMessage> messageList = new ArrayList<>();

		// Keep getting events until there are none left, or the engine has been
		// halted by the user.
		boolean dirty = false;

		boolean complete = false;

		final List<Event> eventList = new ArrayList<>();

		while (complete == false)
		{
			EngineTick tick = new DefaultEngineTick(this);

			final List<Event> getEvents = getEvents(tick);

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
						printEngine(e.getTime(), e.getLogMessage());

						if (e instanceof ModelFinishedEvent)
						{
							complete = true;
						}

						if (e instanceof ServerCompletedEvent)
						{
							System.out.print("");
						}

						eventList.remove(e);
					}
				}

				milliseconds = time;

				tick.setCurrentTime(milliseconds);

				final List<Event> newEvents = getEvents(tick);

				// If we got new messages based on messages sent, the engine is
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
			printEngineErr(milliseconds, "Engine was forcibly stopped.");
		}
		// else if (messageList.isEmpty())
		// {
		//
		// }

		// listListeners.removeAll(runningModel.getListeners());

		running = false;
	}

	private List<Event> getEvents(EngineTick tick)
	{
		return runningModel.getEvents(tick);
	}

	public void stop()
	{
		forceStop = true;
	}

	public double getNext()
	{
		return doubleGenerator.nextDouble();
	}

	public void printEngine(long time, String message)
	{
		printSet(time, "[SimEngine] " + message);
	}

	public void printEngineErr(long time, String message)
	{
		print(time, "[SimEngine] " + message, LoggingStyle.ERR);
	}

	public void printSet(long time, String message)
	{
		print(time, message, LoggingStyle.DATA);
	}

	public void print(long time, String message, LoggingStyle style)
	{
		if (streamOut == null)
		{
			streamOut = System.out;
		}

		if (message == null)
		{
			return;
		}
		switch (style)
		{
		case DATA:
			streamOut.println("{" + time + "}" + message);
			break;
		case DEBUG:
			streamOut.println("{" + time + "}" + "[DEBUG] " + message);
			break;
		case ERR:
		{
			if (streamOut.equals(System.out))
			{
				System.err.println("[ERROR] " + message);
			}
			else
			{
				streamOut.println("[ERROR]" + message);
			}
		}
		case SUPRESS:
			break;
		default:
			break;
		}
	}

	@Override
	public void setStreamOut(PrintStream out)
	{
		this.streamOut = out;
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
}
