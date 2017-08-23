package simugen.core.interfaces;

import java.io.PrintStream;

import simugen.core.enums.TimeStamper;

public interface Engine
{
	public void setModelBuilder(ModelBuilder builder);

	public void start();

	public void start(long seed);

	public void stop();
	
	public void setEpoch(long epoch);
	
	public long getEpoch();

	public double getNext();

	public void setStreamOut(PrintStream out);

	public long getSeed();

	public boolean isRunning();

	public void setRuns(int runs);

	public long getMilliseconds();

	void setTimeStamper(TimeStamper timeStamper);
}
