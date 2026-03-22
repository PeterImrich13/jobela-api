CREATE TABLE candidates (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    title_before VARCHAR(50),
    title_after VARCHAR(50),
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    gender VARCHAR(50),
    phone VARCHAR(50),
    city VARCHAR(100),
    country VARCHAR(100),
    nationality VARCHAR(100),
    date_of_birth DATE,
    headline VARCHAR(150),
    summary TEXT,
    profile_photo_url TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_candidates_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);