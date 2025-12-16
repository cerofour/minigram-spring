-- 1. Tabla de Usuarios
-- Nota: Se usa 'users' en plural porque 'user' es palabra reservada en Postgres
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(32) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    fullname VARCHAR(64) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT now(),
    gender INT, -- Podrías considerar usar un ENUM o SMALLINT
    birthdate DATE NOT NULL
);

-- 2. Tabla de Posts
CREATE TABLE posts (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id INT NOT NULL,
    description VARCHAR(255),
    picture_link VARCHAR(255) NOT NULL, -- Aquí guardarás la 'Key' de S3
    CONSTRAINT fk_posts_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 3. Tabla de Likes (Tabla Intermedia)
CREATE TABLE user_likes (
    user_id INT NOT NULL,
    post_id UUID NOT NULL,
    like_date DATE DEFAULT CURRENT_DATE,

    -- Llave compuesta: evita likes duplicados y sirve como PK
    PRIMARY KEY (user_id, post_id),

    CONSTRAINT fk_likes_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_likes_post FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
);

-- 4. Tabla de Seguidores (Relación recursiva)
CREATE TABLE user_follows (
    follower_id INT NOT NULL,
    followee_id INT NOT NULL,
    follow_date DATE DEFAULT CURRENT_DATE,

    -- Llave compuesta: evita seguir a la misma persona dos veces
    PRIMARY KEY (follower_id, followee_id),

    CONSTRAINT fk_follow_follower FOREIGN KEY (follower_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_follow_followee FOREIGN KEY (followee_id) REFERENCES users(id) ON DELETE CASCADE,

    -- Regla de validación: Un usuario no puede seguirse a sí mismo
    CONSTRAINT check_no_self_follow CHECK (follower_id <> followee_id)
);