package simugen.core.interfaces;

/**
 * Classes that generate data will implement this interface.
 * 
 * @author Lorelei
 *
 * @param <T>
 */
public interface DataGenerator<T extends Object> {

	/**
	 * 
	 * @param tick The 'tick' of the {@link Engine}.
	 * @return The next object that this generator generates.
	 */
	public T getNext(EngineTick tick);
}
