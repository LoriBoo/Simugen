package simugen.core.interfaces;

public interface SimComponent extends Readyable, Producer
{
	public SimEvent process(double d);
	public void setIsComplete(boolean complete);
	public boolean isComplete();
}
