package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

	   private static final String URL = "jdbc:mysql://127.0.0.1:3306/hotel?autoReconnect=true&useSSL=false";
	    private static final String USER = "root";
	    private static final String PASSWORD = "";

	    public static Connection getConnection() throws SQLException {
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");  // Charger le driver MySQL
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        return DriverManager.getConnection(URL, USER, PASSWORD);
	    }
}
