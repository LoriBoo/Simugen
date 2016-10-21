package simugen.core.abstracts;

import java.util.concurrent.TimeUnit;

import simugen.core.interfaces.LoggingStyle;
import simugen.core.interfaces.SimEvent;

public class TimeSimEvent extends AbstractProcessResponseEvent
{
	private final TimeUnit timeUnit;

	private final double time;

	private final SimEvent subEvent;

	public TimeSimEvent(TimeUnit timeUnit, double time, SimEvent subEvent)
	{
		this.time = time;

		this.timeUnit = timeUnit;

		this.subEvent = subEvent;
	}

	@Override
	public String printEvent(LoggingStyle style)
	{
		return subEvent.printEvent(style);
	}

	public long getTime()
	{
		long millis = -1L;

		switch (timeUnit)
		{
		case DAYS:
			millis = (long) (86400000 * time);
			break;
		case HOURS:
			millis = (long) (3600000 * time);
			break;
		case MICROSECONDS:
			throw new UnsupportedOperationException(
					"Microsecond TimeUnit not supported");
		case MILLISECONDS:
			millis = (long) time;
			break;
		case MINUTES:
			millis = (long) (60000 * time);
			break;
		case NANOSECONDS:
			throw new UnsupportedOperationException(
					"Nanoseconds TimeUnit not supported");
		case SECONDS:
			millis = (long) (1000 * time);
			break;
		default:
			throw new IllegalStateException("Error determining TimeUnit");
		}

		return millis;
	}
}
