-- Tabla para estudios
CREATE TABLE IF NOT EXISTS studios (
    id INT AUTO_INCREMENT PRIMARY KEY,      -- ID único para el estudio
    name VARCHAR(100) NOT NULL,             -- Nombre del estudio
    country VARCHAR(50)                     -- País del estudio
);

-- Tabla para videojuegos
CREATE TABLE IF NOT EXISTS video_games (
    id INT AUTO_INCREMENT PRIMARY KEY,      -- ID único para el videojuego
    name VARCHAR(100) NOT NULL,             -- Nombre del videojuego
    studio_id INT NOT NULL,                 -- Clave foránea al estudio
    description TEXT,                       -- Descripción del juego
    metacritic_score DECIMAL(4,1),          -- Puntuación de Metacritic (ej. 85.5)
    release_year INT,                       -- Año de lanzamiento
    FOREIGN KEY (studio_id) REFERENCES studios(id) ON DELETE CASCADE
);

-- Tabla para plataformas
CREATE TABLE IF NOT EXISTS platforms (
    id INT AUTO_INCREMENT PRIMARY KEY,      -- ID único para la plataforma
    name VARCHAR(50) NOT NULL               -- Nombre de la plataforma
);

-- Tabla de relación muchos-a-muchos entre videojuegos y plataformas
CREATE TABLE IF NOT EXISTS video_games_platforms (
    video_game_id INT NOT NULL,             -- Clave foránea al videojuego
    platform_id INT NOT NULL,               -- Clave foránea a la plataforma
    PRIMARY KEY (video_game_id, platform_id), -- Clave primaria compuesta
    FOREIGN KEY (video_game_id) REFERENCES video_games(id) ON DELETE CASCADE,
    FOREIGN KEY (platform_id) REFERENCES platforms(id) ON DELETE CASCADE
);

-- Tabla para géneros
CREATE TABLE IF NOT EXISTS genres (
    id INT AUTO_INCREMENT PRIMARY KEY,      -- ID único para el género
    name VARCHAR(50) NOT NULL               -- Nombre del género
);

-- Tabla de relación muchos-a-muchos entre videojuegos y géneros
CREATE TABLE IF NOT EXISTS video_games_genres (
    video_game_id INT NOT NULL,             -- Clave foránea al videojuego
    genre_id INT NOT NULL,                  -- Clave foránea al género
    PRIMARY KEY (video_game_id, genre_id),  -- Clave primaria compuesta
    FOREIGN KEY (video_game_id) REFERENCES video_games(id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE
);

-- Tabla para usuarios
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,   -- ID único para el usuario
    username VARCHAR(50) UNIQUE NOT NULL,   -- Nombre de usuario único
    password VARCHAR(100) NOT NULL,         -- Contraseña (cifrada)
    enabled BOOLEAN NOT NULL,               -- Estado del usuario (activo/inactivo)
    first_name VARCHAR(50) NOT NULL,        -- Nombre del usuario
    last_name VARCHAR(50) NOT NULL,         -- Apellido del usuario
    image VARCHAR(255),                     -- URL de la imagen de perfil
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Fecha de creación
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Última modificación
    last_password_change_date TIMESTAMP     -- Fecha del último cambio de contraseña
);

-- Tabla para roles
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,   -- ID único para el rol
    name VARCHAR(50) UNIQUE NOT NULL        -- Nombre del rol (ej. ROLE_USER)
);

-- Tabla de relación muchos-a-muchos entre usuarios y roles
CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,                -- Clave foránea al usuario
    role_id BIGINT NOT NULL,                -- Clave foránea al rol
    PRIMARY KEY (user_id, role_id),         -- Clave primaria compuesta
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Tabla para reseñas
CREATE TABLE IF NOT EXISTS reviews (
    id INT AUTO_INCREMENT PRIMARY KEY,      -- ID único para la reseña
    user_id BIGINT NOT NULL,                -- Clave foránea al usuario (solo registrados)
    video_game_id INT NOT NULL,             -- Clave foránea al videojuego
    review_text TEXT,                       -- Contenido de la reseña
    rating DECIMAL(2,1) NOT NULL,           -- Puntuación del usuario (0-10)
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Fecha de creación
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (video_game_id) REFERENCES video_games(id) ON DELETE CASCADE
);

-- Tabla para listas personalizadas (vacía al inicio, se llenará vía API)
CREATE TABLE IF NOT EXISTS custom_lists (
    id INT AUTO_INCREMENT PRIMARY KEY,      -- ID único para la lista
    user_id BIGINT NOT NULL,                -- Clave foránea al usuario (solo registrados)
    name VARCHAR(100) NOT NULL,             -- Nombre de la lista
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Fecha de creación
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Tabla de relación muchos-a-muchos entre listas personalizadas y videojuegos (vacía al inicio)
CREATE TABLE IF NOT EXISTS custom_lists_video_games (
    list_id INT NOT NULL,                   -- Clave foránea a la lista
    video_game_id INT NOT NULL,             -- Clave foránea al videojuego
    PRIMARY KEY (list_id, video_game_id),   -- Clave primaria compuesta
    FOREIGN KEY (list_id) REFERENCES custom_lists(id) ON DELETE CASCADE,
    FOREIGN KEY (video_game_id) REFERENCES video_games(id) ON DELETE CASCADE
);