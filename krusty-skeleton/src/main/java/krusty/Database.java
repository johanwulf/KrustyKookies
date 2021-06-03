package krusty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class Database {
	/**
	 * Modify it to fit your environment and then use this string when connecting to your database!
	 */
	private static final String jdbcString = "jdbc:mysql://127.0.0.1:3306/krusty?serverTimezone=Europe/Stockholm";

	// For use with MySQL or PostgreSQL
	private static final String jdbcUsername = "root";
	private static final String jdbcPassword = "5141";
	private static final String Customers = "INSERT INTO Customers(customer_name, address) VALUES" +
			"    ('Finkakor AB', 'Helsingborg')," +
			"    ('Småbröd AB', 'Malmö')," +
			"    ('Kaffebröd AB', 'Landskrona')," +
			"    ('Bjudkakor AB', 'Ystad')," +
			"    ('Kalaskakor AB', 'Trelleborg')," +
			"    ('Partykakor AB', 'Kristianstad')," +
			"    ('Gästkakor AB', 'Hässleholm')," +
			"    ('Skånekakor AB', 'Perstorp')" +
			";";
	private static final String Cookie = "INSERT INTO Cookie(cookie_name) VALUES" +
			"    ('Amneris')," +
			"    ('Berliner')," +
			"    ('Nut cookie')," +
			"    ('Nut ring')," +
			"    ('Tango')," +
			"    ('Almond delight')" +
			";";
	private static final String Ingredient = "INSERT INTO Ingredient(ingredient_name, stock, unit) VALUES" +
			"    ('Bread crumbs', 500000, 'g')," +
			"    ('Butter', 500000, 'g')," +
			"    ('Chocolate', 500000, 'g')," +
			"    ('Chopped almonds', 500000, 'g')," +
			"    ('Cinnamon', 500000, 'g')," +
			"    ('Egg whites', 500000, 'ml')," +
			"    ('Eggs', 500000, 'g')," +
			"    ('Fine-ground nuts', 500000, 'g')," +
			"    ('Flour', 500000, 'g')," +
			"    ('Ground, roasted nuts', 500000, 'g')," +
			"    ('Icing sugar', 500000, 'g')," +
			"    ('Marzipan', 500000, 'g')," +
			"    ('Potato starch', 500000, 'g')," +
			"    ('Roasted, chopped nuts', 500000, 'g')," +
			"    ('Sodium bicarbonate', 500000, 'g')," +
			"    ('Sugar', 500000, 'g')," +
			"    ('Vanilla sugar', 500000, 'g')," +
			"    ('Vanilla', 500000, 'g')," +
			"    ('Wheat flour', 500000, 'g')" +
			";";

	private static final String Recipes = "INSERT INTO Recipes (cookie_name, ingredient_name, quantity) VALUES" +
			"    ('Almond Delight', 'Butter', 400),('Almond Delight', 'Chopped Almonds', 279),('Almond Delight', 'Cinnamon', 10),('Almond Delight', 'Flour', 400),('Almond Delight', 'Sugar', 270)," +
			"    ('Amneris', 'Butter', 250),('Amneris', 'Eggs', 250),('Amneris', 'Marzipan', 750),('Amneris', 'Potato starch', 25),('Amneris', 'Wheat flour', 25)," +
			"    ('Berliner', 'Butter', 250),('Berliner', 'Chocolate', 50),('Berliner', 'Eggs', 50),('Berliner', 'Flour', 350),('Berliner', 'Icing sugar', 100),('Berliner', 'Vanilla sugar', 5)," +
			"    ('Nut Cookie', 'Bread crumbs', 125),('Nut Cookie', 'Chocolate', 50),('Nut Cookie', 'Egg whites', 350),('Nut Cookie', 'Fine-ground nuts', 750),('Nut Cookie', 'Ground, roasted nuts', 625),('Nut Cookie', 'Sugar', 375)," +
			"    ('Nut Ring', 'Butter', 450),('Nut Ring', 'Flour', 450),('Nut Ring', 'Icing sugar', 190),('Nut Ring', 'Roasted, chopped nuts', 225)," +
			"    ('Tango', 'Butter', 200),('Tango', 'Flour', 300),('Tango', 'Sodium bicarbonate', 4),('Tango', 'Sugar', 250),('Tango', 'Vanilla', 2)" +
			";";

	private Connection conn;

	public void connect() {
		try {
			conn = DriverManager.getConnection(jdbcString, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {

		}
	}

	// TODO: Implement and change output in all methods below!

	public String getCustomers(Request req, Response res) {
		String sql = "SELECT customer_name AS name, address FROM customers";
		String title = "customers";

		return getJson(sql, title);
	}

	public String getRawMaterials(Request req, Response res) {
		String sql = "SELECT ingredient_name AS name, stock AS amount, unit FROM ingredient";
		String title = "raw-materials";

		return getJson(sql, title);
	}

	public String getCookies(Request req, Response res) {
		String sql = "SELECT cookie_name AS name FROM cookie";
		String title = "cookies";

		return getJson(sql, title);
	}

	public String getRecipes(Request req, Response res) {
		String sql = "SELECT cookie_name, Recipes.ingredient_name, quantity, unit FROM Recipes, Ingredient WHERE Recipes.ingredient_name = Ingredient.ingredient_name";
		String title = "recipes";

		return getJson(sql, title);
	}

	public String getPallets(Request req, Response res) throws SQLException {
		String sql = "SELECT id, cookie_name AS cookie, production_date, customer_name AS customer, blocked FROM pallet INNER JOIN orders ON orders.order_id = pallet.order_id"; // TO DO: Fix sql statement
		String title = "pallets";
		StringBuilder sb = new StringBuilder();

		sb.append(sql);

		List<String> paramList = Arrays.asList("from", "to", "cookie", "blocked");
		HashMap<String, String> map = new HashMap<String, String>();

		for (String param : paramList) {
			if (req.queryParams(param) != null) {
				map.put(param, req.queryParams(param));
			}
		}

		if (map.size() > 0) {
			sb.append(" WHERE");
		}

		int size = 1;

		for (Map.Entry<String, String> entry : map.entrySet()) {
			switch (entry.getKey()) {
				case "from":
					sb.append(" production_date >= ?");
					break;
				case "to":
					sb.append(" production_date <= ?");
					break;
				case "blocked":
					sb.append(" blocked = ?");
					break;
				case "cookie":
					sb.append(" cookie_name = ?");
					break;
				default:
					break;
			}
			if (map.size() > size) {
				size++;
				sb.append(" AND");
			}
		}

		PreparedStatement stmt = conn.prepareStatement(sb.toString());

		int i = 1;

		for (Map.Entry<String, String> entry : map.entrySet()) {
			switch (entry.getKey()) {
				case "from":
					stmt.setDate(i, Date.valueOf(req.queryParams("from")));
					break;
				case "to":
					stmt.setDate(i, Date.valueOf(req.queryParams("to")));
					break;
				case "blocked":
					stmt.setString(i, req.queryParams("blocked"));
					break;
				case "cookie":
					stmt.setString(i, req.queryParams("cookie"));
					break;
				default:
					break;
			}
			i++;
		}

		ResultSet rs = stmt.executeQuery();

		String result = Jsonizer.toJson(rs, title);
		System.out.println(result);

		return result;
	}

	public String reset(Request req, Response res) {
		String[] tables = {"cookie", "customers", "ingredient", "orders", "orderspec", "pallet", "recipes"};
		List values = Arrays.asList("cookie", "customers", "ingredient", "recipes");

		for (String table : tables) {
			try {
				Statement stmt = conn.createStatement();
				stmt.execute("SET FOREIGN_KEY_CHECKS=0");
				String sql = "TRUNCATE TABLE " + table;
				stmt.execute(sql);

				if (values.contains(table)) {
					stmt.executeUpdate(getValueQuery(table));
				}

				stmt.execute("SET FOREIGN_KEY_CHECKS=1");
			} catch (SQLException e){
				e.printStackTrace();
			}
		}

		return "{\n\t\"status\": \"ok\"\n}";
	}

	private String getValueQuery(String table) {
		switch (table) {
			case "cookie":
				return Cookie;
			case "customers":
				return Customers;
			case "ingredient":
				return Ingredient;
			case "recipes":
				return Recipes;
			default:
				return "";
		}
	}

	public String createPallet(Request req, Response res) throws SQLException {
		String cookie = req.queryParams("cookie");

		if (cookie == null) {
			return "{\n\t\"status\": \"error\"\n}";
		} else if (!checkCookie(cookie)) {
			return "{\n\t\"status\": \"unknown cookie\"\n}";
		}

		String sql = "UPDATE ingredient SET stock = stock - ? WHERE ingredient_name = ?";
		HashMap<String, Integer> map = cookieRecipe(cookie);

		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, entry.getValue()*54);
			stmt.setString(2, entry.getKey());
			stmt.executeUpdate();
		}

		sql = "INSERT INTO orders (order_date, delivery_date, customer_name) VALUES (NOW(), NOW(), \"Finkakor AB\")";
		Statement stmt = conn.createStatement();

		stmt.executeUpdate(sql, stmt.RETURN_GENERATED_KEYS);
		ResultSet rs = stmt.getGeneratedKeys();

		int createdId = 0;
		if (rs.next()) {
			createdId = rs.getInt(1);
		}

		sql = "INSERT INTO pallet (production_date, delivery_date, blocked, cookie_name, order_id) VALUES (NOW(), NOW(), \"no\", \"" + cookie + "\"," + createdId + ")";

		stmt.executeUpdate(sql);

		return "{\n\t\"status\": \"ok\" ,\n \n\t\"id\":" +  createdId + "}";
	}

	private HashMap<String, Integer> cookieRecipe(String cookie) throws SQLException {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		String sql = "SELECT cookie_name, ingredient_name, quantity FROM recipes WHERE cookie_name=\"" + cookie + "\"";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		while(rs.next()) {
			map.put(rs.getString(2), (Integer) rs.getInt(3));
		}

		return map;
	}

	private boolean checkCookie(String cookie) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM cookie WHERE cookie_name=?");
		stmt.setString(1, cookie);
		ResultSet rs = stmt.executeQuery();

		return rs.next();
	}

	private String getJson(String sql, String title) {
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			return Jsonizer.toJson(rs, title);
		} catch (SQLException e) {
			return "";
		}
	}
}
