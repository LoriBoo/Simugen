package stock.scraper.builder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

public interface StockCompany extends Serializable
{
	public String getMarket();
	public String getCompanyName();
	public String getToken();
	public void addHistorical(Long date, BigDecimal decimal);
	public Map<Long, BigDecimal> getHistorical();
	public void clearHistorical();
	public double getLatest();
}
