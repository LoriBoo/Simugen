package simugen.core.components.interfaces;

import simugen.core.components.abstracts.AbstractComponent;
import simugen.core.interfaces.DataContext;
import simugen.core.interfaces.Engine;
import simugen.core.interfaces.EngineTick;
import simugen.core.interfaces.LoggableID;
import simugen.core.transfer.ElementTransferData;
import simugen.core.transfer.interfaces.PipeData;

/**
 * Class Component.<br>
 * <br>
 * Components are connected to other components to convey Elements and other
 * interactions<br>
 * <br>
 * <b>Subclasses {@link Component}s should subclass {@link AbstractComponent},
 * and not implement {@link Component} directly.</b>
 * 
 * @author Lorelei
 *
 */
public interface Component extends LoggableID {
	final static String NO_INPUT_PIPE_ERR = "Component does not have an InputPipe to receive data.";

	/**
	 * For this EngineTick, get the events from the component.<br>
	 * <br>
	 * Note: Many {@link Component}s can only generate events once per EngineTick,
	 * and may have resultant events based on other events that have occurred during
	 * this {@link EngineTick}. <br>
	 * <br>
	 * {@link Router}s and {@link Queue}s are examples of {@link Component}s that
	 * are allowed to generate multiple events per engine tick.<br>
	 * <br>
	 * This method may seem counter-intuitive since it is labeled as a getter, but
	 * is a <code>void</code> return type;<br>
	 * <br>
	 * <b>This method places any gotten events into the EngineTick.<b>
	 * 
	 * @param tick the current {@link EngineTick} of the {@link Engine}.
	 */
	public void getEvents(EngineTick tick);

	/**
	 * Get the current capacity of the Component, eg. number of free slots left for
	 * Elements to be received. -1 is infinite.
	 * 
	 * @return
	 */
	public int getElementCapacity();

	/**
	 * Receive piped data.<br>
	 * <br>
	 * Classes that implement {@link Component} will handle received
	 * {@link PipeData}. There is no upstream check on whether or not this
	 * {@link Component} has a {@link DataContext} in which to handle this
	 * {@link PipeData}, and therefore subclasses must handle this.
	 * 
	 * @param data The {@link PipeData} from the upstream {@link Component}.
	 */
	public void receiveData(PipeData<?> data);

	/**
	 * Add a {@link DataContext} for a specific {@link PipeData} subclass.
	 * 
	 * Extends {@link Component} ability to process {@link PipeData}.
	 * 
	 * @param dataClass
	 * @param dataContext
	 */
	public void addProcessDataContext(Class<?> dataClass, DataContext<?, ? extends PipeData<?>> dataContext);

	/**
	 * 
	 * @param elementTransferData data for the potential transfer.
	 * @return <b>True</b> if this {@link Component} can receive an element,
	 *         <b>false</b> otherwise.
	 */
	public boolean canReceiveElement(ElementTransferData elementTransferData);
}
