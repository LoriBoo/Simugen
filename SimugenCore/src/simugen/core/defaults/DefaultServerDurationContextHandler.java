package simugen.core.defaults;

import java.util.HashMap;
import java.util.Map;

import simugen.core.abstracts.AbstractDurationContextHandler;
import simugen.core.components.interfaces.Component;
import simugen.core.enums.TimeUnit;

public class DefaultServerDurationContextHandler extends AbstractDurationContextHandler {

	private DefaultElementDurationListener listener;

	private String elementColumnTitle;

	private String serverColumnTitle;

	private int run;
	
	private TimeUnit unit;

	public DefaultServerDurationContextHandler(int run, Component component,
			DefaultElementDurationListener durationListener, String elementColumnTitle, String serverColumnTitle,
			TimeUnit unit) {
		super(component);

		this.listener = durationListener;

		this.elementColumnTitle = elementColumnTitle;

		this.serverColumnTitle = serverColumnTitle;

		this.run = run + 1;
	}

	@Override
	protected void doHandleEvent(ElementTransferEvent event) {
		double multiplier;
		
		switch(unit)
		{
		case DAY:
			multiplier = 1.15741e-8;
			break;
		case HOUR:
			multiplier = 2.7778e-7;
			break;
		case MILLISECOND:
			break;
		case MINUTE:
			multiplier = 1.6666666666667E-5;
			break;
		case SECOND:
			break;
		case WEEK:
			break;
		default:
			break;
		
		}
		
		Double minutes = this.duration * 1.6666666666667E-5;

		String run = String.valueOf(this.run);

		String element = event.getElement().getLogID();

		String server = this.component.getLogID();

		String duration = String.valueOf(minutes);

		Map<String, String> mapRowValues = new HashMap<>();

		mapRowValues.put("Run", run);
		mapRowValues.put(this.elementColumnTitle, element);
		mapRowValues.put(this.serverColumnTitle, server);
		mapRowValues.put("Duration", duration);

		this.listener.insert(mapRowValues);
	}
}
