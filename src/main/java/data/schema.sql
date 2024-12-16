-- Schema for Account table
CREATE TABLE accounts (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          username VARCHAR(255) NOT NULL,
                          password VARCHAR(255) NOT NULL,
                          role VARCHAR(50) NOT NULL,
                          email VARCHAR(255) NOT NULL UNIQUE
);

-- Insert default admin account
INSERT INTO accounts (username, password, role, email)
VALUES ('admin', 'admin123', 'admin', 'admin@example.com');

-- Schema for Hotel table
CREATE TABLE hotels (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        city VARCHAR(255) NOT NULL,
                        stars INT CHECK(stars >= 1 AND stars <= 5),
                        descriptions TEXT,
                        image VARCHAR(255)
);

-- Schema for Room table
CREATE TABLE rooms (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       hotelId INT NOT NULL,
                       label VARCHAR(255) NOT NULL,
                       capacity INT NOT NULL,
                       price DECIMAL(10, 2) NOT NULL,
                       FOREIGN KEY (hotelId) REFERENCES hotels(id) ON DELETE CASCADE
);