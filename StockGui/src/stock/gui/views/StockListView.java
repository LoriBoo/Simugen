package stock.gui.views;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.ViewPart;

import stock.gui.Activator;
import stock.scraper.builder.StockCompany;
import stock.scraper.builder.YahooUrlBuilder;
import stock.scraper.builder.factory.StockBuilderFactory;
import stock.scraper.utils.StockScraperUtils;

public class StockListView extends ViewPart
{
	public static final String view_id = "StockGui.ListView";

	private Tree tree;

	@Override
	public void createPartControl(Composite parent)
	{
		tree = new Tree(parent, SWT.SINGLE);

		final Menu menu = new Menu(tree);

		tree.setMenu(menu);

		menu.addMenuListener(new MenuAdapter()
		{
			public void menuShown(MenuEvent e)
			{
				MenuItem[] items = menu.getItems();

				for (int i = 0; i < items.length; i++)
				{
					items[i].dispose();
				}

				MenuItem newItem = new MenuItem(menu, SWT.NONE);

				StockCompany company = getSelectedCompany();
				
				if(company == null)
				{
					return;
				}

				if (!company.getHistorical().isEmpty())
				{
					MenuItem log = new MenuItem(menu, SWT.NONE);

					log.setText("Log historical data");

					log.addSelectionListener(new SelectionListener()
					{

						@Override
						public void widgetSelected(SelectionEvent e)
						{
							for (Entry<Date, BigDecimal> ent : company
									.getHistorical().entrySet())
							{
								System.out.println("Date: " + ent.getKey()
										+ "\tStock: $" + ent.getValue());
							}
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e)
						{
						}
					});
				}

				newItem.setText(
						"Scrape historical values for " + company.getToken());

				newItem.addSelectionListener(new SelectionListener()
				{

					@Override
					public void widgetSelected(SelectionEvent e)
					{
						SimpleDateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd");

						Date start = null;

						Date end = null;

						InputDialog dialog = new InputDialog(tree.getShell(),
								"Start Date",
								"Enter start date in 'yyyy-MM-dd' format", "",
								validator);

						int rtn = dialog.open();

						if (rtn == InputDialog.OK)
						{
							try
							{
								start = format.parse(dialog.getValue());
							}
							catch (ParseException e1)
							{
								e1.printStackTrace();
							}
						}
						else
						{
							return;
						}

						dialog = new InputDialog(tree.getShell(), "End Date",
								"Enter end date in 'yyyy-MM-dd' format", "",
								validator);

						rtn = dialog.open();

						if (rtn == InputDialog.OK)
						{
							try
							{
								end = format.parse(dialog.getValue());
							}
							catch (ParseException e1)
							{
								e1.printStackTrace();
							}
						}
						else
						{
							return;
						}

						if (!start.before(end))
						{
							throw new IllegalStateException(
									"Start date must be before end date.");
						}

						YahooUrlBuilder builder = StockBuilderFactory.INSTANCE
								.createBuilder(company, start, end);

						try
						{
							Map<Date, BigDecimal> hist = StockScraperUtils
									.getHistorical(builder);

							for (Entry<Date, BigDecimal> entry : hist
									.entrySet())
							{
								company.addHistorical(entry.getKey(),
										entry.getValue());
							}
						}
						catch (IOException | ParseException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e)
					{
					}
				});
			}
		});

		parent.layout();

		for (StockCompany company : Activator.getDefault().getCompanies())
		{
			addStockCompany(company);
		}
	}

	public void addStockCompany(StockCompany company)
	{
		TreeItem item = new TreeItem(tree, SWT.SINGLE);

		item.setText(company.getCompanyName());

		item.setData(company);
	}

	public StockCompany getSelectedCompany()
	{
		return tree.getSelectionCount() == 1
				? (StockCompany) tree.getSelection()[0].getData() : null;
	}

	@Override
	public void setFocus()
	{

	}

	private static IInputValidator validator = new IInputValidator()
	{
		@Override
		public String isValid(String newText)
		{
			try
			{
				new SimpleDateFormat("yyyy-MM-dd").parse(newText);
			}
			catch (ParseException e)
			{
				return "invalid";
			}
			return null;
		}
	};
}
