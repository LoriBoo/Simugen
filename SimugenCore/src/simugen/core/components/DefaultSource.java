package simugen.core.components;

import simugen.core.components.abstracts.AbstractSource;
import simugen.core.defaults.ElementSourcedEvent;
import simugen.core.interfaces.DataGenerator;

public class DefaultSource extends AbstractSource
{
	public DefaultSource(DataGenerator<ElementSourcedEvent> elementGenerator,
			int arrivals)
	{
		super(elementGenerator, arrivals);
	}

	@Override
	protected boolean canGenerate()
	{
		return sourced < arrivals;
	}
}
