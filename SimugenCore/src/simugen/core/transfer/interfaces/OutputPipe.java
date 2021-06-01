package simugen.core.transfer.interfaces;

import simugen.core.components.interfaces.Component;
import simugen.core.interfaces.Event;

/**
 * Class OutputPipe
 * 
 * OutputPipe are children of a {@link Component}, and allows {@link PipeData}
 * of subclass T to be sent by the component
 * 
 * @author Lorelei
 *
 * @param <T> Subclass of {@link PipeData} that this {@link OutputPipe} sends.
 * @param <V> Subclass of {@link InputPipe} that this {@link OutputPipe} can be
 *            unioned to.
 */
public interface OutputPipe<T extends PipeData<?>, V extends InputPipe<T>> {
	/**
	 * Checks downstream component if it can receive piped data.
	 * 
	 * @param pipeData To potentially send.
	 * @return <b>True</b> if the downstream {@link Component} can receive the
	 *         {@link PipeData}. <b>False</b> otherwise.
	 */
	public boolean canSendPipeData(T pipeData);

	/**
	 * @param pipeData The {@link PipeData} to be sent to the downstream
	 *                 {@link Component}.
	 * @return The event generated for sending the data.
	 */
	public Event sendPipeData(T pipeData);

	/**
	 * Every OutputPipe must be unioned to an {@link InputPipe}.
	 * 
	 * <b>Note:</b> InputPipes have no knowledge of their unions, and can be unioned
	 * to multiple OutputPipes.
	 * 
	 * OutputPipes, however must be unioned to only one InputPipe.
	 * 
	 * @param inputPipe The {@link InputPipe} to be unioned to this
	 *                  {@link OutputPipe}.
	 */
	public void union(V inputPipe);

	/**
	 * Return the PipeUnion for this OutputPipe
	 * 
	 * @return
	 */
	public PipeUnion<T> getUnion();

}
