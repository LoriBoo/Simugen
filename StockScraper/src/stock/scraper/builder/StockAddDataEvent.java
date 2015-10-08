package stock.scraper.builder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.EventObject;
import java.util.Map.Entry;

public class StockAddDataEvent extends EventObject
{
	private static final long serialVersionUID = 1L;

	private final Entry<Date, BigDecimal> source;
	
	private final StockCompany company;

	public StockAddDataEvent(Entry<Date, BigDecimal> source, StockCompany company)
	{
		super(source);

		this.source = source;
		
		this.company = company;
	}
	
	public boolean isFor(StockCompany company)
	{
		return this.company.equals(company);
	}
	
	@Override
	public Entry<Date, BigDecimal> getSource()
	{
		return source;
	}
}
