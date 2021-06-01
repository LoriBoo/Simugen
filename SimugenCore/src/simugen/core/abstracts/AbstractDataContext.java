package simugen.core.abstracts;

import simugen.core.interfaces.DataContext;
import simugen.core.transfer.interfaces.PipeData;

/**
 * Abstract implementation of {@link DataContext}. Adds the field
 * {@link #contextOwner} to be exposed to the abstracted method
 * {@link #doProcessData(Object, PipeData)}<br>
 * <br>
 * <b>Subclasses should extend this class instead of implementing
 * {@link DataContext} interface directly.</b>
 * 
 * @author Lorelei
 *
 * @param <T>
 * @param <V>
 */
public abstract class AbstractDataContext<T, V extends PipeData<? extends Object>> implements DataContext<T, V> {
	private T contextOwner;

	/**
	 * The {@link #contextOwner} is the object that owns the process called by
	 * {@link #processData(PipeData)} and ultimately
	 * {@link #doProcessData(Object, PipeData)}.<br>
	 * <br>
	 * 
	 * @param contextOwner the owner of the process.
	 */
	public AbstractDataContext(T contextOwner) {
		this.contextOwner = contextOwner;
	}

	/**
	 * Method {@link #processData(PipeData)} is called by any data processor.
	 * Abstracted to {@link #doProcessData(Object, PipeData)} to allow subclasses
	 * exposure to the {@link #contextOwner}
	 * 
	 * @param data The data to be processed.
	 */
	@Override
	public void processData(V data) {
		doProcessData(contextOwner, data);
	}

	/**
	 * Abstracted method for subclasses to {@link Override}, in order to actually
	 * process the data. Exposes {@link #contextOwner} to the method.
	 * 
	 * @param componentContext The owner of the process.
	 * @param data             The data to be processed
	 */
	abstract protected void doProcessData(T componentContext, V data);
}
