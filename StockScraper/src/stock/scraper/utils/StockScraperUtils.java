package stock.scraper.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;

import stock.scraper.builder.YahooUrlBuilder;

public class StockScraperUtils
{
	public final static String HEADER_DATE = "Date";

	public final static String HEADER_CLOSE = "Close";

	private static int date_index = -1;

	private static int close_index = -1;

	public static Map<Date, BigDecimal> getHistorical(YahooUrlBuilder builder)
			throws MalformedURLException, IOException, ParseException
	{
		Map<Date, BigDecimal> historicalData = new LinkedHashMap<>();

		File data = getDataFile(builder.getUrl());

		BufferedReader readFile = new BufferedReader(new FileReader(data));

		Date date = null;

		BigDecimal value = null;

		while (readFile.ready())
		{
			String line = readFile.readLine();
			String split[] = line.split(",");

			for (int i = 0; i < split.length; i++)
			{
				String col = split[i];

				if (col.equals(HEADER_DATE))
				{
					date_index = i;
				}
				else if (col.equals(HEADER_CLOSE))
				{
					close_index = i;
				}
				else if (i == date_index && date == null)
				{
					date = new SimpleDateFormat("yyyy-MM-dd").parse(col);
				}
				else if (i == close_index && value == null)
				{
					value = new BigDecimal(col).setScale(4,
							BigDecimal.ROUND_HALF_UP);
				}
				else if ((i == date_index && date != null)
						|| (i == close_index && value != null))
				{
					// They must not be reset before they are entered into the
					// map.

					readFile.close();

					deleteTempFile();

					throw new IllegalStateException("Problem reading csv file");
				}

				if (value != null && date != null)
				{
					historicalData.put(date, value);

					date = null;

					value = null;
				}
			}
		}

		readFile.close();
		deleteTempFile();
		
		if(!historicalData.isEmpty())
		{
			SortedMap<Date, BigDecimal> sorted = new TreeMap<>(historicalData);
			
			return sorted;
		}

		return historicalData;
	}

	public static void deleteTempFile()
	{
		String location = System.getProperty("user.dir") + "\\temp";

		File dir = new File(location);

		File csv = new File(location + "\\data.csv");

		if (csv.exists())
		{
			assert csv.canWrite();

			assert csv.canRead();

			assert csv.delete();
		}

		if (dir.exists())
		{
			assert dir.delete();
		}
	}

	public static File getDataFile(URL url) throws IOException
	{
		String location = System.getProperty("user.dir") + "\\temp";

		File dir = new File(location);

		assert!dir.exists();

		dir.mkdirs();

		File csv = new File(location + "\\data.csv");

		FileUtils.copyURLToFile(url, csv);

		assert csv.exists();

		assert csv.canRead();

		assert csv.canWrite();

		return csv;
	}

	public static Map<Date, BigDecimal> getPercentChange(
			Map<Date, BigDecimal> data)
	{
		Map<Date, BigDecimal> derviative = new LinkedHashMap<>();

		BigDecimal last = null;

		for (Entry<Date, BigDecimal> entry : data.entrySet())
		{
			if (last != null)
			{
				BigDecimal value = entry.getValue();

				BigDecimal difference = value.subtract(last);

				BigDecimal percentage = difference.divide(last, 4,
						BigDecimal.ROUND_HALF_UP);

				derviative.put(entry.getKey(), percentage);
			}

			last = entry.getValue();
		}

		return derviative;
	}
}
