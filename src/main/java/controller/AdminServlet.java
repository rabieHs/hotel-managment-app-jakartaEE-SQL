package controller;
import dao.AuthDao;
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
    private AuthDao authDao ;

    @Override
    public void init() throws ServletException {
        authDao = new AuthDao();
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Account> agents = authDao.getAccounts();
        request.setAttribute("agents", agents);
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
        dispatcher.forward(request, response);
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String email = request.getParameter("email");

        authDao.createAccount(username,password,email,role);

        // Redirection vers la page admin après l'ajout
        response.sendRedirect("AdminServlet");
    }

 /*   @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
        String agentId = req.getParameter("id");
        String result = authDao.deleteAccount(agentId);
        resp.sendRedirect("AdminServlet");
    }*/
}
