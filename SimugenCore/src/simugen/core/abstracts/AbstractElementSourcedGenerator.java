package simugen.core.abstracts;

import simugen.core.defaults.ElementSourcedEvent;
import simugen.core.enums.SimTimeUnit;
import simugen.core.interfaces.DataGenerator;
import simugen.core.interfaces.Element;
import simugen.core.interfaces.EngineTick;

public abstract class AbstractElementSourcedGenerator implements DataGenerator<ElementSourcedEvent> {
	private final DataGenerator<Number> numberGen;

	private final SimTimeUnit timeUnit;

	public AbstractElementSourcedGenerator(DataGenerator<Number> numberGen, SimTimeUnit timeUnit) {
		this.numberGen = numberGen;

		this.timeUnit = timeUnit;
	}

	@Override
	public ElementSourcedEvent getNext(EngineTick tick) {
		final double rand = tick.getNextRand();

		final long duration = timeUnit.getMillis(numberGen.getNext(rand));

		final Element element = getElement();

		final long time = tick.getEventTime(duration);

		final ElementSourcedEvent event = new ElementSourcedEvent(element, time);

		return event;
	}

	abstract protected Element getElement();
}
