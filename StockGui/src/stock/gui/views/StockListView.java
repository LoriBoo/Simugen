package stock.gui.views;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import stock.gui.Activator;
import stock.gui.editors.StockCompanyEditorInput;
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
				StockCompany company = getSelectedCompany();

				if (company != null)
				{
					IWorkbenchWindow window = Activator.getDefault()
							.getWorkbench().getActiveWorkbenchWindow();

					IWorkbenchPage page = window.getActivePage();

					IEditorInput input = new StockCompanyEditorInput(company);

					if (page != null)
					{
						try
						{
							page.openEditor(input, "StockGui.editor1");
						}
						catch (PartInitException e1)
						{
							e1.printStackTrace();
						}
					}
				}
			}
		});

		for (StockCompany company : Activator.getDefault().getCompanies())
		{
			addStockCompany(company);
		}
	}

	public Map<String, TreeItem> getMapTreeItems()
	{
		Map<String, TreeItem> map = new HashMap<>();

		for (TreeItem item : tree.getItems())
		{
			map.put(item.getText(), item);
		}

		return map;
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
}
