package simugen.core.defaults;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.random.MersenneTwister;

import simugen.core.abstracts.TimeSimEvent;
import simugen.core.interfaces.LoggingStyle;
import simugen.core.interfaces.SimEngine;
import simugen.core.interfaces.SimEvent;
import simugen.core.interfaces.SimEventListener;
import simugen.core.interfaces.SimModel;

public class DefaultSimEngine implements SimEngine
{
	private SimModel internalModel;

	private PrintStream streamOut;

	private LoggingStyle logging;

	private boolean forceStop;

	private MersenneTwister doubleGenerator;

	private long seed;

	private List<SimEventListener> listListeners = new ArrayList<>();

	private boolean running = false;

	private int runs = 0;

	private long milliseconds = 0L;

	public void setModel(SimModel model)
	{
		internalModel = model;
	}

	public void start()
	{
		printEngine("Engine batching started.");

		for (int i = 0; i < runs; i++)
		{
			printEngine("Run " + i + " Started");
			start(new MersenneTwister().nextLong());
		}

		SimEvent event = new EngineBatchFinishEvent();

		printEngine(event.printEvent(logging));

		listListeners.forEach(new SimEventConsumer(event));
	}

	public void start(long seed)
	{
		this.seed = seed;

		internalModel = internalModel.getCopy();

		if (internalModel == null)
		{
			printEngineErr("Model has not been set!");

			throw new IllegalStateException("Model has not been set");
		}
		else if (!internalModel.isReady())
		{
			printEngineErr("Model has not readied up!");

			throw new IllegalStateException("Model has not readied up");
		}

		this.running = true;

		doubleGenerator = new MersenneTwister(seed);

		forceStop = false;

		internalModel.startUp();

		listListeners.addAll(internalModel.getListeners());

		final List<SimEvent> eventList = new ArrayList<>();

		eventList.addAll(getNextEvents());

		while (!eventList.isEmpty() && !forceStop)
		{
			List<SimEvent> copy = new ArrayList<>(eventList);

			eventList.clear();

			boolean done = false;

			for (SimEvent e : copy)
			{
				if (e instanceof TimeSimEvent)
				{
					final TimeSimEvent evt = (TimeSimEvent) e;

					milliseconds += evt.getTime();
				}

				printSet(e.printEvent(logging));

				listListeners.forEach(new SimEventConsumer(e));

				if (e instanceof ModelFinishedEvent)
				{
					done = true;
				}
			}
			if (!done)
			{
				eventList.addAll(getNextEvents());
			}
		}

		if (forceStop)
		{
			printEngineErr("Engine was forcibly stopped.");
		}

		listListeners.removeAll(internalModel.getListeners());

		running = false;
	}

	private List<SimEvent> getNextEvents()
	{
		return internalModel.getNextEvents(this);
	}

	public void stop()
	{
		forceStop = true;
	}

	public double getNext()
	{
		return doubleGenerator.nextDouble();
	}

	public void printEngine(String message)
	{
		printSet("[SimEngine] " + message);
	}

	public void printEngineErr(String message)
	{
		print("[SimEngine] " + message, LoggingStyle.ERR);
	}

	public void printSet(String message)
	{
		print(message, logging);
	}

	public void print(String message, LoggingStyle style)
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
			streamOut.println("{" + milliseconds + "}" + message);
			break;
		case DEBUG:
			streamOut.println("{" + milliseconds + "}" + "[DEBUG] " + message);
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
	public void setLoggingStyle(LoggingStyle log)
	{
		this.logging = log;
	}

	@Override
	public long getSeed()
	{
		return seed;
	}

	@Override
	public void addEventListener(SimEventListener e)
	{
		listListeners.add(e);
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
}
