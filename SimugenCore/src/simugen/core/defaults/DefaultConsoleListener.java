package simugen.core.defaults;

import java.io.PrintStream;

import simugen.core.data.interfaces.EventListener;
import simugen.core.interfaces.Event;

public class DefaultConsoleListener implements EventListener<Event> {
	private PrintStream console = System.out;

	public DefaultConsoleListener() {

	}

	public DefaultConsoleListener(PrintStream console) {
		this.console = console;
	}

	@Override
	public Class<Event> getEventType() {
		return Event.class;
	}

	@Override
	public void listen(Event event) {
		String logMessage = event.getLogMessage();
		long logTime = event.getTime();

		if (!(event.getClass().isAssignableFrom(PublishSeedEvent.class))) {
			logMessage = "{" + logTime + "} " + logMessage;
		}

		console.println(logMessage);
	}

	public void setConsole(PrintStream stream) {
		console = stream;
	}
}
