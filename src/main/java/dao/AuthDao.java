package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDao {
	public static  int SignUp(String username,String password,String phone) {
		try (Connection connection = DataBaseConnection.getConnection()) {
			String sql = "INSERT INTO accounts (username, password, phone) VALUES (?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);
			statement.setString(3, phone);
			int rowsInserted = statement.executeUpdate();
			return rowsInserted;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static String Login(String username,String password) {

	         try (Connection conn = DataBaseConnection.getConnection()) {
	             String sql = "SELECT * FROM accounts WHERE username = ? AND password = ?";
	             PreparedStatement statement = conn.prepareStatement(sql);
	             statement.setString(1, username);
	             statement.setString(2, password);

	             ResultSet resultSet = statement.executeQuery();

	             if (resultSet.next()) {
	            	 System.out.println(resultSet.getString("role"));
	                 String role = resultSet.getString("role");
		             return role;
	             }

	         } catch (Exception e) {
	             e.printStackTrace();
	             return "-1";
	         }
			return "-1";

	}



}
