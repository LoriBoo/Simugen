package simugen.core.components;

import simugen.core.components.abstracts.AbstractQueue;
import simugen.core.enums.QueueMethod;

public class DefaultQueue extends AbstractQueue
{

	public DefaultQueue()
	{

	}

	public DefaultQueue(int totalCapacity)
	{
		super(totalCapacity);
	}

	/**
	 * Does not support QueueMethod.PRIORITY
	 * 
	 * @param method
	 */
	public DefaultQueue(QueueMethod method)
	{
		super(method);

		assert !(method.equals(QueueMethod.PRIORITY));
	}

	/**
	 * Does not support QueueMethod.PRIORITY
	 * 
	 * @param method
	 */
	public DefaultQueue(int totalCapacity, QueueMethod method)
	{
		super(totalCapacity, method);

		assert !(method.equals(QueueMethod.PRIORITY));
	}
}
