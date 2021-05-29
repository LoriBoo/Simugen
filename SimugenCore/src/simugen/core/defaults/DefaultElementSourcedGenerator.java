package simugen.core.defaults;


import simugen.core.abstracts.AbstractElementSourcedGenerator;
import simugen.core.enums.SimTimeUnit;
import simugen.core.interfaces.DataGenerator;
import simugen.core.interfaces.Element;

public class DefaultElementSourcedGenerator
		extends AbstractElementSourcedGenerator
{
	public DefaultElementSourcedGenerator(DataGenerator<Number> numberGen,
			SimTimeUnit timeUnit)
	{
		super(numberGen, timeUnit);
	}

	@Override
	protected Element getElement()
	{
		return new DefaultElement();
	}

}
