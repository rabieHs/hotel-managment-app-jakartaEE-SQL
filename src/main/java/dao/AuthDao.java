package dao;

import Models.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

	public static List<Account> getAccounts(){

		List<Account> accounts = new ArrayList<>();
		try(Connection con = DataBaseConnection.getConnection()){
			String query = "Select * From accounts";
			PreparedStatement statement = con.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			while(resultSet.next()){
				Account account = new Account(

						resultSet.getString("username"),
						resultSet.getString("password"),
						resultSet.getString("role"),
						resultSet.getString("email")
				);
				account.id=resultSet.getInt("id");
				accounts.add(account);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return accounts;
	}

	public static String deleteAccount(String id){
		try (Connection conn = DataBaseConnection.getConnection()) {
			String sql = "DELETE FROM accounts WHERE id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, id);
			statement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			return "-1";

		}
		return "1";
	}

}
