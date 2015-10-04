package stock.scraper.builder.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import stock.scraper.builder.StockCompany;
import stock.scraper.builder.YahooUrlBuilder;
import stock.scraper.builder.factory.StockBuilderFactory;
import stock.scraper.utils.StockScraperUtils;

public class StockBuilderFactoryTest
{
	@Test
	public void testCompanyCreation()
	{
		StockCompany company = StockBuilderFactory.INSTANCE
				.createCompany("HII");

		assertTrue(company.getMarket().equals("NYQ"));

		assertTrue(company.getCompanyName()
				.equals("Huntington Ingalls Industries"));
	}

	@Test
	public void testScraping()
	{
		StockCompany company = StockBuilderFactory.INSTANCE
				.createCompany("HII");

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		try
		{
			Date startDate = format.parse("2015-10-01");

			Date endDate = format.parse("2015-10-02");

			YahooUrlBuilder builder = StockBuilderFactory.INSTANCE
					.createBuilder(company, startDate, endDate);

			Map<Date, Double> data = StockScraperUtils.getHistorical(builder);

			assertTrue(data.entrySet().size() == 1);

			for (Entry<Date, Double> e : data.entrySet())
			{
				System.out.println("Date: " + e.getKey().toString());
				System.out.println("Stock Value: " + e.getValue());
			}
		}
		catch (IOException | ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
