package simugen.core.transfer.interfaces;

import simugen.core.components.interfaces.Component;

/**
 * Class InputPipe
 * 
 * InputPipes are children of a {@link Component}, and allows {@link PipeData}
 * of subType T to be received by the component
 * 
 * @author Lorelei
 *
 * @param <T>
 */
public interface InputPipe<T extends PipeData<?>> {
	/**
	 * Receive {@link PipeData}.
	 * 
	 * Components must receive pipe data without failure, regardless of if anything
	 * is done with the data.
	 * 
	 * @param pipeData The {@link PipeData} being sent.
	 */
	public void getPipeData(T pipeData);

	/**
	 * Check if owning component is ready for pipe data.
	 * 
	 * @param pipeData The {@link PipeData} that is ready to be sent.
	 * @return <b>True</b> if the {@link Component} is ready to accept the
	 *         {@link PipeData}. <b>False</b> otherwise
	 */
	public boolean isReady(T pipeData);

	/**
	 * @return The owner of this {@link InputPipe}.
	 */
	public Component getOwner();
}
