-- V2__Sample_data.sql
-- Sample data for Asturian Cinema demo

-- Insert roles
INSERT INTO roles (name) VALUES ('USER'), ('ADMIN'), ('MODERATOR') ON CONFLICT (name) DO NOTHING;

-- Insert categories
INSERT INTO categories (name, description, icon_url) VALUES 
('Drama', 'Películas dramáticas', 'https://example.com/icons/drama.png'),
('Comedia', 'Películas cómicas', 'https://example.com/icons/comedy.png'),
('Documental', 'Documentales', 'https://example.com/icons/documentary.png'),
('Acción', 'Películas de acción', 'https://example.com/icons/action.png'),
('Ciencia Ficción', 'Ciencia ficción', 'https://example.com/icons/scifi.png'),
('Terror', 'Películas de terror', 'https://example.com/icons/horror.png')
ON CONFLICT (name) DO NOTHING;

-- Insert admin user (password: admin123 - BCrypt hash)
INSERT INTO users (username, email, password, full_name, bio, is_active) 
VALUES ('admin', 'admin@asturiancinema.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Administrador', 'Administrador del sitio', true)
ON CONFLICT (username) DO NOTHING;

-- Insert demo user (password: demo123 - BCrypt hash)
INSERT INTO users (username, email, password, full_name, bio, is_active) 
VALUES ('demo', 'demo@asturiancinema.com', '$2a$10$6Z7kJZxmNJU4QV7xNQVgxOFJ3BTMM4aWvLZwXnC3T8YdPqKxXH0G', 'Usuario Demo', 'Usuario de demostración', true)
ON CONFLICT (username) DO NOTHING;

-- Assign admin role to admin user
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r 
WHERE u.username = 'admin' AND r.name = 'ADMIN'
ON CONFLICT DO NOTHING;

-- Assign user role to demo user
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r 
WHERE u.username = 'demo' AND r.name = 'USER'
ON CONFLICT DO NOTHING;

