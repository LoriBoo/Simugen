package simugen.core.enums;

import java.util.concurrent.TimeUnit;

/**
 * Might be redundant with Java's {@link TimeUnit}, but I couldn't figure out
 * how to do what I wanted, so I built my own. <br>
 * <br>
 * Allows for easy conversion between time units.
 * 
 * @author Lorelei
 *
 */
public enum SimTimeUnit
{
	MILLISECOND, SECOND, MINUTE, HOUR, DAY, WEEK;

	public long getMillis(Number value)
	{
		final double v = value.doubleValue();

		switch (this)
		{
		case DAY:
			return (long) (86400000.0 * v);
		case HOUR:
			return (long) (3600000.0 * v);
		case MILLISECOND:
			return (long) v;
		case MINUTE:
			return (long) (60000.0 * v);
		case SECOND:
			return (long) (1000.0 * v);
		case WEEK:
			return (long) (604800000.0 * v);
		default:
			throw new IllegalStateException();
		}
	}

	public double getConvertMillis(Number value)
	{
		final double v = value.doubleValue();

		switch (this)
		{
		case DAY:
			return (v / 86400000.0);
		case HOUR:
			return (v / 3600000.0);
		case MILLISECOND:
			return v;
		case MINUTE:
			return (v / 60000.0);
		case SECOND:
			return (v / 1000.0);
		case WEEK:
			return (v / 604800000.0);
		default:
			throw new IllegalStateException();
		}
	}

	public double getDays(Number value)
	{
		final double v = value.doubleValue();

		switch (this)
		{
		case DAY:
			return v;
		case HOUR:
			return (v / 24.0);
		case MILLISECOND:
			return (v / 86400000.0);
		case MINUTE:
			return (v / 24.0 / 60.0);
		case SECOND:
			return (v / 86400.0);
		case WEEK:
			return (v * 7.0);
		default:
			throw new IllegalStateException();
		}
	}

	public double getHours(Number value)
	{
		final double v = value.doubleValue();

		switch (this)
		{
		case DAY:
			return v;
		case HOUR:
			return v;
		case MILLISECOND:
			return (v / 86400000.0);
		case MINUTE:
			return (v / 24.0 / 60.0);
		case SECOND:
			return (v / 86400.0);
		case WEEK:
			return (v * 7.0);
		default:
			throw new IllegalStateException();
		}
	}
}
