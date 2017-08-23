package simugen.core.transfer.interfaces;

import simugen.core.components.interfaces.Component;
import simugen.core.interfaces.Event;

/**
 * Class OutputPipe
 * 
 * OutputPipe are children of a {@link Component}, and allows {@link PipeData}
 * of subType T to be sent by the component
 * 
 * @author BASHH
 *
 * @param <T>
 */
public interface OutputPipe<T extends PipeData<?>, V extends InputPipe<T>>
{
	/**
	 * Checks downstream component if it can receive piped data.
	 * 
	 * @param pipeData
	 * @return
	 */
	public boolean canSendPipeData(T pipeData);

	/**
	 * Send {@link PipeData}.
	 * 
	 * @param pipeData
	 * @return Event - an event generated for sending the data.
	 */
	public Event sendPipeData(T pipeData);

	/**
	 * Every OutputPipe must be unioned to an {@link InputPipe}.
	 * 
	 * <b>Note:</b> InputPipes have no knowledge of their unions, and can be
	 * unioned to multiple OutputPipes.
	 * 
	 * OutputPipes, however must be unioned to only one InputPipe.
	 * 
	 * @param inputPipe
	 */
	public void union(V inputPipe);

	/**
	 * Return the PipeUnion for this OutputPipe
	 * 
	 * @return
	 */
	public PipeUnion<T> getUnion();

}
