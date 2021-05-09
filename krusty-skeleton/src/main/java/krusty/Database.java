package krusty;

import spark.Request;
import spark.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

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
			"    ('Almond Delight')," +
			"    ('Amneris')," +
			"    ('Berliner')," +
			"    ('Nut Cookie')," +
			"    ('Nut Ring')," +
			"    ('Tango')" +
			";";
	private static final String Ingredient = "INSERT INTO Ingredient(ingredient_name, stock, unit) VALUES" +
			"    ('Bread crumbs', 500000, 'g')," +
			"    ('Butter', 500000, 'g')," +
			"    ('Chocolate', 500000, 'g')," +
			"    ('Chopped Almonds', 500000, 'g')," +
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
		System.out.println("connected");
		try {
			conn = DriverManager.getConnection(jdbcString, jdbcUsername, jdbcPassword);
			System.out.println("success");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	// TODO: Implement and change output in all methods below!

	public String getCustomers(Request req, Response res) {
		String sql = "SELECT customer_name, address FROM customers";
		String title = "customers";

		return getJson(sql, title);
	}

	public String getRawMaterials(Request req, Response res) {
		String sql = "SELECT ingredient_name, stock, unit FROM ingredient";
		String title = "raw-materials";

		return getJson(sql, title);
	}

	public String getCookies(Request req, Response res) {
		String sql = "SELECT cookie_name FROM cookie";
		String title = "cookies";

		return getJson(sql, title);
	}

	public String getRecipes(Request req, Response res) {
		String sql = "SELECT cookie_name, Recipes.ingredient_name, quantity, unit FROM Recipes, Ingredient WHERE Recipes.ingredient_name = Ingredient.ingredient_name";
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

		return "{}";
		//return getJson(sql, title);
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

	public String createPallet(Request req, Response res) {
		String cookie = req.queryParams("cookie");
		String sql = "SELECT * FROM cookie WHERE cookie_name = ?;";

		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, cookie);


		} catch (SQLException e) {

		}





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