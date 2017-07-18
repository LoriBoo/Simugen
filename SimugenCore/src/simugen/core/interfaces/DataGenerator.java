package simugen.core.interfaces;

public interface DataGenerator<T extends Object>
{
	public T getNext(double d);
}
