package simugen.gui.views.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import simugen.core.sql.SqlUtils;

public class SqlTableFiller {
	private Table table;

	private Connection connection;

	public SqlTableFiller(Table table) {
		this.table = table;
	}

	public void fill(String url, String sql) throws SQLException, ClassNotFoundException {

		while (table.getColumnCount() > 0) {
			table.getColumns()[0].dispose();
		}

		Class.forName("org.sqlite.JDBC");

		connection = DriverManager.getConnection(url);

		Statement stmt = connection.createStatement();

		stmt.execute(sql);

		ResultSet set = stmt.getResultSet();

		ResultSetMetaData rsmd = set.getMetaData();

		int columnCount = rsmd.getColumnCount();

		// table.clearAll();

		for (TableItem tableItem : table.getItems()) {
			tableItem.dispose();
		}

		for (int i = 1; i <= columnCount; i++) {
			String name = rsmd.getColumnName(i);

			TableColumn col = new TableColumn(table, SWT.CENTER);

			col.setText(name);

			col.setWidth(100);

			table.setHeaderVisible(true);
		}

		while (set.next()) {
			String[] values = new String[columnCount];

			for (int i = 1; i <= columnCount; i++) {
				values[i - 1] = SqlUtils.getString(rsmd, set, i);
			}

			TableItem tableItem = new TableItem(table, SWT.NONE);

			tableItem.setText(values);
		}

		connection.close();
	}
}
