package simugen.core.test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 *
 * @author sqlitetutorial.net
 */
public class TestDB {

	/**
	 * Connect to a sample database
	 *
	 * @param fileName the database file name
	 */
	public static void createNewDatabase(String fileName) {

		String url = "jdbc:sqlite:C:/Output/db/" + fileName;

		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("The driver name is " + meta.getDriverName());
				System.out.println("A new database has been created.");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Connect to the test.db database
	 *
	 * @return the Connection object
	 */
	private Connection connect() {
		// SQLite connection string
		String url = "jdbc:sqlite:C:/Output/db/test.db";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	/**
	 * Insert a new row into the warehouses table
	 *
	 * @param name
	 * @param capacity
	 */
	public void insert() {
		String sql = "INSERT INTO testTable (testTime, testDate, testTimestamp) VALUES(?,?,?)";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setTime(1, new Time(Calendar.getInstance().getTimeInMillis()));
			pstmt.setDate(2, new Date(Calendar.getInstance().getTimeInMillis()));
			pstmt.setTimestamp(3, new Timestamp(Calendar.getInstance().getTimeInMillis()));
			System.out.println(pstmt.toString());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void createNewTable() {
		// SQLite connection string
		String url = "jdbc:sqlite:C:/Output/db/test.db";

		// SQL statement for creating a new table
		String sql = "CREATE TABLE IF NOT EXISTS testTable (\n" + "	testTime TIME(3),\n" + "	testDate DATE,\n"
				+ "	testTimestamp TIMESTAMP\n" + ");";
		
		try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
			// create a new table
			stmt.execute(sql);
			System.out.println("Table has been created");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * select all rows in the warehouses table
	 */
	public void selectAll() {
		String sql = "SELECT testTime, testDate, testTimestamp FROM testTable";

		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			ResultSetMetaData rsmd = rs.getMetaData();
			
			
			System.out.println(rsmd.getColumnTypeName(1));
			System.out.println(rsmd.getColumnType(1));
			System.out.println(rsmd.getColumnType(2));
			System.out.println(rsmd.getColumnType(3));
			
			// loop through the result set
			while (rs.next()) {
				System.out.println(rs.getTime("testTime") + "\t" + rs.getDate("testDate") + "\t"
						+ rs.getTimestamp("testTimestamp"));
			}

			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		TestDB test = new TestDB();
		test.createNewTable();
		test.insert();
		test.selectAll();
	}
}
