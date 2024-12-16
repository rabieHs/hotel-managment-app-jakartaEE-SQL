package Models;

public class Room {
    private int id;
    private int hotelId;
    private String label;
    private int capacity;
    private double price;

    public Room(int id, int hotelId, String label, int capacity, double price) {
        this.id = id;
        this.hotelId = hotelId;
        this.label = label;
        this.capacity = capacity;
        this.price = price;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getHotelId() {
        return hotelId;
    }

    public String getLabel() {
        return label;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getPrice() {
        return price;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}