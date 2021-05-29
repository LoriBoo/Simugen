package simugen.gui;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;

import simugen.core.data.interfaces.EventListener;
import simugen.core.defaults.DefaultModelBuilder;
import simugen.core.defaults.ModelFinishedEvent;
import simugen.core.interfaces.Engine;
import simugen.core.interfaces.Event;
import simugen.core.interfaces.Model;
import simugen.gui.interfaces.ModelRunner;
import simugen.gui.interfaces.RefreshableView;

public class SimModelRunner implements ModelRunner {

	@Override
	public void execute() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {

		Model model = SimActivator.getDefault().getModelClass().getDeclaredConstructor().newInstance();

		Engine engine = SimActivator.getDefault().getModelEngine();

		Calendar calendar = Calendar.getInstance();

		calendar.set(2021, 0, 1, 10, 0, 0);

		engine.setEpoch(calendar.getTimeInMillis());

		// engine.setTimeStamper(TimeStamper.SHORT_DATE_AMPM);

		engine.setModelBuilder(new DefaultModelBuilder(model.getClass()));

		engine.addListener(new EventListener() {

			@Override
			public void listen(Event event) {
				for (RefreshableView view : SimActivator.getDefault().getRefreshableViews()) {
					view.refresh();
				}
			}

			@Override
			public Class<? extends Event> getEventType() {
				// TODO Auto-generated method stub
				return ModelFinishedEvent.class;
			}
		});

		engine.setRuns(5);

		engine.start();

		engine.printSeed();
	}

}
