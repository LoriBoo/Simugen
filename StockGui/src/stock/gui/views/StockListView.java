package stock.gui.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.ViewPart;

import stock.gui.Activator;
import stock.scraper.builder.StockCompany;

public class StockListView extends ViewPart
{
	public static final String view_id = "StockGui.ListView";

	private Tree tree;

	@Override
	public void createPartControl(Composite parent)
	{
		tree = new Tree(parent, SWT.SINGLE);

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

}
