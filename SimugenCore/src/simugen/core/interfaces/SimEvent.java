package simugen.core.interfaces;

/**
 * Interface for an event in the simulation.
 * 
 * An event is defined as something that occurs at a discrete time slice in a
 * simulation.
 * 
 * The simulation engine will prompt a model for events, and then process them.
 * The engine has no idea what an event is, and only keeps track of the time
 * that has elapsed in the model, and events after they fire.
 * 
 * @formatter:off
 * 
 * Engine ->  Model
 * 		prompts model for events with new random number
 * 		Model determines event(s) that fired, and at what time
 * Model -> Engine
 * 		Relays events to be processed
 * 		Engine processes events.
 * Engine -> Model
 * 		Ensures no new events have been generated for this time slice
 * 
 * Cycle repeats until engine gets a special event that denotes the model has completed.
 * 
 * @formatter:on
 * @author BASHH
 *
 */
public interface SimEvent {
	
	/**
	 * Returns a printable message for the event, for the console.
	 * 
	 * @param style
	 * @return
	 */
	public String printEvent(LoggingStyle style);

	/**
	 * Returns whether the event has been processed
	 * 
	 * @return
	 */
	public boolean processed();

	/**
	 * Sets the event to processed. Once an event is processed it cannot be reversed
	 */
	public void setProcessed();

	/**
	 * Returns the millisecond time-stamp from the model epoch for this event.
	 * 
	 * @return
	 */
	public long getTime();

	@Deprecated
	public boolean response();

	@Deprecated
	public void setProcessedResponse(boolean processed, boolean response);
}
