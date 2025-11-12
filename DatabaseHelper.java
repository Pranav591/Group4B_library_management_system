package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {
    private static final String URL =
        "jdbc:mysql://localhost:3306/librarydb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connected to MySQL 9.5.0 successfully!");
            return con;
        } catch (ClassNotFoundException e) {
            System.err.println("❌ MySQL Connector/J 9.5.0 driver not found in classpath!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ SQL Connection Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
