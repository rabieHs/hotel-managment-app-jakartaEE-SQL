package controller;

import dao.HotelDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import Models.Room;
import Models.Hotel;
import dao.RoomDAO;

public class RoomHotelServlet extends HttpServlet {
    private RoomDAO roomDAO = new RoomDAO();
    private HotelDAO hotelDAO = new HotelDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
        int hotelId = Integer.parseInt(request.getParameter("hotelId"));
        String maxPrice = request.getParameter("maxPrice");
        Double maxPriceValue = null;

        // Convert maxPrice to Double if it's provided
        if (maxPrice != null && !maxPrice.isEmpty()) {
            maxPriceValue = Double.parseDouble(maxPrice);
        }

        // Get hotel details using the DAO
        Hotel hotel = hotelDAO.getHotelById(hotelId);

        // Set hotel attributes for JSP
        if (hotel != null) {

            request.setAttribute("hotel", hotel);

        }

        // Get rooms for the hotel using the DAO, filtered by max price if applicable
        List<Room> rooms = null;

            rooms = roomDAO.getRoomsByHotelId(hotelId);


        // Set rooms and hotelId as attributes for the JSP page
        request.setAttribute("rooms", rooms);
        request.setAttribute("hotelId", hotelId);

        // Forward to JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("roomhotel.jsp");
        dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // POST logic can be added here if needed
    }
}
