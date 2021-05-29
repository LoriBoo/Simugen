package simugen.core.defaults;

import simugen.core.abstracts.AbstractElementSourcedGenerator;
import simugen.core.enums.SimTimeUnit;
import simugen.core.interfaces.DataGenerator;
import simugen.core.interfaces.Element;

public class NumberedElementSourcedGenerator extends AbstractElementSourcedGenerator {
	private int cust = 1;

	private final String prefix;

	public NumberedElementSourcedGenerator(String prefix, DataGenerator<Number> numberGen, SimTimeUnit timeUnit) {
		super(numberGen, timeUnit);

		this.prefix = prefix;
	}

	public NumberedElementSourcedGenerator(DataGenerator<Number> numberGen, SimTimeUnit timeUnit) {
		super(numberGen, timeUnit);

		this.prefix = "Element";
	}

	@Override
	protected Element getElement() {
		final Element e = new DefaultElement();

		e.setLogID(prefix.concat("_" + String.valueOf(cust)));

		cust++;

		return e;
	}
}
