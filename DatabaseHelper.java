package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseHelper - Handles connection logic for MySQL database.
 * -------------------------------------------------------------
 * Key OOP Concepts:
 * - Encapsulation: Connection details are private.
 * - Singleton Pattern: Ensures one shared database connection.
 */
public class DatabaseHelper {

    // --- Database Credentials ---
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/librarydb";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    // --- Singleton Connection Instance ---
    private static Connection connection = null;

    // --- Private Constructor ---
    private DatabaseHelper() {}

    /**
     * Provides a single reusable database connection.
     *
     * @return Connection object for MySQL database
     */
    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Database connected successfully!");
            } catch (ClassNotFoundException e) {
                System.err.println("❌ MySQL JDBC Driver not found.");
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("❌ Failed to connect to database.");
                e.printStackTrace();
            }
        }
        return connection;
    }

    /**
     * Closes the connection safely when the app shuts down.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("🔒 Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connection = null;
        }
    }
}
