package stock.gui.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.ViewPart;

public class StockListView extends ViewPart
{
	public static final String view_id = "StockGui.ListView";
	
	private Tree tree;
	
	@Override
	public void createPartControl(Composite parent)
	{
		tree = new Tree(parent, SWT.DEFAULT);
		
		parent.layout();
		
		System.out.println("asdf");
	}

	@Override
	public void setFocus()
	{
		// TODO Auto-generated method stub
		
	}

}
