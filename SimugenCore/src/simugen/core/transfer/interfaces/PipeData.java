package simugen.core.transfer.interfaces;

public interface PipeData<T extends Object>
{
	public T getData();
	
	public long getTime();
}
