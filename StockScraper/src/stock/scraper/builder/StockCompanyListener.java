package stock.scraper.builder;

import java.util.EventListener;

public interface StockCompanyListener extends EventListener
{
	public void processStockEvent(StockAddDataEvent evt);
}
