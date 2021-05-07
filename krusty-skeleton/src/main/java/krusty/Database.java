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
	private static final String jdbcUsername = "<CHANGE ME>";
	private static final String jdbcPassword = "<CHANGE ME>";

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
		Statement stmt = conn.createStatement();
		String sql = "SELECT name, address FROM customers";
		ResultSet rs = stmt.executeQuery(sql);
		String json = JSONiszer.toJSON(rs, "customers");

		return json;
	}

	public String getRawMaterials(Request req, Response res) {
		return "{}";
	}

	public String getCookies(Request req, Response res) {
		return "{\"cookies\":[]}";
	}

	public String getRecipes(Request req, Response res) {
		return "{}";
	}

	public String getPallets(Request req, Response res) {
		return "{\"pallets\":[]}";
	}

	public String reset(Request req, Response res) {
		return "{}";
	}

	public String createPallet(Request req, Response res) {
		return "{}";
	}
}
