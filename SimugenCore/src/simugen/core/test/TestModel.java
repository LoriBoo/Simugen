package simugen.core.test;

import simugen.core.abstracts.AbstractSimModel;
import simugen.core.defaults.DefaultSimSink;
import simugen.core.defaults.DefaultSimSource;
import simugen.core.interfaces.SimModel;
import simugen.core.interfaces.SimSink;
import simugen.core.interfaces.SimSource;
import simugen.core.rng.TriangularNumberGenerator;

public class TestModel extends AbstractSimModel
{

	@Override
	public void startUp()
	{
		final TriangularNumberGenerator gen = new TriangularNumberGenerator(1,
				5, 10);
		
		final SimSource source = new DefaultSimSource(gen, 10);
		final SimSink sink = new DefaultSimSink();

		source.getOutputHook().hookTo(sink.getInputHook());

		addComponent(source);
		addComponent(sink);
	}

	@Override
	public void onShutdown()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public SimModel getCopy()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isReady()
	{
		return true;
	}

}
