package simugen.core.transfer.interfaces;

public interface PipeDataOut<T extends PipeData<?>>
{
	public boolean sendPipeData(T data);

	public boolean canPipeDataOut(T data);
}
