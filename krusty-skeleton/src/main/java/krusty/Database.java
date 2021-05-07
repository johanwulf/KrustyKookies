package krusty;

import spark.Request;
import spark.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class Database {
	/**
	 * Modify it to fit your environment and then use this string when connecting to your database!
	 */
	private static final String jdbcString = "jdbc:mysql://127.0.0.1:3306/krusty?serverTimezone=Europe/Stockholm";

	// For use with MySQL or PostgreSQL
	private static final String jdbcUsername = "root";
	private static final String jdbcPassword = "5141";

	private Connection conn;

	public void connect() {

		try {
			conn = DriverManager.getConnection(jdbcString, jdbcUsername, jdbcPassword);
			System.out.println("success");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	// TODO: Implement and change output in all methods below!

	public String getCustomers(Request req, Response res) {
		String sql = "SELECT name, address FROM customers";
		String title = "customers";

		return getJson(sql, title);
	}

	public String getRawMaterials(Request req, Response res) {
		String sql = "SELECT ingredient_name, stock, unit FROM ingredient"; // TO DO: Fix sql statement
		String title = "raw-materials";

		return getJson(sql, title);
	}

	public String getCookies(Request req, Response res) {
		String sql = "SELECT cookie_name FROM cookie"; // TO DO: Fix sql statement
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
		String path = "src/main/java/krusty/reset.sql";

		try {
			String sql = new String(Files.readAllBytes(Paths.get(path)));

			try {
				Statement stmt = conn.createStatement();
				stmt.executeQuery(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}

	public String createPallet(Request req, Response res) {
		return "{}";
	}

	private String getJson(String sql, String title) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			return Jsonizer.toJson(rs, title);
		} catch (SQLException e) {
			return "";
		}



	}
}