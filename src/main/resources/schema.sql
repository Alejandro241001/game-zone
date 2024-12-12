-- Table for studios
CREATE TABLE IF NOT EXISTS studios(
    id INT AUTO_INCREMENT PRIMARY KEY,   -- Unique ID for the studio
    name VARCHAR(100) NOT NULL,          -- Studio name
    country VARCHAR(50)                  -- Studio's country
);

-- Table for video games
CREATE TABLE IF NOT EXISTS video_games (
    id INT AUTO_INCREMENT PRIMARY KEY,   -- Unique ID for the video game
    name VARCHAR(100) NOT NULL,          -- Name of the video game
    studio_id INT NOT NULL,           -- Foreign key linking to studios table
    FOREIGN KEY (studio_id) REFERENCES studios(id)
);

-- Crear la tabla 'users'
CREATE TABLE IF NOT EXISTS users (
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   username VARCHAR(50) UNIQUE NOT NULL,
   password VARCHAR(100) NOT NULL,
   enabled BOOLEAN NOT NULL,
   first_name VARCHAR(50) NOT NULL,
   last_name VARCHAR(50) NOT NULL,
   image VARCHAR(255),
   created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   last_password_change_date TIMESTAMP
);
