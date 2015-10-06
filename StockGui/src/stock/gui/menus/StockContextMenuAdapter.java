package stock.gui.menus;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import stock.gui.views.StockListView;
import stock.scraper.builder.StockCompany;

public class StockContextMenuAdapter extends MenuAdapter
{
	private final StockListView view;

	private final Menu menu;

	public StockContextMenuAdapter(StockListView view, Menu menu)
	{
		this.view = view;

		this.menu = menu;
	}

	@Override
	public void menuShown(MenuEvent e)
	{
		MenuItem[] items = menu.getItems();

		for (int i = 0; i < items.length; i++)
		{
			items[i].dispose();
		}

		StockCompany company = view.getSelectedCompany();

		if (company != null)
		{
			new StockHistoricalMenuItem(menu, SWT.NONE, company);
		}
	}
}
