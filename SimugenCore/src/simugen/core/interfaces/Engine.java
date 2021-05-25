package simugen.core.interfaces;

import simugen.core.data.interfaces.EventListener;

public interface Engine {
	public void setModelBuilder(ModelBuilder builder);

	public void start();

	public void start(long seed);

	public void stop();

	public void setEpoch(long epoch);

	public long getEpoch();

	public double getNext();

	public long getSeed();

	public boolean isRunning();

	public void setRuns(int runs);

	public long getMilliseconds();

//	public void setTimeStamper(TimeStamper timeStamper);

	public void printSeed();

	public void addListener(EventListener listener);
}
