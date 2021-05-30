package simugen.core.enums;

/**
 * FIFO - First in First out. This is the queuing method we would expect for a
 * system with customers. First customer in line is the first out to be served.
 * 
 * LIFO - Last in First out. This queuing method would be more for a system with
 * items being stacked on a pallet. The first element available to you is the
 * last one placed on the pile.
 * 
 * Priority - Requires a custom routine to be provided by subclasses.
 * 
 * @author Lorelei
 *
 */
public enum QueueMethod
{
	FIFO, LIFO, PRIORITY;
}
