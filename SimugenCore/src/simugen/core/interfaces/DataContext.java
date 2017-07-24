package simugen.core.interfaces;

import simugen.core.transfer.interfaces.PipeData;

public interface DataContext<T extends Component, V extends PipeData<? extends Object>>
{
	public void processData(V data);
}
