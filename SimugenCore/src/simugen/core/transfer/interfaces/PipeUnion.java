package simugen.core.transfer.interfaces;

import simugen.core.components.interfaces.Component;

/**
 * Represents the joining of two pipes, an {@link OutputPipe} and an
 * {@link InputPipe}.
 * 
 * @author Lorelei
 *
 * @param <T>
 *            The {@link PipeData} the two pipes share.
 */
public interface PipeUnion<T extends PipeData<?>>
{

	/**
	 * @return The upstream {@link Component}'s {@link OutputPipe}.
	 */
	public OutputPipe<T, ?> getUpstreamPipe();

	/**
	 * @return The downstream {@link Component}'s {@link InputPipe}.
	 */
	public InputPipe<T> getDownStreamPipe();
}
