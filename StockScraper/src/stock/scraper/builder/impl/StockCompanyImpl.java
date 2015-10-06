package stock.scraper.builder.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import stock.scraper.builder.StockCompany;

public class StockCompanyImpl implements StockCompany
{
	private static final long serialVersionUID = 1L;

	private final String market;
	
	private final String name;
	
	private final String token;
	
	private final Map<Date, BigDecimal> historical;
	
	public StockCompanyImpl(String market, String name, String token)
	{
		this.market = market;
		
		this.name = name;
		
		this.token = token;
		
		this.historical = new LinkedHashMap<>();
	}
	
	@Override
	public String getMarket()
	{
		return market;
	}

	@Override
	public String getCompanyName()
	{
		return name;
	}

	@Override
	public String getToken()
	{
		return token;
	}
	
	@Override
	public void addHistorical(Date date, BigDecimal decimal)
	{
		historical.put(date, decimal);
	}
	
	@Override
	public Map<Date, BigDecimal> getHistorical()
	{
		return historical;
	}

	@Override
	public void clearHistorical()
	{
		historical.clear();
	}
}
