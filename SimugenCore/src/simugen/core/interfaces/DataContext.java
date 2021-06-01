package simugen.core.interfaces;

import simugen.core.transfer.interfaces.PipeData;

/**
 * This interface wraps a subclass of {@link PipeData} such that it has access to
 * the context owner of this {@link DataContext}.<br>
 * <br>
 * Look at the implementation of {@link DefaultElementTransferDataContex}t for a
 * good example of how to use this interface.<br>
 * <br>
 * <b>Subclasses should extend AbstractDataContext instead of implementing this
 * interface.</b>
 * 
 * @author Lorelei
 *
 * @param <T>
 * @param <V>
 */
public interface DataContext<T, V extends PipeData<? extends Object>> {
	public void processData(V data);
}
