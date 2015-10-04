package stock.scraper.builder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public interface YahooUrlBuilder
{
	public URL getUrl() throws MalformedURLException;
	public void setStartDate(Date start);
	public void setEndDate(Date end);
	public void setInterval(StockInterval interval);
	public void setCompany(StockCompany company);
	public Date getStartDate();
	public Date getEndDate();
	public StockInterval getInterval();
	public StockCompany getCompany();
}
