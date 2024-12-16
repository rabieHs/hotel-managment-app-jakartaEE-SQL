package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import dao.DataBaseConnection;
public class CreateAgentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String email = request.getParameter("email");

        try (Connection conn = DataBaseConnection.getConnection()) {
            String sql = "INSERT INTO accounts (username, password, role, email) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, role);
            statement.setString(4, email);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Redirection vers la page admin après l'ajout
        response.sendRedirect("AdminServlet");
    }
}
