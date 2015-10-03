package simugen.core.interfaces;

public interface SimComponent extends Readyable
{
	public SimEvent process(double d);
	public boolean isComplete();
}
