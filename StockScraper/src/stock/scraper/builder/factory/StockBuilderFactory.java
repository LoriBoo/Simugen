package stock.scraper.builder.factory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import stock.scraper.builder.StockCompany;
import stock.scraper.builder.StockInterval;
import stock.scraper.builder.YahooUrlBuilder;
import stock.scraper.builder.impl.StockCompanyImpl;
import stock.scraper.builder.impl.YahooUrlBuilderImpl;
import stock.scraper.utils.StockScraperUtils;

public class StockBuilderFactory
{
	public static final StockBuilderFactory INSTANCE = new StockBuilderFactory();

	private static final String prefix = "http://download.finance.yahoo.com/d/quotes.csv?s=";

	private static final String suffix = "&f=nx&e=.csv";

	public StockCompany createCompany(String token)
	{

		String market = null;

		String name = null;

		try
		{
			URL url = new URL(prefix + token + suffix);

			File csv = StockScraperUtils.getDataFile(url);

			FileReader reader = new FileReader(csv);

			char buffer[] = new char[100];

			reader.read(buffer);

			String data = new String(buffer);

			data = fix(data);

			String[] split = data.trim().split(",");

			if (split.length != 2 || split[0].equals("NA")
					|| split[1].equals("NA"))
			{
				reader.close();

				StockScraperUtils.deleteTempFile();

				throw new IOException("Token not found.");
			}

			name = split[0].trim();

			market = split[1].trim();

			reader.close();

			StockScraperUtils.deleteTempFile();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		StockCompany company = new StockCompanyImpl(market, name, token);

		return company;
	}

	private String fix(String data)
	{
		data = data.replaceAll("\",\"", ";;;");
		data = data.replaceAll(",", "");
		data = data.replaceAll("\"", "");
		data = data.replaceAll(";;;", ",");
		return data;
	}

	/**
	 * Create a {@link YahooUrlBuilder} from the specified start date to the
	 * current system date, and default interval of Day
	 * 
	 * @param company
	 * @param startDate
	 * @return
	 */
	public YahooUrlBuilder createBuilder(StockCompany company, Date startDate)
	{
		Date endDate = Calendar.getInstance().getTime();

		return createBuilder(company, startDate, endDate);
	}

	/**
	 * Create a {@link YahooUrlBuilder} from the specified start date to the
	 * specified end date, and default interval of Day
	 * 
	 * @param company
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public YahooUrlBuilder createBuilder(StockCompany company, Date startDate,
			Date endDate)
	{
		return createBuilder(company, startDate, endDate, StockInterval.DAY);
	}

	/**
	 * Create a {@link YahooUrlBuilder} from the specified start date to the
	 * specified end date, with the specified interval.
	 * 
	 * @param company
	 * @param startDate
	 * @param endDate
	 * @param interval
	 * @return
	 */
	private YahooUrlBuilder createBuilder(StockCompany company, Date startDate,
			Date endDate, StockInterval interval)
	{
		YahooUrlBuilder builder = new YahooUrlBuilderImpl();

		builder.setCompany(company);
		builder.setStartDate(startDate);
		builder.setEndDate(startDate);
		builder.setInterval(interval);

		return builder;
	}
}
