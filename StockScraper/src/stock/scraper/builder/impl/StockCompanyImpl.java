package stock.scraper.builder.impl;

import stock.scraper.builder.StockCompany;

public class StockCompanyImpl implements StockCompany
{

	private final String market;
	
	private final String name;
	
	private final String token;
	
	public StockCompanyImpl(String market, String name, String token)
	{
		this.market = market;
		
		this.name = name;
		
		this.token = token;
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
}
