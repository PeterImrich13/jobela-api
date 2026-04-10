CREATE TABLE candidate_certifications (
    id BIGSERIAL PRIMARY KEY,
    candidate_id BIGINT NOT NULL,
    name VARCHAR(150) NOT NULL,
    issuer VARCHAR(150) NOT NULL,
    issue_date DATE,
    credential_url VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_candidate_certifications_candidate
        FOREIGN KEY (candidate_id)
        REFERENCES candidates(id)
        ON DELETE CASCADE,

    CONSTRAINT uk_candidate_certifications_candidate_name_issuer
        UNIQUE (candidate_id, name, issuer)
    );

CREATE INDEX idx_candidate_certifications_candidate_id
ON candidate_certifications(candidate_id);