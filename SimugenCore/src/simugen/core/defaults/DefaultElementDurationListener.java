package simugen.core.defaults;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import simugen.core.components.interfaces.Component;
import simugen.core.data.interfaces.EventListener;
import simugen.core.interfaces.Element;
import simugen.core.interfaces.Event;

public class DefaultElementDurationListener implements EventListener {

	private Component component;

	private String dataFile;

	private Map<Element, Long> mapElementToBeginTime = new HashMap<>();

	private Map<Element, Long> mapElementToEndTime = new HashMap<>();

	public DefaultElementDurationListener(Component component, String dataFile) {
		this.component = component;

		this.dataFile = dataFile;
	}

	@Override
	public Class<? extends Event> getEventType() {
		// TODO Auto-generated method stub
		return ElementTransferEvent.class;
	}

	@Override
	public void listen(Event event) {
		ElementTransferEvent ev = (ElementTransferEvent) event;

		// being the "to" means the element has entered the component.
		if (ev.getToID().equals(component)) {
			assert !mapElementToEndTime.containsKey(ev.getElement());

			mapElementToBeginTime.put(ev.getElement(), ev.getTime());
		}

		// being the "from" means the element has left the component.
		if (ev.getFromID().equals(component)) {
			mapElementToEndTime.put(ev.getElement(), ev.getTime());

			long duration = mapElementToEndTime.get(ev.getElement()) - mapElementToBeginTime.get(ev.getElement());

			try {
				PrintStream writeToCSV = new PrintStream(new FileOutputStream("C:\\Output\\" + dataFile, true));

				writeToCSV.println(ev.getModelSeed() + "," + ev.getElement().getLogID() + "," + duration);

				writeToCSV.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
