package simugen.core.defaults;

import java.lang.reflect.InvocationTargetException;

import simugen.core.interfaces.Model;
import simugen.core.interfaces.ModelBuilder;

/**
 * Default implementation of {@link ModelBuilder}.
 * 
 * @author Lorelei
 *
 */
final public class DefaultModelBuilder implements ModelBuilder
{

	private final Class<? extends Model> modelClass;

	/**
	 * Only use DefaultModelBuilder if the model class has 1 default constructor
	 * with no parameters
	 * 
	 * @param modelClass
	 *            The subclass of {@link Model} which this {@link ModelBuilder}
	 *            will build new instances of.
	 */
	public DefaultModelBuilder(Class<? extends Model> modelClass)
	{
		assert modelClass.getConstructors().length == 1;

		assert modelClass.getConstructors()[0].getParameterCount() == 0;

		this.modelClass = modelClass;
	}

	/**
	 * @return The new instance of the {@link Model} subclass that this
	 *         {@link ModelBuilder} was instantiated with.
	 */
	@Override
	public Model buildModel() throws InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException
	{
		return modelClass.getDeclaredConstructor().newInstance();
	}

}
