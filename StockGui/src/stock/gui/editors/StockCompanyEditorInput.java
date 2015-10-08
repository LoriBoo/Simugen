package stock.gui.editors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPersistableElement;

import stock.scraper.builder.StockCompany;

public class StockCompanyEditorInput implements IEditorInput
{
	public static final String COMPANY_NAME = "COMPANY_NAME";

	private final StockCompany company;

	public StockCompanyEditorInput(StockCompany company)
	{
		this.company = company;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter)
	{
		return adapter.cast(adapter.isInstance(company) ? company : null);
	}

	@Override
	public boolean exists()
	{
		return true;
	}

	@Override
	public ImageDescriptor getImageDescriptor()
	{
		return null;
	}

	@Override
	public String getName()
	{
		return company.getCompanyName();
	}

	@Override
	public IPersistableElement getPersistable()
	{
		IPersistableElement elemnt = new IPersistableElement()
		{
			
			@Override
			public void saveState(IMemento memento)
			{
				memento.putString(COMPANY_NAME, company.getCompanyName());
			}
			
			@Override
			public String getFactoryId()
			{
				return StockCompanyPersistenceFactory.ID;
			}
		};
		return elemnt;
	}

	@Override
	public String getToolTipText()
	{
		return company.getCompanyName();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (StockCompanyEditorInput.class.isInstance(obj))
		{
			return StockCompanyEditorInput.class.cast(obj).company.equals(this.company);
		}
		return false;
	}

}
