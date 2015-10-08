package stock.gui.editors;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IMemento;

import stock.gui.Activator;
import stock.scraper.builder.StockCompany;

public class StockCompanyPersistenceFactory implements IElementFactory
{
	public static final String ID = "stock.gui.editors.StockCompanyPersistenceFactory";
	
	@Override
	public IAdaptable createElement(IMemento memento)
	{
		String name = memento.getString(StockCompanyEditorInput.COMPANY_NAME);

		StockCompany company = Activator.getDefault().getCompany(name);

		assert company != null;

		StockCompanyEditorInput input = new StockCompanyEditorInput(company);

		return input;
	}

}
