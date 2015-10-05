package stock.scraper.builder;

import java.io.Serializable;

import simugen.core.rng.EmpiricalGenerator;

public interface StockCompany extends Serializable
{
	public String getMarket();
	public String getCompanyName();
	public String getToken();
	public EmpiricalGenerator getDataGenerator();
}
