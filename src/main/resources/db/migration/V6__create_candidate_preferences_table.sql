CREATE TABLE candidate_preferences (
    id BIGSERIAL PRIMARY KEY,
    candidate_id BIGINT NOT NULL UNIQUE,
    desired_salary_min INTEGER,
    desired_salary_max INTEGER,
    salary_currency VARCHAR (10),
    open_to_work BOOLEAN NOT NULL DEFAULT FALSE,
    availability_date DATE,
    relocation_preference BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_candidate_preferences_candidate
        FOREIGN KEY(candidate_id)
        REFERENCES candidates(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_salary_range
        CHECK (
            desired_salary_min IS NULL
            OR desired_salary_max IS NULL
            OR desired_salary_min <= desired_salary_max
            )
);

