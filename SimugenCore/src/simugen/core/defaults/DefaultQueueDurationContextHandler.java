package simugen.core.defaults;

import java.util.HashMap;
import java.util.Map;

import simugen.core.abstracts.AbstractDurationContextHandler;
import simugen.core.components.interfaces.Component;

public class DefaultQueueDurationContextHandler extends AbstractDurationContextHandler {

	private DefaultElementDurationListener listener;

	private String elementColumnTitle;

	private int run;

	public DefaultQueueDurationContextHandler(int run, Component component,
			DefaultElementDurationListener durationListener, String elementColumnTitle) {
		super(component);

		this.listener = durationListener;

		this.elementColumnTitle = elementColumnTitle;

		this.run = run + 1;
	}

	@Override
	protected void doHandleEvent(ElementTransferEvent event) {
		Double minutes = this.duration * 1.6666666666667E-5;

		String run = String.valueOf(this.run);

		String element = event.getElement().getLogID();

		String duration = String.valueOf(minutes);

		Map<String, String> mapRowValues = new HashMap<>();

		mapRowValues.put("Run", run);
		mapRowValues.put(this.elementColumnTitle, element);
		mapRowValues.put("Duration", duration);

		this.listener.insert(mapRowValues);
	}

}
