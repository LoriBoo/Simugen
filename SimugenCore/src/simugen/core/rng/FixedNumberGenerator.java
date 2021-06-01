package simugen.core.rng;

import simugen.core.interfaces.DataGenerator;
import simugen.core.interfaces.EngineTick;

/**
 * {@link DataGenerator} that always generates the same {@link Number}.
 * 
 * @author Lorelei
 *
 */
public class FixedNumberGenerator implements DataGenerator<Number> {
	final double fixed;

	public FixedNumberGenerator(double fixed) {
		this.fixed = fixed;
	}

	@Override
	public Number getNext(EngineTick tick) {
		return fixed;
	}

	public boolean isReady() {
		return true;
	}
}
