package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import dao.DataBaseConnection;

public class DeleteAgentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String agentId = request.getParameter("id"); // Récupère l'id de l'agent depuis la requête
        	
        try (Connection conn = DataBaseConnection.getConnection()) {
            String sql = "DELETE FROM accounts WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, agentId);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Redirection vers la page admin après suppression
        response.sendRedirect("AdminServlet");
    }
}

