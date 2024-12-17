package controller;

import dao.AuthDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import dao.DataBaseConnection;

public class DeleteAgentServlet extends HttpServlet {
    private AuthDao authDao;

    @Override
    public void init() throws ServletException {
        authDao = new AuthDao();
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String agentId = request.getParameter("id");
     String result = authDao.deleteAccount(agentId);
        response.sendRedirect("AdminServlet");
    }
}

