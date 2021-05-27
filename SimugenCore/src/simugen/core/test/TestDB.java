package simugen.core.test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
		String url = "jdbc:sqlite:C:/Output/db/CoffeeShop.db";
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
	public void insert(String name, double capacity) {
		String sql = "INSERT INTO warehouses(name,capacity) VALUES(?,?)";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, name);
			pstmt.setDouble(2, capacity);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void createNewTable() {
		// SQLite connection string
		String url = "jdbc:sqlite:C:/Output/db/test.db";

		// SQL statement for creating a new table
		String sql = "CREATE TABLE IF NOT EXISTS warehouses (\n" + "	id integer PRIMARY KEY,\n"
				+ "	name text NOT NULL,\n" + "	capacity real\n" + ");";

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
		String sql = "SELECT Run, Customer, Duration FROM QueueData";

		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			// loop through the result set
			while (rs.next()) {
				System.out
						.println(rs.getInt("Run") + "\t" + rs.getString("Customer") + "\t" + rs.getDouble("Duration"));
			}

			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		sql = "SELECT Run, Barista, Customer, Duration FROM ServerData";

		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			// loop through the result set
			while (rs.next()) {
				System.out.println(rs.getInt("Run") + "\t" + rs.getString("Barista") + "\t" + rs.getString("Customer")
						+ "\t" + rs.getDouble("Duration"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		TestDB test = new TestDB();

		test.selectAll();
//
//		String sql = "SELECT AVG(Duration) AS AverageDuration FROM QueueData;";
//
//		try (Connection conn = test.connect();
//				Statement stmt = conn.createStatement();
//				ResultSet rs = stmt.executeQuery(sql)) {
//
//			// loop through the result set
//			while (rs.next()) {
//				System.out.println(rs.getDouble(1));
//			}
//
//			conn.close();
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());
//		}

	}
}
