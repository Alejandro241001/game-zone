-- Insert data into the studios table (15 registros adicionales, total 30)
INSERT IGNORE INTO studios (id, name, country) VALUES
(1, 'Nintendo', 'Japan'),
(2, 'FromSoftware', 'Japan'),
(3, 'Santa Monica Studio', 'United States'),
(4, 'CD Projekt Red', 'Poland'),
(5, 'Mojang Studios', 'Sweden'),
(6, 'Rockstar Games', 'United States'),
(7, 'Team Cherry', 'Australia'),
(8, 'Naughty Dog', 'United States'),
(9, 'Square Enix', 'Japan'),
(10, 'Ubisoft', 'France'),
(11, 'Bethesda Softworks', 'United States'),
(12, 'Capcom', 'Japan'),
(13, 'Blizzard Entertainment', 'United States'),
(14, 'Valve Corporation', 'United States'),
(15, 'Supergiant Games', 'United States'),
(16, 'Konami', 'Japan'),
(17, 'Sega', 'Japan'),
(18, 'Electronic Arts', 'United States'),
(19, 'Insomniac Games', 'United States'),
(20, 'Bandai Namco', 'Japan'),
(21, 'Rare', 'United Kingdom'),
(22, 'Larian Studios', 'Belgium'),
(23, 'Obsidian Entertainment', 'United States'),
(24, 'Guerrilla Games', 'Netherlands'),
(25, 'PlatinumGames', 'Japan'),
(26, 'IO Interactive', 'Denmark'),
(27, 'Activision', 'United States'),
(28, 'Telltale Games', 'United States'),
(29, 'Remedy Entertainment', 'Finland'),
(30, 'Annapurna Interactive', 'United States');

-- Insert data into the video_games table (15 registros adicionales, total 30)
INSERT IGNORE INTO video_games (id, name, studio_id, description, metacritic_score, release_year) VALUES
(1, 'The Legend of Zelda: Breath of the Wild', 1, 'Un juego de mundo abierto con Link explorando Hyrule.', 97.0, 2017),
(2, 'Elden Ring', 2, 'Un RPG de acción en un mundo abierto creado con George R.R. Martin.', 96.0, 2022),
(3, 'God of War Ragnarok', 3, 'La continuación épica de Kratos y Atreus en la mitología nórdica.', 94.0, 2022),
(4, 'Cyberpunk 2077', 4, 'Un RPG futurista en la ciudad de Night City.', 86.0, 2020),
(5, 'Minecraft', 5, 'Un juego de construcción y supervivencia en un mundo de bloques.', 93.0, 2011),
(6, 'Red Dead Redemption 2', 6, 'Una épica aventura en el Salvaje Oeste.', 97.0, 2018),
(7, 'Hollow Knight', 7, 'Un metroidvania con un mundo oscuro y hermoso.', 90.0, 2017),
(8, 'The Last of Us Part II', 8, 'Una historia emocional en un mundo postapocalíptico.', 93.0, 2020),
(9, 'Final Fantasy VII Remake', 9, 'Un remake del clásico RPG con gráficos modernos.', 87.0, 2020),
(10, 'Assassin’s Creed Valhalla', 10, 'Una aventura vikinga en un mundo abierto.', 84.0, 2020),
(11, 'The Elder Scrolls V: Skyrim', 11, 'Un RPG épico en un mundo de fantasía.', 94.0, 2011),
(12, 'Resident Evil Village', 12, 'Terror y acción en un pueblo misterioso.', 84.0, 2021),
(13, 'Overwatch', 13, 'Un shooter en equipo con héroes únicos.', 91.0, 2016),
(14, 'Half-Life: Alyx', 14, 'Una experiencia VR revolucionaria.', 92.0, 2020),
(15, 'Hades', 15, 'Un roguelike mitológico con gran narrativa.', 93.0, 2020),
(16, 'Metal Gear Solid V: The Phantom Pain', 16, 'Un juego de sigilo en un mundo abierto.', 93.0, 2015),
(17, 'Sonic Mania', 17, 'Un regreso a las raíces de Sonic con estilo retro.', 86.0, 2017),
(18, 'FIFA 23', 18, 'El simulador de fútbol más popular.', 77.0, 2022),
(19, 'Spider-Man: Miles Morales', 19, 'Una aventura de superhéroes en Nueva York.', 85.0, 2020),
(20, 'Dark Souls III', 20, 'Un RPG de acción desafiante y oscuro.', 89.0, 2016),
(21, 'Sea of Thieves', 21, 'Aventuras piratas en un mundo multijugador.', 81.0, 2018),
(22, 'Baldur’s Gate 3', 22, 'Un RPG profundo basado en Dungeons & Dragons.', 96.0, 2023),
(23, 'The Outer Worlds', 23, 'Un RPG de ciencia ficción con humor.', 85.0, 2019),
(24, 'Horizon Forbidden West', 24, 'Una aventura en un mundo postapocalíptico con robots.', 88.0, 2022),
(25, 'Bayonetta 3', 25, 'Acción estilizada con una bruja poderosa.', 88.0, 2022),
(26, 'Hitman 3', 26, 'Sigilo y asesinatos creativos en entornos globales.', 87.0, 2021),
(27, 'Call of Duty: Modern Warfare II', 27, 'Un shooter militar intenso.', 79.0, 2022),
(28, 'The Walking Dead: Season 1', 28, 'Una narrativa interactiva emocionante.', 92.0, 2012),
(29, 'Alan Wake II', 29, 'Terror psicológico con una gran historia.', 89.0, 2023),
(30, 'Stray', 30, 'Una aventura como gato en un mundo cyberpunk.', 83.0, 2022);

