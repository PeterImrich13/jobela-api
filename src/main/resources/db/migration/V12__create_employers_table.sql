CREATE TABLE employers(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    company_name VARCHAR(255) NOT NULL,
    company_description VARCHAR(3000) NOT NULL,
    industry VARCHAR(50) NOT NULL,
    website VARCHAR(100),
    phone VARCHAR(50),
    contact_email VARCHAR(255),
    city VARCHAR(100),
    country VARCHAR(100),
    street VARCHAR(100),
    street_number VARCHAR(10),
    profile_photo VARCHAR(500),
    profile_visible BOOLEAN DEFAULT FALSE,
    verified BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_employers_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);