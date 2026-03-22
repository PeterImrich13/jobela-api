CREATE TABLE candidate_location_preferences (
    id BIGSERIAL PRIMARY KEY,
    candidate_id BIGINT NOT NULL,
    city VARCHAR(100),
    country VARCHAR(100),
    region VARCHAR(100),
    created_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_candidate_candidate_location_preferences_candidate
        FOREIGN KEY (candidate_id)
        REFERENCES candidates(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_candidate_location_preferences_not_empty
        CHECK (
        city IS NOT NULL OR
        country IS NOT NULL OR
        region IS NOT NULL
        )
);

CREATE INDEX idx_candidate_location_preferences_candidate_id
ON candidate_location_preferences(candidate_id);