-- Insert data into the platforms table (15 registros adicionales, total 30)
INSERT IGNORE INTO platforms (id, name) VALUES
(1, 'Nintendo Switch'),
(2, 'PlayStation 5'),
(3, 'Xbox Series X'),
(4, 'PC'),
(5, 'PlayStation 4'),
(6, 'Xbox One'),
(7, 'Wii U'),
(8, 'Nintendo 3DS'),
(9, 'PlayStation 3'),
(10, 'Xbox 360'),
(11, 'PlayStation Vita'),
(12, 'iOS'),
(13, 'Android'),
(14, 'MacOS'),
(15, 'Linux'),
(16, 'Nintendo DS'),
(17, 'Wii'),
(18, 'GameCube'),
(19, 'PlayStation 2'),
(20, 'Xbox'),
(21, 'Stadia'),
(22, 'Oculus Quest'),
(23, 'Steam Deck'),
(24, 'Atari VCS'),
(25, 'Amazon Luna'),
(26, 'Nintendo 64'),
(27, 'Sega Genesis'),
(28, 'Game Boy Advance'),
(29, 'PSP'),
(30, 'Dreamcast');

-- Insert data into the video_games_platforms table (15 registros adicionales, total > 30)
INSERT IGNORE INTO video_games_platforms (video_game_id, platform_id) VALUES
(1, 1), (1, 7), -- Zelda en Switch y Wii U
(2, 2), (2, 3), (2, 4), -- Elden Ring en PS5, Xbox Series X, PC
(3, 2), (3, 5), -- God of War Ragnarok en PS5 y PS4
(4, 2), (4, 3), (4, 4), -- Cyberpunk 2077 en PS5, Xbox Series X, PC
(5, 4), (5, 5), (5, 6), (5, 12), (5, 13), -- Minecraft en PC, PS4, Xbox One, iOS, Android
(6, 5), (6, 6), (6, 4), -- Red Dead Redemption 2 en PS4, Xbox One, PC
(7, 4), (7, 1), -- Hollow Knight en PC y Switch
(8, 5), (8, 2), -- The Last of Us Part II en PS4 y PS5
(9, 5), (9, 2), -- Final Fantasy VII Remake en PS4 y PS5
(10, 2), (10, 3), (10, 4), -- Assassin’s Creed Valhalla en PS5, Xbox Series X, PC
(11, 4), (11, 9), (11, 10), -- Skyrim en PC, PS3, Xbox 360
(12, 2), (12, 3), (12, 4), -- Resident Evil Village en PS5, Xbox Series X, PC
(13, 4), (13, 5), (13, 6), -- Overwatch en PC, PS4, Xbox One
(14, 4), -- Half-Life: Alyx en PC (VR)
(15, 4), (15, 1), (15, 14), -- Hades en PC, Switch, MacOS
(16, 5), (16, 6), -- Metal Gear Solid V en PS4 y Xbox One
(17, 1), (17, 23), -- Sonic Mania en Switch y Steam Deck
(18, 2), (18, 3), -- FIFA 23 en PS5 y Xbox Series X
(19, 2), (19, 4), -- Spider-Man: Miles Morales en PS5 y PC
(20, 4), (20, 5), -- Dark Souls III en PC y PS4
(21, 3), (21, 4), -- Sea of Thieves en Xbox Series X y PC
(22, 4), (22, 2), -- Baldur’s Gate 3 en PC y PS5
(23, 4), (23, 6), -- The Outer Worlds en PC y Xbox One
(24, 2), (24, 5), -- Horizon Forbidden West en PS5 y PS4
(25, 1), -- Bayonetta 3 en Switch
(26, 2), (26, 3), -- Hitman 3 en PS5 y Xbox Series X
(27, 2), (27, 3), -- Call of Duty MWII en PS5 y Xbox Series X
(28, 4), (28, 9), -- The Walking Dead en PC y PS3
(29, 2), (29, 4), -- Alan Wake II en PS5 y PC
(30, 4), (30, 2); -- Stray en PC y PS5

