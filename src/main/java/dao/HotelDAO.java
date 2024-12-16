package dao;

import Models.Hotel;
import Models.Room;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelDAO {

    // Get all hotels with optional filtering
    public List<Hotel> getHotels(String filterName, String filterCity, String filterStars) throws SQLException {
        List<Hotel> hotels = new ArrayList<>();

        try (Connection conn = DataBaseConnection.getConnection()) {
            String sql = "SELECT * FROM hotels ";
            boolean test = false;

            // Dynamically build WHERE clause based on filters
            if (filterName != null && !filterName.isEmpty()) {
                sql += "WHERE name LIKE ?";
                test = true;
            }

            if (filterCity != null && !filterCity.isEmpty()) {
                if (!test) {
                    sql += "WHERE city LIKE ? ";
                    test = true;
                } else {
                    sql += " AND city LIKE ? ";
                }
            }

            if (filterStars != null && !filterStars.isEmpty()) {
                if (!test) {
                    sql += "WHERE stars = ? ";
                } else {
                    sql += " AND stars = ?";
                }
            }

            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                int paramIndex = 1;

                if (filterName != null && !filterName.isEmpty()) {
                    statement.setString(paramIndex++, "%" + filterName + "%");
                }

                if (filterCity != null && !filterCity.isEmpty()) {
                    statement.setString(paramIndex++, "%" + filterCity + "%");
                }

                if (filterStars != null && !filterStars.isEmpty()) {
                    statement.setInt(paramIndex++, Integer.parseInt(filterStars));
                }

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        hotels.add(new Hotel(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("city"),
                                resultSet.getInt("stars"),
                                resultSet.getString("descriptions"),
                                resultSet.getString("image")
                        ));
                    }
                }
            }
        }

        return hotels;
    }

    // Add a new hotel
    public void addHotel(String name, String city, int stars, String description, String imagePath) throws SQLException {
        try (Connection conn = DataBaseConnection.getConnection()) {
            String sql = "INSERT INTO hotels (name, city, image, descriptions, stars) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, city);
                statement.setString(3, imagePath);
                statement.setString(4, description);
                statement.setInt(5, stars);
                statement.executeUpdate();
            }
        }
    }

    // Delete a hotel by ID
    public void deleteHotel(int id) throws SQLException {
        try (Connection conn = DataBaseConnection.getConnection()) {
            String sql = "DELETE FROM hotels WHERE id = ?";

            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        }
    }
    public List<Hotel> filterHotels(String city, String minStars, String maxPrice) throws SQLException {
        List<Hotel> hotels = new ArrayList<>();

        try (Connection conn = DataBaseConnection.getConnection()) {
            StringBuilder query = new StringBuilder("SELECT * FROM hotels WHERE 1=1");

            // Dynamically build query based on filter parameters
            if (city != null && !city.isEmpty()) {
                query.append(" AND city = ?");
            }
            if (minStars != null && !minStars.isEmpty()) {
                query.append(" AND stars >= ?");
            }
            if (maxPrice != null && !maxPrice.isEmpty()) {
                query.append(" AND price_per_night <= ?");
            }

            try (PreparedStatement statement = conn.prepareStatement(query.toString())) {
                int paramIndex = 1;

                // Set parameters dynamically
                if (city != null && !city.isEmpty()) {
                    statement.setString(paramIndex++, city);
                }
                if (minStars != null && !minStars.isEmpty()) {
                    statement.setInt(paramIndex++, Integer.parseInt(minStars));
                }
                if (maxPrice != null && !maxPrice.isEmpty()) {
                    statement.setDouble(paramIndex++, Double.parseDouble(maxPrice));
                }

                // Execute query and map results
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        hotels.add(new Hotel(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("city"),
                                resultSet.getInt("stars"),
                                resultSet.getString("descriptions"),
                                resultSet.getString("image")
                        ));
                    }
                }
            }
        }

        return hotels;
    }

    // Method to get hotel details by ID
    public Hotel getHotelById(int hotelId) {
        Hotel hotel = null;
        String query = "SELECT * FROM hotels WHERE id = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, hotelId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                hotel = new Hotel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("city"),
                        rs.getInt("stars"),
                        rs.getString("descriptions"),
                        rs.getString("image")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hotel;
    }
}
