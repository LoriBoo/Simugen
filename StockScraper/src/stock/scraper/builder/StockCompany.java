package stock.scraper.builder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public interface StockCompany extends Serializable
{
	public String getMarket();
	public String getCompanyName();
	public String getToken();
	public void addHistorical(Date date, BigDecimal decimal);
	public Map<Date, BigDecimal> getHistorical();
}
