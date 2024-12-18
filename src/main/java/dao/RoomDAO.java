package dao;

import Models.Room;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    public double getRoomPrice(int roomId) throws SQLException {
        try (Connection conn = DataBaseConnection.getConnection()) {
            String sql = "SELECT price FROM rooms WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, roomId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getDouble("price");
                    }
                }
            }
        }
        throw new SQLException("Room not found");
    }



    // Get rooms by hotel ID
    public List<Room> getRoomsByHotelId(int hotelId) throws SQLException {
        List<Room> rooms = new ArrayList<>();
        try (Connection conn = DataBaseConnection.getConnection()) {
            String roomQuery = "SELECT * FROM rooms WHERE hotelId = ?";
            try (PreparedStatement roomStatement = conn.prepareStatement(roomQuery)) {
                roomStatement.setInt(1, hotelId);
                try (ResultSet roomResultSet = roomStatement.executeQuery()) {
                    while (roomResultSet.next()) {
                        rooms.add(new Room(
                                roomResultSet.getInt("id"),
                                roomResultSet.getInt("hotelId"),
                                roomResultSet.getString("label"),
                                roomResultSet.getInt("capacity"),
                                roomResultSet.getDouble("price")
                        ));
                    }
                }
            }
        }
        return rooms;
    }

    // Add a new room
    public void addRoom(int hotelId, String label, int capacity, double price) throws SQLException {
        try (Connection conn = DataBaseConnection.getConnection()) {
            String sql = "INSERT INTO rooms (hotelId, label, capacity, price) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, hotelId);
                statement.setString(2, label);
                statement.setInt(3, capacity);
                statement.setDouble(4, price);
                statement.executeUpdate();
            }
        }
    }

    public void deleteRoom(int roomId) throws SQLException {
        try (Connection conn = DataBaseConnection.getConnection()) {
            String sql = "DELETE FROM rooms WHERE id = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, roomId);
                statement.executeUpdate();
            }
        }
    }

    public String getHotelName(int hotelId) throws SQLException {
        try (Connection conn = DataBaseConnection.getConnection()) {
            String hotelQuery = "SELECT name FROM hotels WHERE id = ?";
            try (PreparedStatement hotelStatement = conn.prepareStatement(hotelQuery)) {
                hotelStatement.setInt(1, hotelId);
                try (ResultSet hotelResultSet = hotelStatement.executeQuery()) {
                    if (hotelResultSet.next()) {
                        return hotelResultSet.getString("name");
                    }
                }
            }
        }
        return null;
    }
}