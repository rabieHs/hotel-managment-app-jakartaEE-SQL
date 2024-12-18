package Models;

import java.time.LocalDate;

public class Reservation {
    private int id;
    private int roomId;
    private int hotelId;
    private String userName;
    private String userPhone;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalPrice;

    // Additional fields for joined data
    private String hotelName;
    private String roomLabel;

    // Constructor
    public Reservation(int id, int roomId, int hotelId, String userName, String userPhone,
                       LocalDate startDate, LocalDate endDate, double totalPrice) {
        this.id = id;
        this.roomId = roomId;
        this.hotelId = hotelId;
        this.userName = userName;
        this.userPhone = userPhone;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public int getHotelId() { return hotelId; }
    public void setHotelId(int hotelId) { this.hotelId = hotelId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserPhone() { return userPhone; }
    public void setUserPhone(String userPhone) { this.userPhone = userPhone; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }

    public String getRoomLabel() { return roomLabel; }
    public void setRoomLabel(String roomLabel) { this.roomLabel = roomLabel; }
}