-- Insert data into the genres table (15 registros adicionales, total 30)
INSERT IGNORE INTO genres (id, name) VALUES
(1, 'Action'),
(2, 'RPG'),
(3, 'Adventure'),
(4, 'Open World'),
(5, 'Sandbox'),
(6, 'Metroidvania'),
(7, 'Survival Horror'),
(8, 'Shooter'),
(9, 'Platformer'),
(10, 'Strategy'),
(11, 'Fighting'),
(12, 'Racing'),
(13, 'Simulation'),
(14, 'Roguelike'),
(15, 'Puzzle'),
(16, 'Stealth'),
(17, 'Sports'),
(18, 'Music'),
(19, 'MMORPG'),
(20, 'Battle Royale'),
(21, 'Hack and Slash'),
(22, 'Visual Novel'),
(23, 'Tower Defense'),
(24, 'Party'),
(25, 'Card Game'),
(26, 'Point and Click'),
(27, 'Flight Simulator'),
(28, 'Real-Time Strategy'),
(29, 'Beat \'em Up'),
(30, 'Interactive Drama');

-- Insert data into the video_games_genres table (15 registros adicionales, total > 30)
INSERT IGNORE INTO video_games_genres (video_game_id, genre_id) VALUES
(1, 3), (1, 4), -- Zelda: Adventure, Open World
(2, 2), (2, 4), -- Elden Ring: RPG, Open World
(3, 1), (3, 3), -- God of War: Action, Adventure
(4, 2), (4, 4), -- Cyberpunk: RPG, Open World
(5, 5), -- Minecraft: Sandbox
(6, 3), (6, 4), -- Red Dead Redemption 2: Adventure, Open World
(7, 6), -- Hollow Knight: Metroidvania
(8, 1), (8, 3), -- The Last of Us Part II: Action, Adventure
(9, 2), -- Final Fantasy VII Remake: RPG
(10, 1), (10, 4), -- Assassin’s Creed Valhalla: Action, Open World
(11, 2), (11, 4), -- Skyrim: RPG, Open World
(12, 7), -- Resident Evil Village: Survival Horror
(13, 8), -- Overwatch: Shooter
(14, 1), (14, 3), -- Half-Life: Alyx: Action, Adventure
(15, 14), -- Hades: Roguelike
(16, 16), -- Metal Gear Solid V: Stealth
(17, 9), -- Sonic Mania: Platformer
(18, 17), -- FIFA 23: Sports
(19, 1), (19, 3), -- Spider-Man: Miles Morales: Action, Adventure
(20, 2), (20, 21), -- Dark Souls III: RPG, Hack and Slash
(21, 4), (21, 5), -- Sea of Thieves: Open World, Sandbox
(22, 2), -- Baldur’s Gate 3: RPG
(23, 2), (23, 4), -- The Outer Worlds: RPG, Open World
(24, 3), (24, 4), -- Horizon Forbidden West: Adventure, Open World
(25, 21), -- Bayonetta 3: Hack and Slash
(26, 16), -- Hitman 3: Stealth
(27, 8), -- Call of Duty MWII: Shooter
(28, 30), -- The Walking Dead: Interactive Drama
(29, 7), -- Alan Wake II: Survival Horror
(30, 3); -- Stray: Adventure

