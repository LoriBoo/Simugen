package simugen.core.defaults;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import simugen.core.abstracts.AbstractComponentOutputListener;
import simugen.core.components.interfaces.Component;
import simugen.core.data.interfaces.EventContextHandler;
import simugen.core.interfaces.Event;
import simugen.core.sql.ColumnType;
import simugen.core.sql.DefaultTableBuilder;

public class DefaultElementDurationListener extends AbstractComponentOutputListener {

//	private Component component;
//
//	private Map<Element, Long> mapElementToBeginTime = new HashMap<>();
//
//	private Map<Element, Long> mapElementToEndTime = new HashMap<>();

	private DefaultTableBuilder builder;

	private List<EventContextHandler<ElementTransferEvent>> listEventContextHandlers = new ArrayList<>();;

	public DefaultElementDurationListener(Component component, String dataFile) {
		super(component, dataFile);
	}

	@Override
	public Class<? extends Event> getEventType() {
		return ElementTransferEvent.class;
	}

	@Override
	public void listen(Event event) {
		ElementTransferEvent ev = (ElementTransferEvent) event;

		for (EventContextHandler<ElementTransferEvent> handler : listEventContextHandlers) {
			handler.handleEvent(ev);
		}
	}

	public void setTableName(String tableName) {
		builder = new DefaultTableBuilder(tableName);
	}

	public void addColumn(String column, ColumnType type) {
		builder.addColumn(column, type);
	}

	@Override
	public void createNewTable() {
		connect();
		builder.buildTable(connection);
		disconnect();
	}

	public void insert(Map<String, String> mapColumnValues) {
		connect();
		builder.insert(connection, mapColumnValues);
		disconnect();
	}

	public void addEventContextHandler(EventContextHandler<ElementTransferEvent> handler) {
		this.listEventContextHandlers.add(handler);
	}
}
