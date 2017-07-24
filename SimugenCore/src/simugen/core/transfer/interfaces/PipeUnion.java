package simugen.core.transfer.interfaces;

public interface PipeUnion<T extends PipeData<?>>
{
	public OutputPipe<T, ?> getUpstreamPipe();

	public InputPipe<T> getDownStreamPipe();
}
