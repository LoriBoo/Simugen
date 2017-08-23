package simugen.core.rng;

import simugen.core.interfaces.DataGenerator;

public class FixedNumberGenerator implements DataGenerator<Number>
{
	final double fixed;

	public FixedNumberGenerator(double fixed)
	{
		this.fixed = fixed;
	}
	
	@Override
	public Number getNext(double d)
	{
		return fixed;
	}
	
	public boolean isReady()
	{
		return true;
	}
}
