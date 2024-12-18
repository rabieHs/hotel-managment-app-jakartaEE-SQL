package dao;

import Models.Reservation;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    public void addReservation(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO reservations " +
                "(room_id, hotel_id, user_name, user_phone, start_date, end_date, total_price) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, reservation.getRoomId());
            pstmt.setInt(2, reservation.getHotelId());
            pstmt.setString(3, reservation.getUserName());
            pstmt.setString(4, reservation.getUserPhone());
            pstmt.setDate(5, Date.valueOf(reservation.getStartDate()));
            pstmt.setDate(6, Date.valueOf(reservation.getEndDate()));
            pstmt.setDouble(7, reservation.getTotalPrice());

            pstmt.executeUpdate();
        }
    }

    public List<Reservation> getAllReservations() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT r.*, h.name AS hotel_name, rm.label AS room_label " +
                "FROM reservations r " +
                "JOIN hotels h ON r.hotel_id = h.id " +
                "JOIN rooms rm ON r.room_id = rm.id " +
                "ORDER BY r.start_date";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Reservation reservation = new Reservation(
                        rs.getInt("id"),
                        rs.getInt("room_id"),
                        rs.getInt("hotel_id"),
                        rs.getString("user_name"),
                        rs.getString("user_phone"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate(),
                        rs.getDouble("total_price")
                );

                reservation.setHotelName(rs.getString("hotel_name"));
                reservation.setRoomLabel(rs.getString("room_label"));

                reservations.add(reservation);
            }
        }

        return reservations;
    }

    // Get reservations by hotel ID
    public List<Reservation> getReservationsByHotelId(int hotelId) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT r.*, h.name AS hotel_name, rm.label AS room_label " +
                "FROM reservations r " +
                "JOIN hotels h ON r.hotel_id = h.id " +
                "JOIN rooms rm ON r.room_id = rm.id " +
                "WHERE r.hotel_id = ? " +
                "ORDER BY r.start_date";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, hotelId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Reservation reservation = new Reservation(
                            rs.getInt("id"),
                            rs.getInt("room_id"),
                            rs.getInt("hotel_id"),
                            rs.getString("user_name"),
                            rs.getString("user_phone"),
                            rs.getDate("start_date").toLocalDate(),
                            rs.getDate("end_date").toLocalDate(),
                            rs.getDouble("total_price")
                    );

                    // Additional details from joined tables
                    reservation.setHotelName(rs.getString("hotel_name"));
                    reservation.setRoomLabel(rs.getString("room_label"));

                    reservations.add(reservation);
                }
            }
        }

        return reservations;
    }

    public boolean isRoomAvailable(int roomId, LocalDate startDate, LocalDate endDate) throws SQLException {
        String sql = "SELECT COUNT(*) AS conflict_count " +
                "FROM reservations " +
                "WHERE room_id = ? AND " +
                "((start_date <= ? AND end_date >= ?) OR " +
                "(start_date <= ? AND end_date >= ?) OR " +
                "(start_date >= ? AND end_date <= ?))";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, roomId);
            pstmt.setDate(2, Date.valueOf(startDate));
            pstmt.setDate(3, Date.valueOf(startDate));
            pstmt.setDate(4, Date.valueOf(endDate));
            pstmt.setDate(5, Date.valueOf(endDate));
            pstmt.setDate(6, Date.valueOf(startDate));
            pstmt.setDate(7, Date.valueOf(endDate));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("conflict_count") == 0;
                }
            }
        }

        return false;
    }
}