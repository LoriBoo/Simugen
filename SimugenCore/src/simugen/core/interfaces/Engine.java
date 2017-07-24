package simugen.core.interfaces;

import java.io.PrintStream;

public interface Engine
{
	public void setModelBuilder(ModelBuilder builder);

	public void start();

	public void start(long seed);

	public void stop();

	public double getNext();

	public void setStreamOut(PrintStream out);

	public long getSeed();

	public boolean isRunning();

	public void setRuns(int runs);

	public long getMilliseconds();
}
