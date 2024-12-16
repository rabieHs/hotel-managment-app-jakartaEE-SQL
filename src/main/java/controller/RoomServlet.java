package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Models.Room;
import dao.RoomDAO;

public class RoomServlet extends HttpServlet {
    private RoomDAO roomDAO;

    @Override
    public void init() throws ServletException {
        roomDAO = new RoomDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int hotelId = Integer.parseInt(request.getParameter("hotelId"));
        List<Room> rooms = new ArrayList<>();
        String hotelName = null;

        try {
            // Get hotel name
            hotelName = roomDAO.getHotelName(hotelId);

            // Get rooms for the hotel
            rooms = roomDAO.getRoomsByHotelId(hotelId);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
        }

        request.setAttribute("hotelName", hotelName);
        request.setAttribute("rooms", rooms);
        request.setAttribute("hotelId", hotelId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("room.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int hotelId = Integer.parseInt(request.getParameter("hotelId"));

        try {
            if ("addRoom".equals(action)) {
                String label = request.getParameter("label");
                int capacity = Integer.parseInt(request.getParameter("capacity"));
                double price = Double.parseDouble(request.getParameter("price"));

                roomDAO.addRoom(hotelId, label, capacity, price);
            } else if ("deleteRoom".equals(action)) {
                int roomId = Integer.parseInt(request.getParameter("roomId"));
                roomDAO.deleteRoom(roomId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
        }

        response.sendRedirect("RoomServlet?hotelId=" + hotelId);
    }
}