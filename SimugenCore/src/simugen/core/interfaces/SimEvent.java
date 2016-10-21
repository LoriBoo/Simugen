package simugen.core.interfaces;

public interface SimEvent
{
	public String printEvent(LoggingStyle style);
	public boolean processed();
	public boolean response();
	public void setProcessedResponse(boolean processed, boolean response);
	public long getTime();
}
