package simugen.core.test;

import simugen.core.abstracts.AbstractModel;
import simugen.core.components.DefaultQueue;
import simugen.core.components.DefaultServer;
import simugen.core.components.DefaultSink;
import simugen.core.components.DefaultSource;
import simugen.core.components.interfaces.Queue;
import simugen.core.components.interfaces.Server;
import simugen.core.components.interfaces.Sink;
import simugen.core.components.interfaces.Source;
import simugen.core.defaults.NumberedElementSourcedGenerator;
import simugen.core.enums.TimeUnit;
import simugen.core.interfaces.DataGenerator;
import simugen.core.rng.FixedNumberGenerator;

public class TestNewModel extends AbstractModel
{

	@Override
	public void onShutdown()
	{

	}

	@Override
	public void onStartup()
	{
		final DataGenerator<Number> gen = new FixedNumberGenerator(1);

		// final DataGenerator<Number> servedTime = new
		// TriangularNumberGenerator(
		// 1, 5, 7);

		final NumberedElementSourcedGenerator generator = new NumberedElementSourcedGenerator(
				"Customer", gen, TimeUnit.MILLISECOND);

		final Source source = new DefaultSource(generator, 10);

		final Sink sink = new DefaultSink();

		final Queue queue = new DefaultQueue();

		final Server server = new DefaultServer(gen, TimeUnit.SECOND);

		source.setLogID("Customer_Source");

		sink.setLogID("Customer_Sink");

		queue.setLogID("Coffee_Shop_Queue");

		server.setLogID("Barista_Server");

		source.getTransferOutputPipe().union(queue.getTransferInputPipe());

		queue.getTransferOutputPipe().union(server.getTransferInputPipe());

		server.getTransferOutputPipe().union(sink.getTransferInputPipe());

		addComponent(source);

		addComponent(sink);

		addComponent(queue);

		addComponent(server);
	}

	@Override
	public boolean isReady()
	{
		return true;
	}

}
