package simugen.core.transfer.interfaces;

import simugen.core.interfaces.Model;

/**
 * {@link PipeData} is data that is sent through {@link OutputPipe}s and
 * {@link InputPipe}s
 * 
 * @author Lorelei
 *
 * @param <T> The class of the data to be sent.
 */
public interface PipeData<T extends Object> {

	/**
	 * @return The data being sent.
	 */
	public T getData();

	/**
	 * @return The {@link Model} time in milliseconds, that the data was sent.
	 */
	public long getTime();
}
