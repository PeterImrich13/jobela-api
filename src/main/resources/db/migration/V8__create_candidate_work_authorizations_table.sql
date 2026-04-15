CREATE TABLE candidate_work_authorizations (
    id BIGSERIAL PRIMARY KEY,
    candidate_id BIGINT NOT NULL,
    country VARCHAR(100) NOT NULL,
    authorization_type VARCHAR(100) NOT NULL,
    valid_until DATE,
    sponsorship_required BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,


    CONSTRAINT fk_candidate_work_authorizations_candidate
        FOREIGN KEY (candidate_id)
        REFERENCES candidates(id)
        ON DELETE CASCADE,

    CONSTRAINT uk_candidate_work_auth_candidate_country_type
        UNIQUE (candidate_id, country, authorization_type)
);

CREATE INDEX idx_candidate_work_candidate_work_authorizations_candidate_id
ON candidate_work_authorizations(candidate_id);