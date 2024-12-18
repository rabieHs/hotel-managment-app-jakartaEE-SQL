package controller;

import dao.ReservationDAO;
import Models.Reservation;
import dao.RoomDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.Gson;

public class ReservationServlet extends HttpServlet {
    private ReservationDAO reservationDAO;
    private RoomDAO roomDAO;

    @Override
    public void init() throws ServletException {
        reservationDAO = new ReservationDAO();
        roomDAO = new RoomDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject jsonResponse = new JsonObject();
        Gson gson = new Gson();

        try {
            // Validate and parse inputs
            String roomIdParam = request.getParameter("roomId");
            String hotelIdParam = request.getParameter("hotelId");
            String userName = request.getParameter("userName");
            String userPhone = request.getParameter("userPhone");
            String startDateParam = request.getParameter("startDate");
            String endDateParam = request.getParameter("endDate");

            if (roomIdParam == null || hotelIdParam == null || userName == null || userPhone == null ||
                    startDateParam == null || endDateParam == null) {
                throw new IllegalArgumentException("Tous les champs sont obligatoires.");
            }

            int roomId = Integer.parseInt(roomIdParam);
            int hotelId = Integer.parseInt(hotelIdParam);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(startDateParam, formatter);
            LocalDate endDate = LocalDate.parse(endDateParam, formatter);

            // Validate dates
            if (endDate.isBefore(startDate) || startDate.isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Les dates fournies sont invalides.");
            }

            // Get room price
            double roomPrice = getRoomPrice(roomId);
            long nights = startDate.until(endDate).getDays();
            double totalPrice = roomPrice * nights;

            // Check room availability
            if (!reservationDAO.isRoomAvailable(roomId, startDate, endDate)) {
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "La chambre n'est pas disponible pour les dates sélectionnées.");
                response.getWriter().write(gson.toJson(jsonResponse));
                return;
            }

            // Create and save reservation
            Reservation reservation = new Reservation(
                    0, // ID will be auto-generated
                    roomId,
                    hotelId,
                    userName,
                    userPhone,
                    startDate,
                    endDate,
                    totalPrice
            );
            reservationDAO.addReservation(reservation);

            // Success response
            jsonResponse.addProperty("success", true);
            jsonResponse.addProperty("message", "Réservation effectuée avec succès.");
        } catch (NumberFormatException e) {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Données numériques invalides : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", e.getMessage());
        } catch (SQLException e) {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Erreur de base de données: " + e.getMessage());
        } catch (Exception e) {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Une erreur est survenue: " + e.getMessage());
        }

        // Send JSON response
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(jsonResponse));
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Get reservations (potentially filtered)
            String hotelIdParam = request.getParameter("hotelId");

            List<Reservation> reservations;
            if (hotelIdParam != null && !hotelIdParam.isEmpty()) {
                int hotelId = Integer.parseInt(hotelIdParam);
                reservations = reservationDAO.getReservationsByHotelId(hotelId);
            } else {
                reservations = reservationDAO.getAllReservations();
            }

            // Set reservations as attribute for JSP
            request.setAttribute("reservations", reservations);

            // Forward to reservations page
            RequestDispatcher dispatcher = request.getRequestDispatcher("agent.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error fetching reservations", e);
        }
    }

    // Helper method to get room price (you'll need to implement this in RoomDAO)
    private double getRoomPrice(int roomId) throws SQLException {
        return roomDAO.getRoomPrice(roomId);
    }
}