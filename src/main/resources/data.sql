-- Insert data into the studios table
INSERT IGNORE INTO studios (id,name, country) VALUES
(1,'Nintendo', 'Japan'),
(2,'FromSoftware', 'Japan'),
(3,'Santa Monica Studio', 'United States'),
(4,'CD Projekt Red', 'Poland'),
(5,'Mojang Studios', 'Sweden'),
(6,'Rockstar Games', 'United States'),
(7,'Team Cherry', 'Australia');

-- Insert data into the video_games table
INSERT IGNORE INTO video_games (id,name, studio_id) VALUES
(1,'The Legend of Zelda: Breath of the Wild', 1),
(2,'Elden Ring', 2),
(3,'God of War Ragnarok', 3),
(4,'Cyberpunk 2077', 4),
(5,'Minecraft', 5),
(6,'Red Dead Redemption 2', 6),
(7,'Hollow Knight', 7);

-- Crear la tabla 'roles'
CREATE TABLE IF NOT EXISTS roles (
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   name VARCHAR(50) UNIQUE NOT NULL
);

-- Crear la tabla 'user_roles'
CREATE TABLE IF NOT EXISTS user_roles (
   user_id BIGINT NOT NULL,
   role_id BIGINT NOT NULL,
   PRIMARY KEY (user_id, role_id),
   FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
   FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Insertar datos de ejemplo para 'roles'
INSERT IGNORE INTO roles (id, name) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_MANAGER'),
(3, 'ROLE_USER');


-- Insertar datos de ejemplo para 'users'. La contraseña de cada usuario es password
INSERT IGNORE INTO users (id, username, password, enabled, first_name, last_name, image, created_date, last_modified_date, last_password_change_date) VALUES
(1, 'admin', '$2b$12$FVRijCavVZ7Qt15.CQssHe9m/6eLAdjAv0PiOKFIjMU161wApxzye', true, 'Admin', 'User', '/images/admin.jpg', NOW(), NOW(), NOW()),
(2, 'manager', '$2b$12$FVRijCavVZ7Qt15.CQssHe9m/6eLAdjAv0PiOKFIjMU161wApxzye', true, 'Manager', 'User', '/images/manager.jpg', NOW(), NOW(), NOW()),
(3, 'normal', '$2b$12$FVRijCavVZ7Qt15.CQssHe9m/6eLAdjAv0PiOKFIjMU161wApxzye', true, 'Regular', 'User', '/images/user.jpg', NOW(), NOW(), NOW());


-- Asignar el rol de administrador al usuario con id 1
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES
(1, 1);
-- Asignar el rol de gestor al usuario con id 2
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES
(2, 2);
-- Asignar el rol de usuario normal al usuario con id 3
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES
(3, 3);