package simugen.core.transfer.interfaces;

import simugen.core.components.interfaces.Component;

/**
 * Class InputPipe
 * 
 * InputPipes are children of a {@link Component}, and allows {@link PipeData}
 * of subType T to be received by the component
 * 
 * @author BASHH
 *
 * @param <T>
 */
public interface InputPipe<T extends PipeData<?>>
{
	/**
	 * Receive {@link PipeData}.
	 * 
	 * Components must receive pipe data without failure, regardless of if
	 * anything is done with the data.
	 * 
	 * @param pipeData
	 */
	public void getPipeData(T pipeData);

	/**
	 * Check if owning component is ready for pipe data.
	 * 
	 * @param pipeData
	 * @return
	 */
	public boolean isReady(T pipeData);

	public Component getOwner();
}
