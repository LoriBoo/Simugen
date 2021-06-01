package simugen.core.enums;

/**
 * <b><i>FIFO</i></b> - First in First out. This is the queuing method we would
 * expect for a system with customers. First customer in line is the first out
 * to be served.<br>
 * <br>
 * <b><i>LIFO</i></b> - Last in First out. This queuing method would be more for
 * a system with items being stacked on a pallet. The first element available to
 * you is the last one placed on the pile. <br>
 * <br>
 * <b><i>Priority</i></b> - Requires a custom routine to be provided by
 * subclasses.
 * 
 * @author Lorelei
 *
 */
public enum QueueMethod {
	FIFO, LIFO, PRIORITY;
}
