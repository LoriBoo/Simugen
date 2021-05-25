package simugen.core.interfaces;

public interface Event extends LoggableID
{
	/**
	 * The time, in milliseconds from model start that this event was
	 * created.<br>
	 * Represents model time, not real time.
	 * 
	 * @return
	 */
	public long getTime();

	default String getLogMessage()
	{
		return this.getLogID();
	}

	public void Consume();

	public boolean isConsumed();

	//public int getModelRun();

	//public void setModelRun(int modelRun);
	
	public long getModelSeed();
	
	public void setModelSeed(long modelSeed);
}
