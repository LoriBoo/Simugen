package stock.scraper.builder;

public enum StockInterval
{
	DAY, WEEK, MONTH;

	public char getInterval()
	{
		switch (this)
		{
		case DAY:
			return 'd';
		case WEEK:
			return 'w';
		case MONTH:
			return 'm';
		default:
			throw new IllegalStateException(
					"Enumerated type not one of the accepted values");
		}
	}
}
