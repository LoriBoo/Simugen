package simugen.core.defaults;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import simugen.core.components.interfaces.Component;
import simugen.core.data.interfaces.EventListener;
import simugen.core.enums.SimTimeUnit;
import simugen.core.interfaces.Element;
import simugen.core.sql.ColumnType;
import simugen.core.sql.DefaultTableBuilder;
import simugen.core.sql.SqlUtils;

public class DefaultElementDurationListener implements EventListener<ElementTransferEvent> {
	private Component component;

	private String pathToDB;

	private SimTimeUnit unit;

	private String elementColumn;

	private String componentColumn;

	private int run;

	private long epoch;

	private static final String RUN = "'Run'";

	private static final String DURATION = "'Duration'";

	private static final String TIME_IN = "'Time In'";

	private static final String TIME_OUT = "'Time Out'";

	protected Map<Element, Long> mapBeginTimes = new HashMap<>();

	protected Map<Element, Long> mapEndTimes = new HashMap<>();

	protected Map<Element, Long> mapDurations = new HashMap<>();

	private DefaultTableBuilder builder;

	private Connection connection;

	public DefaultElementDurationListener(Component component, String pathToDB, SimTimeUnit unit, String tableName,
			String elementColumn, String componentColumn, int run, long epoch) {
		this.component = component;
		this.pathToDB = pathToDB;
		this.unit = unit;
		this.elementColumn = elementColumn;
		this.componentColumn = componentColumn;
		this.run = run;
		this.epoch = epoch;
		
		builder = new DefaultTableBuilder(tableName);

		builder.addColumn(RUN, ColumnType.INT);
		builder.addColumn(componentColumn, ColumnType.STRING);
		builder.addColumn(elementColumn, ColumnType.STRING);
		builder.addColumn(TIME_IN, ColumnType.TIME);
		builder.addColumn(TIME_OUT, ColumnType.TIME);
		builder.addColumn(DURATION, ColumnType.DOUBLE);

		connect();
		builder.buildTable(connection);
		disconnect();
	}

	@Override
	public Class<ElementTransferEvent> getEventType() {
		return ElementTransferEvent.class;
	}

	@Override
	public void listen(ElementTransferEvent event) {
		Element e = event.getElement();

		if (event.getToID().equals(component)) {
			mapBeginTimes.put(e, event.getTime() + epoch);
		} else if (event.getFromID().equals(component)) {
			mapEndTimes.put(e, event.getTime() + epoch);

			mapDurations.put(e, mapEndTimes.get(e) - mapBeginTimes.get(e));

			insert(event);
		}
	}

	private void insert(ElementTransferEvent event) {
		Element e = event.getElement();

		DecimalFormat decimalFormat = new DecimalFormat("#.###");

		double time = Double.valueOf(decimalFormat.format(unit.getConvertMillis(mapDurations.get(e))));

		String element = event.getElement().getLogID();

		Map<String, Object> mapRowValues = new LinkedHashMap<>();

		mapRowValues.put(RUN, this.run);
		mapRowValues.put(this.componentColumn, component.getLogID());
		mapRowValues.put(this.elementColumn, element);
		mapRowValues.put(TIME_IN, SqlUtils.getTime(mapBeginTimes.get(e)));
		mapRowValues.put(TIME_OUT, SqlUtils.getTime(mapEndTimes.get(e)));
		mapRowValues.put(DURATION, time);

		connect();

		builder.insert(connection, mapRowValues);

		disconnect();
	}

	protected void connect() {
		String url = "jdbc:sqlite:" + pathToDB;

		try {
			connection = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	protected void disconnect() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
