package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import dao.AuthDao;


public class SignUpServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	 String username = request.getParameter("username");
         String password = request.getParameter("password");
         String phone = request.getParameter("phone");
         
         int res=AuthDao.SignUp(username, password, phone);
         if (res > 0) {
             response.getWriter().println("Compte créé avec succès !");
         } else {
             response.getWriter().println("Erreur lors de la création du compte.");
         }
    }
}

