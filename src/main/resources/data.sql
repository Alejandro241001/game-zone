-- Insert data into the studios table
INSERT INTO studios (name, country) VALUES
('Nintendo', 'Japan'),
('FromSoftware', 'Japan'),
('Santa Monica Studio', 'United States'),
('CD Projekt Red', 'Poland'),
('Mojang Studios', 'Sweden'),
('Rockstar Games', 'United States'),
('Team Cherry', 'Australia');

-- Insert data into the video_games table
INSERT INTO video_games (name, studio_id) VALUES
('The Legend of Zelda: Breath of the Wild', 1),
('Elden Ring', 2),
('God of War Ragnarok', 3),
('Cyberpunk 2077', 4),
('Minecraft', 5),
('Red Dead Redemption 2', 6),
('Hollow Knight', 7);