package simugen.core.interfaces;

public interface DataGenerator<T extends Object> extends Readyable
{
	public T getNext(double d);
}
