package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import dao.AuthDao;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	 String username = request.getParameter("username");
         String password = request.getParameter("password");
     	
         String user=AuthDao.Login(username, password);
         
         if(user.equals("admin") ) {
        	 response.sendRedirect("AdminServlet");
         }else if(user.equals("agent") ) {
        	 response.sendRedirect("AgentServlet");
         }else if(user.equals("visiteur") )
        	 response.sendRedirect("VisiteurServlet");
         else
        	 response.getWriter().println("user not found");
    }
}