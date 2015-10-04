package stock.scraper.builder.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import stock.scraper.builder.StockCompany;
import stock.scraper.builder.StockInterval;
import stock.scraper.builder.YahooUrlBuilder;

public class YahooUrlBuilderImpl implements YahooUrlBuilder
{

	private URL url;

	private Calendar start;

	private Calendar end;

	private StockInterval interval;

	private StockCompany company;

	private static final String prefix = "http://ichart.yahoo.com/table.csv?s=";

	private static final String suffix = "&ignore=.csv";

	@Override
	public URL getUrl() throws MalformedURLException
	{
		// a = start month
		// b = start day
		// c = start year
		// d = end month
		// e = end day
		// f = end year
		// g = interval

		String a = make('a', start.get(Calendar.MONTH));
		String b = make('b', start.get(Calendar.DAY_OF_MONTH));
		String c = make('c', start.get(Calendar.YEAR));
		String d = make('d', end.get(Calendar.MONTH));
		String e = make('e', end.get(Calendar.DAY_OF_MONTH));
		String f = make('f', end.get(Calendar.YEAR));
		String g = make('g', interval.getInterval());

		String sUrl = prefix + company.getToken() + a + b + c + d + e + f + g
				+ suffix;
		
		url = new URL(sUrl);

		return url;
	}

	private String make(char item, Object value)
	{
		return new String("&" + item + "=" + value);
	}

	@Override
	public void setStartDate(Date start)
	{
		this.start = (Calendar) Calendar.getInstance().clone();

		this.start.setTime(start);
	}

	@Override
	public void setEndDate(Date end)
	{
		this.end = (Calendar) Calendar.getInstance().clone();

		this.end.setTime(end);
	}

	@Override
	public void setInterval(StockInterval interval)
	{
		this.interval = interval;
	}

	@Override
	public void setCompany(StockCompany company)
	{
		this.company = company;
	}

	@Override
	public Date getStartDate()
	{
		return start.getTime();
	}

	@Override
	public Date getEndDate()
	{
		return end.getTime();
	}

	@Override
	public StockInterval getInterval()
	{
		return interval;
	}

	@Override
	public StockCompany getCompany()
	{
		return company;
	}

}
