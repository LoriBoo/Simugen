package simugen.core.defaults;

import simugen.core.abstracts.AbstractElementSourcedGenerator;
import simugen.core.enums.TimeUnit;
import simugen.core.interfaces.DataGenerator;
import simugen.core.interfaces.Element;

public class DefaultElementSourcedGenerator
		extends AbstractElementSourcedGenerator
{
	public DefaultElementSourcedGenerator(DataGenerator<Number> numberGen,
			TimeUnit timeUnit)
	{
		super(numberGen, timeUnit);
	}

	@Override
	protected Element getElement()
	{
		return new DefaultElement();
	}

}
