package coffeeshopmodel.src;

import java.util.concurrent.TimeUnit;

import simugen.core.abstracts.AbstractModel;
import simugen.core.components.DefaultQueue;
import simugen.core.components.DefaultRouter;
import simugen.core.components.DefaultServer;
import simugen.core.components.DefaultSink;
import simugen.core.components.DefaultSource;
import simugen.core.components.interfaces.Queue;
import simugen.core.components.interfaces.Router;
import simugen.core.components.interfaces.Server;
import simugen.core.components.interfaces.Sink;
import simugen.core.components.interfaces.Source;
import simugen.core.defaults.DefaultElementDurationListener;
import simugen.core.defaults.NumberedElementSourcedGenerator;
import simugen.core.enums.SimTimeUnit;
import simugen.core.interfaces.DataGenerator;
import simugen.core.rng.TriangularNumberGenerator;

public class CoffeeShopModel extends AbstractModel {

	@Override
	public void onShutdown() {

	}

	@Override
	public void onStartup() {
		final DataGenerator<Number> gen = new TriangularNumberGenerator(1, 3, 4);

		final DataGenerator<Number> servedTime = new TriangularNumberGenerator(5, 6, 15);

		final NumberedElementSourcedGenerator generator = new NumberedElementSourcedGenerator("Customer", gen,
				SimTimeUnit.MINUTE);

		final Source source = new DefaultSource(generator, 10);

		final Sink sink = new DefaultSink();

		final Queue queue = new DefaultQueue();

		final Server server1 = new DefaultServer(servedTime, TimeUnit.MINUTES);

		final Server server2 = new DefaultServer(servedTime, TimeUnit.MINUTES);

		final Router router = new DefaultRouter();

		source.setLogID("Customer_Source");

		sink.setLogID("Customer_Sink");

		queue.setLogID("Coffee_Shop_Queue");

		server1.setLogID("Barista_Server_1");

		server2.setLogID("Barista_Server_2");

		router.setLogID("Customer_Router");

		source.getTransferOutputPipe().union(queue.getTransferInputPipe());

		queue.getTransferOutputPipe().union(router.getTransferInputPipe());

		router.addTransferOutputPipe(server1).union(server1.getTransferInputPipe());

		router.addTransferOutputPipe(server2).union(server2.getTransferInputPipe());

		server1.getTransferOutputPipe().union(sink.getTransferInputPipe());

		server2.getTransferOutputPipe().union(sink.getTransferInputPipe());

		addComponent(source);

		addComponent(sink);

		addComponent(router);

		addComponent(queue);

		addComponent(server1);

		addComponent(server2);

		String coffeeShopDB = getOutputLocation() + "CoffeeShop.db";

		DefaultElementDurationListener queueListener = new DefaultElementDurationListener(queue, coffeeShopDB,
				SimTimeUnit.MINUTE, "QueueData", "Customer", "Queue", run, epoch);

		DefaultElementDurationListener server1Listener = new DefaultElementDurationListener(server1, coffeeShopDB,
				SimTimeUnit.MINUTE, "ServerData", "Customer", "Barista", run, epoch);

		DefaultElementDurationListener server2Listener = new DefaultElementDurationListener(server2, coffeeShopDB,
				SimTimeUnit.MINUTE, "ServerData", "Customer", "Barista", run, epoch);

		addListener(queueListener);

		addListener(server1Listener);

		addListener(server2Listener);
	}

	@Override
	public boolean isReady() {
		return true;
	}

}
