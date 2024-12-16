package controller;

import dao.HotelDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Models.Hotel;

public class VisiteurServlet extends HttpServlet {
    private HotelDAO hotelDAO;

    @Override
    public void init() throws ServletException {
        hotelDAO = new HotelDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String city = request.getParameter("city");
        String minStars = request.getParameter("minStars");
        String maxPrice = request.getParameter("maxPrice");

        List<Hotel> hotels = new ArrayList<>();

        try {
            hotels = hotelDAO.filterHotels(city, minStars, maxPrice);

            System.out.println(hotels.size()); // Keeping the original debug print

            request.setAttribute("hotels", hotels);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("visiteur.jsp");
        dispatcher.forward(request, response);
    }
}