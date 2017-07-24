package simugen.core.defaults;

import simugen.core.interfaces.Model;
import simugen.core.interfaces.ModelBuilder;

public class DefaultModelBuilder implements ModelBuilder
{

	private final Class<? extends Model> modelClass;

	/**
	 * Only use DefaultModelBuilder if the model class has 1 default constructor
	 * with no parameters
	 * 
	 * @param modelClass
	 */
	public DefaultModelBuilder(Class<? extends Model> modelClass)
	{
		assert modelClass.getConstructors().length == 1;

		assert modelClass.getConstructors()[0].getParameterCount() == 0;

		this.modelClass = modelClass;
	}

	@Override
	public Model buildModel() throws InstantiationException, IllegalAccessException
	{
		return modelClass.newInstance();
	}

}
