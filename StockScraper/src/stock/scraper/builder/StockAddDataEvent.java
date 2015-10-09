package stock.scraper.builder;

import java.math.BigDecimal;
import java.util.EventObject;
import java.util.Map.Entry;

public class StockAddDataEvent extends EventObject
{
	private static final long serialVersionUID = 1L;

	private final Entry<Long, BigDecimal> source;
	
	private final StockCompany company;

	public StockAddDataEvent(Entry<Long, BigDecimal> source, StockCompany company)
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
	public Entry<Long, BigDecimal> getSource()
	{
		return source;
	}
}
