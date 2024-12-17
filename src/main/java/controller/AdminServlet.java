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
}
