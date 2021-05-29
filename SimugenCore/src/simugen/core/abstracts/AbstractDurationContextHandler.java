package simugen.core.abstracts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import simugen.core.components.interfaces.Component;
import simugen.core.data.interfaces.EventContextHandler;
import simugen.core.defaults.ElementTransferEvent;
import simugen.core.enums.SimTimeUnit;
import simugen.core.interfaces.Element;
import simugen.core.sql.ColumnType;
import simugen.core.sql.DefaultTableBuilder;
import simugen.core.sql.SqlUtils;

public abstract class AbstractDurationContextHandler implements EventContextHandler<ElementTransferEvent> {
	protected Component component;

	protected Map<Element, Long> mapBeginTimes = new HashMap<>();

	protected Map<Element, Long> mapEndTimes = new HashMap<>();

	protected Map<Element, Long> mapDurations = new HashMap<>();

	private DefaultTableBuilder builder;

	private String componentColumnTitle;

	private String elementColumnTitle;

	private int run;

	private Connection connection;

	private String pathToDB;

	private SimTimeUnit unit;

	private long epoch = 0L;

	private static final String RUN = "'Run'";

	private static final String DURATION = "'Duration'";

	private static final String TIME_IN = "'Time In'";

	private static final String TIME_OUT = "'Time Out'";

	public AbstractDurationContextHandler(Component component, String pathToDB, SimTimeUnit unit, String tableName,
			String elementColumn, String componentColumn, int run, long epoch) {
		this.component = component;

		this.componentColumnTitle = componentColumn;

		this.elementColumnTitle = elementColumn;

		this.run = run;

		this.unit = unit;

		this.pathToDB = pathToDB;

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
	public void handleEvent(ElementTransferEvent event) {
		Element e = event.getElement();

		if (event.getToID().equals(component)) {
			mapBeginTimes.put(e, event.getTime() + epoch);
		} else if (event.getFromID().equals(component)) {
			mapEndTimes.put(e, event.getTime() + epoch);

			mapDurations.put(e, mapEndTimes.get(e) - mapBeginTimes.get(e));

			insert(event);

			doHandleEvent(event);
		}
	}

	private void insert(ElementTransferEvent event) {
		Element e = event.getElement();

		DecimalFormat decimalFormat = new DecimalFormat("#.###");

		double time = Double.valueOf(decimalFormat.format(unit.getConvertMillis(mapDurations.get(e))));

		String element = event.getElement().getLogID();

		Map<String, Object> mapRowValues = new LinkedHashMap<>();

		mapRowValues.put(RUN, this.run);
		mapRowValues.put(this.componentColumnTitle, component.getLogID());
		mapRowValues.put(this.elementColumnTitle, element);
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

	protected abstract void doHandleEvent(ElementTransferEvent event);
}
