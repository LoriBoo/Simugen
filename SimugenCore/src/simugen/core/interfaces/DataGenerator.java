package simugen.core.interfaces;

public interface DataGenerator<T extends Object>
{
	default T getNext(double d)
	{
		throw new IllegalAccessError();
	}

	default T getNext(EngineTick tick)
	{
		throw new IllegalAccessError();
	}
}
