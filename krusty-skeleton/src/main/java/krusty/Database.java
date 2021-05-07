package krusty;

import spark.Request;
import spark.Response;

import java.sql.DriverManager;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import static krusty.Jsonizer.toJson;

public class Database {
	/**
	 * Modify it to fit your environment and then use this string when connecting to your database!
	 */
	private static final String jdbcString = "jdbc:mysql://localhost/krusty";

	// For use with MySQL or PostgreSQL
	private static final String jdbcUsername = "root";
	private static final String jdbcPassword = "5141";

	private Connection conn;

	public void connect() {
		try {
			conn = DriverManager.getConnection(jdbcString, jdbcUsername, jdbcPassword);
		} catch (SQLException error) {
			System.out.println(error);
		}
	}

	// TODO: Implement and change output in all methods below!

	public String getCustomers(Request req, Response res) {
		String sql = "SELECT name, address FROM customers";
		String title = "customers";

		return getJson(sql, title);
	}

	public String getRawMaterials(Request req, Response res) {
		String sql = ""; // TO DO: Fix sql statement
		String title = "raw-materials";

		return getJson(sql, title);
	}

	public String getCookies(Request req, Response res) {
		String sql = ""; // TO DO: Fix sql statement
		String title = "cookies";

		return getJson(sql, title);
	}

	public String getRecipes(Request req, Response res) {
		String sql = ""; // TO DO: Fix sql statement
		String title = "recipes";

		return getJson(sql, title);
	}

	public String getPallets(Request req, Response res) {
		String sql = ""; // TO DO: Fix sql statement
		String title = "pallets";

		/**
		 * from
		 * to
		 * cookie
		 * blocked
		 * ^
		 * params from request
		 */

		


		return getJson(sql, title);
	}

	public String reset(Request req, Response res) {
		// Resets all tables



		return "{}";
	}

	public String createPallet(Request req, Response res) {
		return "{}";
	}
	
	private String getJson(String sql, String title) {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		return JSONizer.toJSON("rs", title)
	}
}
