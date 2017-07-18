package simugen.core.defaults;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.random.MersenneTwister;

import simugen.core.interfaces.LoggingStyle;
import simugen.core.interfaces.SimComponent;
import simugen.core.interfaces.SimEngine;
import simugen.core.interfaces.SimMessage;
import simugen.core.interfaces.SimModel;
import simugen.core.messages.EngineBatchFinishMessage;
import simugen.core.messages.ModelFinishedEvent;
import simugen.core.stats.SimMessageComparitor;

public class DefaultSimEngine implements SimEngine
{
	private SimModel internalModel;

	private PrintStream streamOut;

	private LoggingStyle logging;

	private boolean forceStop;

	private MersenneTwister doubleGenerator;

	private long seed;

	// private List<SimEventListener> listListeners = new ArrayList<>();

	private boolean running = false;

	private int runs = 0;

	private long milliseconds = 0L;

	private long tick = 0L;

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

		SimMessage finishedMessage = new EngineBatchFinishMessage(this,
				milliseconds);

		printEngine(finishedMessage.getLogMessage());

		// SimEvent event = new EngineBatchFinishEvent(milliseconds);
		//
		// printEngine(event.printEvent(logging));
		//
		// listListeners.forEach(new SimEventConsumer(event));
	}

	public void start(long seed)
	{
		this.seed = seed;

		// internalModel = internalModel.getCopy();

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

		// listListeners.addAll(internalModel.getListeners());

		// List<SimMessage> messageList = new ArrayList<>();

		// Keep getting events until there are none left, or the engine has been
		// halted by the user.

		boolean dirty = false;

		boolean complete = false;

		while (complete == false)
		{

			List<SimMessage> messageList = getMessages();

			if (messageList == null)
			{
				messageList = new ArrayList<>();

				messageList.add(
						new ModelFinishedEvent(milliseconds, internalModel));
			}

			do
			{
				// Sort the list in order of model time
				messageList.sort(new SimMessageComparitor());

				for (SimMessage e : messageList)
				{
					// For each message, if it has a sender, send it to the
					// component.

					final SimComponent to = e.getSimComponentTo();

					if (to != null)
					{
						to.receiveMessage(e);
					}

					// Update the time in the engine
					milliseconds = e.getModelTime();

					printEngine(e.getLogMessage());

					if (e instanceof ModelFinishedEvent)
					{
						complete = true;
					}
				}

				messageList = getMessages();

				// If we got new messages based on messages sent, the engine is
				// dirty and needs to process the messages
				dirty = messageList != null;

			} while (dirty && !forceStop);

			// Tick the engine, so that components know to generate new messages
			tick++;
		}

		if (forceStop)
		{
			printEngineErr("Engine was forcibly stopped.");
		}
		// else if (messageList.isEmpty())
		// {
		//
		// }

		// listListeners.removeAll(internalModel.getListeners());

		running = false;
	}

	private List<SimMessage> getMessages()
	{
		return internalModel.getMessages(this, this.tick);
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

	// @Override
	// public void addEventListener(SimEventListener e)
	// {
	// listListeners.add(e);
	// }

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
}
