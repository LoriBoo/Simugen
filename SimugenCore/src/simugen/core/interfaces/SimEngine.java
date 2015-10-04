package simugen.core.interfaces;

import java.io.PrintStream;

public interface SimEngine
{
	public void setModel(SimModel model);
	public void start();
	public void start(long seed);
	public void stop();
	public double getNext();
	public void setStreamOut(PrintStream out);
	public void setLoggingStyle(LoggingStyle log);
	public long getSeed();
	public void addEventListener(SimEventListener e);
}
