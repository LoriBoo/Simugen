package stock.gui.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewReference;

import stock.gui.Activator;
import stock.gui.utilities.StockCompanyDialogBox;
import stock.gui.views.StockListView;
import stock.scraper.builder.StockCompany;

public class AddCompanyHandler implements IHandler
{

	@Override
	public void addHandlerListener(IHandlerListener handlerListener)
	{
	}

	@Override
	public void dispose()
	{
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		StockCompanyDialogBox box = new StockCompanyDialogBox(
				Display.getDefault().getActiveShell());

		StockCompany company = box.open();

		if (company != null)
		{
			for (IViewReference reference : Activator.getDefault()
					.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.getViewReferences())
			{
				final Object view = reference.getView(false);

				if (view != null && view instanceof StockListView)
				{
					StockListView listView = (StockListView) view;

					listView.addStockCompany(company);
				}
			}
		}

		return null;
	}

	@Override
	public boolean isEnabled()
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isHandled()
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener)
	{
		// TODO Auto-generated method stub

	}

}
