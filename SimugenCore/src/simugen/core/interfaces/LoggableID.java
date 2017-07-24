package simugen.core.interfaces;

public interface LoggableID
{
	/**
	 * Returns an easily read identifier for the component. As the default, it's
	 * the short name of the top level class '@' its hashcode.
	 * 
	 * @return
	 */
	default String getLogID()
	{
		return this.getClass().getSimpleName().concat("@")
				.concat(String.valueOf(this.hashCode()));
	}
	
	public void setLogID(String ID);
}
