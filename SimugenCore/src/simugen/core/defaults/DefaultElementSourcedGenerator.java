package simugen.core.defaults;

import simugen.core.abstracts.AbstractElementSourcedGenerator;
import simugen.core.enums.SimTimeUnit;
import simugen.core.interfaces.DataGenerator;
import simugen.core.interfaces.Element;

/**
 * Default implementation of {@link DataGenerator} for the type
 * {@link ElementSourcedEvent}.<br>
 * <br>
 * Generates a new {@link DefaultElement} based on the {@link DataGenerator} and
 * {@link SimTimeUnit} passed to the constructor.
 * 
 * @author Lorelei
 *
 */
final public class DefaultElementSourcedGenerator
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
