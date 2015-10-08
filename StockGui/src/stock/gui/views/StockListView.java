package stock.gui.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.ViewPart;

import stock.gui.Activator;
import stock.gui.menus.StockContextMenuAdapter;
import stock.scraper.builder.StockCompany;

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

		menu.addMenuListener(new StockContextMenuAdapter(this, menu));

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

		tree.addMouseListener(new MouseListener()
		{

			@Override
			public void mouseUp(MouseEvent e)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseDown(MouseEvent e)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseDoubleClick(MouseEvent e)
			{
				if (getSelectedCompany() != null)
				{
					StockModelView view = new StockModelView();

					view.setCompany(getSelectedCompany());

					Activator.getDefault().getWorkbench()
							.getActiveWorkbenchWindow().getActivePage()
							.activate(view);
				}
			}
		});
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
}
