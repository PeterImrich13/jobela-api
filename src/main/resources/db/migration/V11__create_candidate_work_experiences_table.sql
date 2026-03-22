CREATE TABLE candidate_work_experiences (
    id BIGSERIAL PRIMARY KEY,
    candidate_id BIGINT NOT NULL,
    company_name VARCHAR(150) NOT NULL,
    job_title VARCHAR(150) NOT NULL,
    employment_type VARCHAR(30),
    city VARCHAR(100),
    country VARCHAR(100),
    start_date DATE NOT NULL,
    end_date DATE,
    currently_working BOOLEAN NOT NULL DEFAULT FALSE,
    description VARCHAR(4000),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_candidate_work_experiences_candidate
        FOREIGN KEY (candidate_id)
        REFERENCES candidates(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_candidate_work_experiences_dates
        CHECK (
        end_date IS NULL OR start_date <= end_date
        )
);

CREATE INDEX idx_candidate_work_experiences_candidate_id
ON candidate_work_experiences(candidate_id);