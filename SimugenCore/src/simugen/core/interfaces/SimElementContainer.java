package simugen.core.interfaces;

public interface SimElementContainer
{
	/**
	 * Receive an element into our container. Element is ignored if the
	 * container can't receive.
	 * 
	 * @param element
	 */
	public void doRecieveElement(SimElement element);

	/**
	 * Removes an element from our container. Element is ignored if it is not in
	 * the container.
	 * 
	 * @param element
	 */
	public void doRemoveElement(SimElement element);

	/**
	 * Returns whether or not there is space to receive the element currently.
	 * 
	 * @return
	 */
	public boolean canRecieveElement();
}
