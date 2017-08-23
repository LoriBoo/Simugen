package simugen.core.abstracts;

import simugen.core.components.interfaces.Component;
import simugen.core.interfaces.DataContext;
import simugen.core.transfer.interfaces.PipeData;

public abstract class AbstractDataContext<T extends Component, V extends PipeData<? extends Object>>
		implements DataContext<T, V>
{
	private T componentContext;

	public AbstractDataContext(T componentContext)
	{
		this.componentContext = componentContext;
	}

	@Override
	public void processData(V data)
	{
		doProcessData(componentContext, data);
	}

	abstract protected void doProcessData(T componentContext, V data);
}
