package controller;

import Models.Reservation;
import dao.HotelDAO;
import dao.ReservationDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import Models.Hotel;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
        maxFileSize = 1024 * 1024 * 10,       // 10MB
        maxRequestSize = 1024 * 1024 * 50     // 50MB
)
public class AgentServlet extends HttpServlet {
    private HotelDAO hotelDAO;
    private ReservationDAO reservationDAO;

    @Override
    public void init() throws ServletException {
        hotelDAO = new HotelDAO();
        reservationDAO = new ReservationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filterName = request.getParameter("name");
        String filterCity = request.getParameter("city");
        String filterStars = request.getParameter("stars");

        try {
            List<Hotel> hotels = hotelDAO.getHotels(filterName, filterCity, filterStars);
            request.setAttribute("hotel", hotels);
            List<Reservation> reservations  = reservationDAO.getAllReservations();
            request.setAttribute("reservations", reservations);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("agent.jsp");
        dispatcher.forward(request, response);
    }

    private static final String UPLOAD_DIRECTORY = "uploads";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("addHotel".equals(action)) {
                String name = request.getParameter("name");
                String city = request.getParameter("city");
                int stars = Integer.parseInt(request.getParameter("stars"));
                String description = request.getParameter("description");

                // Handle image upload
                Part filePart = request.getPart("image");
                String fileName = extractFileName(filePart);
                String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;

                // Create directory if it doesn't exist
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();

                // Generate unique filename to prevent overwriting
                String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
                String filePath = uploadPath + File.separator + uniqueFileName;
                filePart.write(filePath);

                // Call DAO method to add hotel
                hotelDAO.addHotel(
                        name,
                        city,
                        stars,
                        description,
                        UPLOAD_DIRECTORY + "/" + uniqueFileName
                );

            } else if ("deleteHotel".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                hotelDAO.deleteHotel(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error processing hotel: " + e.getMessage());
        }

        response.sendRedirect("AgentServlet");
    }

    // Utility method to extract filename
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}