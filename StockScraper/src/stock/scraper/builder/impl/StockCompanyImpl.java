package stock.scraper.builder.impl;

import simugen.core.rng.EmpiricalGenerator;
import stock.scraper.builder.StockCompany;

public class StockCompanyImpl implements StockCompany
{
	private static final long serialVersionUID = 1L;

	private final String market;
	
	private final String name;
	
	private final String token;
	
	private transient EmpiricalGenerator generator = null;
	
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

	@Override
	public EmpiricalGenerator getDataGenerator()
	{
		return generator;
	}
}
