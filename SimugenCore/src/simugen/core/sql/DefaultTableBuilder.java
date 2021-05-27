package simugen.core.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class DefaultTableBuilder {
	private String tableName;

	private Map<String, ColumnType> mapColumnToType = new HashMap<>();

	public DefaultTableBuilder(String tableName) {
		this.tableName = tableName;
	}

	public void addColumn(String name, ColumnType type) {
		mapColumnToType.put(name, type);
	}

	public void buildTable(Connection conn) {
		String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n";

		for (String name : mapColumnToType.keySet()) {
			sql = sql + name + " " + mapColumnToType.get(name).getType() + ",\n";
		}

		sql = sql.substring(0, sql.length() - 2);
		sql = sql + ");";

		try {
			Statement statement = conn.createStatement();

			statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DefaultTableBuilder builder = new DefaultTableBuilder("warehouses");

		Map<String, String> mapColumnValues = new HashMap<>();

		builder.addColumn("name", ColumnType.STRING);
		builder.addColumn("capacity", ColumnType.INT);
		builder.addColumn("location", ColumnType.STRING);

		mapColumnValues.put("name", "Warehouse 1");
		mapColumnValues.put("capacity", "300");
		mapColumnValues.put("location", "Virginia");

		builder.insert(null, mapColumnValues);
	}

	public void insert(Connection conn, Map<String, String> mapColumnsValues) {
		// String sql = "INSERT INTO warehouses(name,capacity) VALUES(?,?)";

		assert mapColumnsValues.keySet().containsAll(mapColumnToType.keySet());

		String sql = "INSERT INTO " + tableName + "(";

		for (String column : mapColumnsValues.keySet()) {
			sql = sql + column + ",";
		}

		sql = sql.substring(0, sql.length() - 1);
		sql = sql + ") VALUES(";

		for (int i = 0; i < mapColumnsValues.keySet().size(); i++) {
			sql = sql + "?,";
		}

		sql = sql.substring(0, sql.length() - 1);
		sql = sql + ")";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			for (int i = 0; i < mapColumnsValues.keySet().size(); i++) {
				String column = (String) mapColumnsValues.keySet().toArray()[i];

				int j = i + 1;

				switch (mapColumnToType.get(column)) {
				case STRING:
					pstmt.setString(j, mapColumnsValues.get(column));
					break;
				case DOUBLE:
					pstmt.setDouble(j, Double.valueOf(mapColumnsValues.get(column)));
					break;
				case INT:
					pstmt.setInt(j, Integer.valueOf(mapColumnsValues.get(column)));
					break;
				default:
					break;
				}
			}

			// pstmt.setString(1, name);
			// pstmt.setDouble(2, capacity);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
