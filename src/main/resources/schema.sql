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