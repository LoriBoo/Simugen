package simugen.core.components.interfaces;

import simugen.core.interfaces.DataContext;
import simugen.core.interfaces.EngineTick;
import simugen.core.interfaces.LoggableID;
import simugen.core.transfer.ElementTransferData;
import simugen.core.transfer.interfaces.PipeData;

/**
 * Class Component.
 * 
 * Components are connected to other components to convey Elements and other
 * interactions
 * 
 * @author BASHH
 *
 */
public interface Component extends LoggableID
{
	final static String NO_INPUT_PIPE_ERR = "Component does not have an InputPipe to receive data.";

	/**
	 * For this EngineTick, get the events from the component.<br>
	 * <br>
	 * Note: Components can only generate events once per EngineTick, but it may
	 * have resultant events based on other events that have occurred during
	 * this EngineTick <br>
	 * <br>
	 * This method may seem counter-intuitive since it is labeled as a getter,
	 * but is a <code>void</code> return type;<br>
	 * <br>
	 * <b>This method places any gotten events into the EngineTick.<b>
	 * 
	 * @param tick
	 */
	public void getEvents(EngineTick tick);

	/**
	 * Get the current capacity of the Component, eg. number of free slots left
	 * for Elements to be received. -1 is infinite.
	 * 
	 * @return
	 */
	public int getElementCapacity();

	/**
	 * Receive piped data.
	 * 
	 * @param data
	 */
	public void receiveData(PipeData<?> data);

	/**
	 * Add a DataContext for a specific PipedData subclass.
	 * 
	 * Extends Component ability to process PipedData.
	 * 
	 * @param dataClass
	 * @param dataContext
	 */
	public void addProcessDataContext(Class<?> dataClass,
			DataContext<?, ? extends PipeData<?>> dataContext);

	public boolean canReceiveElement(ElementTransferData pipeData);
}
