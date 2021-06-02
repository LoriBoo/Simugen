package simugen.core.interfaces;

/**
 * Classes implement this interface to give it an easily read identifier for
 * logging. The default is the simple name of the class '@' and its hashcode.
 * 
 * @author Lorelei
 *
 */
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

	/**
	 * @param ID
	 *            set the {@link Element}'s string identifier used for logging.
	 *            This ID should be unique, if overridden.
	 */
	public void setLogID(String ID);
}
