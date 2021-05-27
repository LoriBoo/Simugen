package simugen.core.abstracts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import simugen.core.components.interfaces.Component;
import simugen.core.data.interfaces.EventListener;

public abstract class AbstractComponentOutputListener implements EventListener {

	protected Component component;

	protected String dataFile;

	protected String location;

	protected Connection connection;

	public AbstractComponentOutputListener(Component component, String dataFile) {
		this.component = component;

		this.dataFile = dataFile;
	}

	public void setOutputLocation(String location) {
		this.location = location;
	}

	protected void connect() {

		String url = "jdbc:sqlite:" + location + dataFile;

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

	abstract public void createNewTable();
}
