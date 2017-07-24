package simugen.core.enums;

public enum TimeUnit
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
}