-- Insert sample movies
INSERT INTO movies (title, original_title, synopsis, director, year, duration, genre, cover_image, backdrop_image, trailer_url, is_asturian, origin, language, cast, production_company, filming_locations, published, views, created_by) VALUES
('La Vuelta', NULL, 'Un documental que explora los pueblos más hermoso de Asturias a través de sus habitantes.', 'Miguel Álvarez', 2023, 95, 'Documental', 'https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400', 'https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=1200', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', true, 'Asturias', 'ast', 'Varios habitantes de Asturias', 'Producciones del Norte', 'Cangas de Onís, Lagos de Covadonga', true, 1250, 1),

('El Gaitero', NULL, 'Historia de un gaitero tradicional que lucha por mantener viva la música tradicional asturiana en un mundo moderno.', 'Ana González', 2022, 108, 'Drama', 'https://images.unsplash.com/photo-1514525253161-7a46d19cd819?w=400', 'https://images.unsplash.com/photo-1514525253161-7a46d19cd819?w=1200', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', true, 'Asturias', 'ast', 'Pablo Martínez, María Sánchez', 'Asturias Films', 'Avilés, Gijón', true, 890, 1),

('Memorias de un Pionero', NULL, 'Documental sobre la historia de la industria cinematográfica en Asturias desde sus inicios.', 'Carlos Rodríguez', 2024, 82, 'Documental', 'https://images.unsplash.com/photo-1489599849927-2ee91cede3ba?w=400', 'https://images.unsplash.com/photo-1489599849927-2ee91cede3ba?w=1200', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', true, 'Asturias', 'es', 'Historiadores locales', 'Universidad de Oviedo', 'Oviedo', true, 567, 1),

('El Bosque de los Muertos', NULL, 'Thriller de misterio ambientado en los bosques de Picos de Europa.', 'David Fernández', 2023, 94, 'Terror', 'https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=400', 'https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=1200', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', true, 'Asturias', 'es', 'Laura Torres, Jorge Blanco', 'Fear Productions', 'Picos de Europa, Amieva', true, 2100, 1),

('Crónicas de la Mar', NULL, 'Drama costero sobre una familia de pescadores de Luarca que enfrenta los cambios del mar y la vida.', 'Sofia Menéndez', 2021, 115, 'Drama', 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400', 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=1200', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', true, 'Asturias', 'es', 'Ricardo Acevedo, Elena Suárez', 'Mar Azul Studios', 'Luarca, Cudillero', true, 1567, 1),

('A Furia de Mar', NULL, 'Comedia ambientada en un bar de Avilés donde los habitual resolverán un misterio.', 'Pablo Iglesias', 2024, 98, 'Comedia', 'https://images.unsplash.com/photo-1517604931442-7e0c8ed2963c?w=400', 'https://images.unsplash.com/photo-1517604931442-7e0c8ed2963c?w=1200', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', true, 'Asturias', 'ast', 'Elena García, Marcos Ruiz', 'Asturias Comedia', 'Avilés', true, 3200, 1),

('Llastres', NULL, 'Corto documental sobre el pueblo pesquero de Llastres y sus tradiciones.', 'María Fernández', 2023, 28, 'Documental', 'https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=400', 'https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=1200', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', true, 'Asturias', 'ast', 'Vecinos de Llastres', 'Cine Amateur Astur', 'Llastres', true, 450, 1),

('El Rey Silencio', NULL, 'Drama histórico sobre un rey medieval escondido en las montañas de Asturias.', 'Antonio Vega', 2022, 125, 'Drama', 'https://images.unsplash.com/photo-1460881680093-7bc456c8fe6d?w=400', 'https://images.unsplash.com/photo-1460881680093-7bc456c8fe6d?w=1200', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', true, 'Asturias', 'es', 'Carlos Álvarez, Patricia Núñez', 'Historia Films', 'Somiedo, Pola de Lena', true, 980, 1),

('8M: Voces de Asturias', NULL, 'Documental sobre el movimiento feminista en Asturias.', 'Lucía Martínez', 2024, 75, 'Documental', 'https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?w=400', 'https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?w=1200', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', true, 'Asturias', 'es', 'Mujeres de toda Asturias', 'Colectivo Feminista', 'Oviedo, Gijón, Avilés', true, 780, 1),

('El Chalán de Prada', NULL, 'Historia de un maestro constructor de pallozas en los Picos de Europa.', 'Roberto Alonso', 2023, 65, 'Documental', 'https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400', 'https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=1200', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', true, 'Asturias', 'ast', 'Manuel Rodríguez', 'Cultura Astur', 'Prada, Caso', true, 340, 1);

-- Insert sample reviews
INSERT INTO reviews (user_id, movie_id, rating, comment) VALUES
-- Reviews for 'La Vuelta' (movie id 1)
(2, 1, 5, 'Precioso documental que muestra la esencia de Asturias. Muy recomendable.'),
-- Reviews for 'El Gaitero' (movie id 2)
(2, 2, 4, 'Una historia conmovedora sobre la tradición musical asturiana.'),
-- Reviews for 'El Bosque de los Muertos' (movie id 4)
(2, 4, 4, 'Terror efectivo con unos paisajes impresionantes de Asturias.'),
-- Reviews for 'A Furia de Mar' (movie id 6)
(2, 6, 5, 'La mejor comedia asturiana que he visto. Muy divertida.');

-- Insert sample favorites
INSERT INTO favorites (user_id, movie_id) VALUES
(2, 1),
(2, 4),
(2, 6),
(2, 8);

-- Insert sample posts for blog
INSERT INTO posts (title, slug, excerpt, content, category, author_id, published_at, is_published, views) VALUES
('Bienvenido a Asturian Cinema', 'bienvenido-a-asturian-cinema', 'Descubre la nueva plataforma para el cine asturiano', 'Nos complace dar la bienvenida a Asturian Cinema, una plataforma dedicada a promover y difundir el cine realizado en Asturias...', 'Noticias', 1, CURRENT_TIMESTAMP, true, 450),
('Las mejores películas asturianas de 2024', 'mejores-peliculas-asturianeas-2024', 'Un repaso a las producciones más destacadas del año', 'Este año hemos visto un aumento significativo en la producción cinematográfica en Asturias...', 'Noticias', 1, CURRENT_TIMESTAMP, true, 320),
('Entrevista al director Miguel Álvarez', 'entrevista-miguel-alvarez', 'Hablamos con el director de La Vuelta', 'Miguel Álvarez nos cuenta cómo fue rodar su último documental en los pueblos de Asturias...', 'Entrevistas', 1, CURRENT_TIMESTAMP, true, 280);

-- Insert sample notifications
INSERT INTO notifications (user_id, type, message, link, is_read, created_at) VALUES
(2, 'REVIEW', 'Nuevo comentario en una película', '/movie/1', false, CURRENT_TIMESTAMP),
(2, 'FOLLOW', 'El usuario admin te está siguiendo', '/profile/1', true, CURRENT_TIMESTAMP);
