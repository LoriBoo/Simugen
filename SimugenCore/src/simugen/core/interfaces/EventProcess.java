package simugen.core.interfaces;

public interface EventProcess
{
	public SimEvent process(double d);
	
	public boolean isComplete();
}