-- Insert data into the roles table
INSERT IGNORE INTO roles (id, name) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_MANAGER'),
(3, 'ROLE_USER');

-- Insert data into the users table (contraseña: "password")
INSERT IGNORE INTO users (id, username, password, enabled, first_name, last_name, image, created_date, last_modified_date, last_password_change_date) VALUES
(1, 'admin', '$2b$12$FVRijCavVZ7Qt15.CQssHe9m/6eLAdjAv0PiOKFIjMU161wApxzye', true, 'Admin', 'User', '/images/admin.jpg', NOW(), NOW(), NOW()),
(2, 'manager', '$2b$12$FVRijCavVZ7Qt15.CQssHe9m/6eLAdjAv0PiOKFIjMU161wApxzye', true, 'Manager', 'User', '/images/manager.jpg', NOW(), NOW(), NOW()),
(3, 'normal', '$2b$12$FVRijCavVZ7Qt15.CQssHe9m/6eLAdjAv0PiOKFIjMU161wApxzye', true, 'Regular', 'User', '/images/user.jpg', NOW(), NOW(), NOW());

-- Insert data into the user_roles table
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES
(1, 1), -- admin con ROLE_ADMIN
(2, 2), -- manager con ROLE_MANAGER
(3, 3); -- normal con ROLE_USER

-- Insert data into the reviews table (sin cambios, 15 registros originales)
INSERT IGNORE INTO reviews (id, user_id, video_game_id, review_text, rating, created_date) VALUES
(1, 3, 1, 'Una obra maestra, el mundo abierto es increíble.', 9.5, NOW()),
(2, 4, 2, 'Difícil pero muy gratificante, gran diseño.', 9.0, NOW()),
(3, 5, 3, 'La historia de Kratos sigue siendo épica.', 9.2, NOW()),
(4, 6, 4, 'Tiene fallos, pero la ambientación es única.', 8.0, NOW()),
(5, 7, 5, 'Infinitas posibilidades, nunca me aburro.', 9.8, NOW()),
(6, 8, 6, 'El mejor juego del oeste que he jugado.', 9.7, NOW()),
(7, 9, 7, 'Un arte en cada rincón, imprescindible.', 9.0, NOW()),
(8, 10, 8, 'Emocionante pero muy intenso.', 8.5, NOW()),
(9, 11, 9, 'Un remake que supera expectativas.', 9.0, NOW()),
(10, 12, 10, 'Me encanta explorar como vikingo.', 8.8, NOW()),
(11, 13, 11, 'Todavía lo juego después de años.', 9.5, NOW()),
(12, 14, 12, 'Da miedo pero no puedo parar.', 8.7, NOW()),
(13, 15, 13, 'El mejor shooter en equipo.', 9.0, NOW()),
(14, 3, 14, 'Increíble en VR, ojalá más juegos así.', 9.3, NOW()),
(15, 4, 15, 'Adictivo y con gran historia.', 9.5, NOW());