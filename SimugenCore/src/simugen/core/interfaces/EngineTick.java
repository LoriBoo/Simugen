package simugen.core.interfaces;

import java.util.List;

public interface EngineTick
{
	/**
	 * Get the next random number from the Engine.<br>
	 * <br>
	 * Used to generate random variates from DataGenerators.
	 * 
	 * @return
	 */
	public double getNextRand();

	/**
	 * Gets the time at which the event should fire, based on the current time
	 * of the engine.<br>
	 * <br>
	 * On the backend, duration gets saved as the new time for the engine once
	 * the EngineTick is done. Only update the new event if the duration is
	 * longer than the previous event's duration.
	 * 
	 * @param duration
	 * @return
	 */
	public long getEventTime(long duration);

	/**
	 * Add the event to the EngineTick, to be processed by the Engine
	 * 
	 * @param event
	 */
	public void addEvent(Event event);

	/**
	 * Add all of the events in the list to this EngineTick
	 * 
	 * @param listEvents
	 */
	public void addAllEvents(List<Event> listEvents);

	/**
	 * Returns the list of Events. Intended to be called by the Engine.
	 * 
	 * @return
	 */
	public List<Event> getEvents();

	public void setCurrentTime(long milliseconds);
}
