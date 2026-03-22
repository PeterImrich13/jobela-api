CREATE TABLE candidate_educations (
    id BIGSERIAL PRIMARY KEY,
    candidate_id BIGINT NOT NULL,
    school_name VARCHAR(150) NOT NULL,
    education_level VARCHAR(30) NOT NULL,
    field_of_study VARCHAR(150),
    city VARCHAR(100),
    country VARCHAR(100),
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP,
    currently_studying BOOLEAN NOT NULL DEFAULT FALSE,
    description VARCHAR(3000),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_candidate_educations_candidate
        FOREIGN KEY (candidate_id)
        REFERENCES candidates(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_candidate_educations_dates
        CHECK (
            end_date IS NULL OR start_date <= end_date
        )
    );

CREATE INDEX idx_candidate_educations_candidate_id
ON candidate_educations(candidate_id);
