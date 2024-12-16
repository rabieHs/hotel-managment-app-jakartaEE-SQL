package Models;

import java.io.File;

public class Hotel {
	private int id;
	private String name;
	private String city;
	private int stars;
	private String descriptions;
	private String image;

	// Default constructor
	public Hotel(int id, String name, String city, int stars, String descriptions, String image) {
		this.id = id;
		this.name = name;
		this.city = city;
		this.stars = stars;
		this.descriptions = descriptions;
		this.image = image;
	}


	// Getters
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCity() {
		return city;
	}

	public int getStars() {
		return stars;
	}

	public String getDescriptions() {
		return descriptions;
	}

	public String getImage() {
		return image;
	}

	// Setters
	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public void setImage(String imagePath) {
		File imageFile = new File(imagePath);
		if (imageFile.exists() && (imagePath.endsWith(".png") ||
				imagePath.endsWith(".jpg") ||
				imagePath.endsWith(".jpeg") ||
				imagePath.endsWith(".gif"))) {
			this.image = imagePath;
		} else {
			this.image = "default-hotel.png";
		}
	}

	@Override
	public String toString() {
		return "Hotel{id=" + id + ", name='" + name + "', city='" + city + "', stars=" + stars + ", descriptions='" + descriptions + "', image='" + image + "'}";
	}
}
