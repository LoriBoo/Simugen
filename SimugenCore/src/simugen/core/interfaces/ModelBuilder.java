package simugen.core.interfaces;

import java.lang.reflect.InvocationTargetException;

/**
 * Creates a new instance of the associated {@link Model}. <br>
 * <br>
 * The subclass of {@link Model} must have a default constructor that takes no
 * parameters, or the Default implementation of {@link ModelBuilder} will not
 * work.
 * 
 * @author Lorelei
 *
 */
public interface ModelBuilder
{

	/**
	 * @return A new instance of the {@link Model} that is associated with this
	 *         builder.
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public Model buildModel() throws InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException;
}
