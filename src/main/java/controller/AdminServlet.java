package controller;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Models.Account;
import dao.DataBaseConnection;

public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Account> agents = new ArrayList<>();
        try (Connection conn = DataBaseConnection.getConnection()) {
            String sql = "SELECT * FROM accounts";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Account agent = new Account(
                		
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("role"),
                    resultSet.getString("email")
                );
                agent.id=resultSet.getInt("id");
                agents.add(agent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("agents", agents);
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
        dispatcher.forward(request, response);
    }
}
