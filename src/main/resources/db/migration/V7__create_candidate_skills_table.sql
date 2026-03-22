CREATE TABLE candidate_skills (
    id BIGSERIAL PRIMARY KEY,
    candidate_id BIGINT NOT NULL,
    skill_name VARCHAR(100) NOT NULL,
    level VARCHAR(30) NOT NULL,
    created_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_candidate_skills_candidate
        FOREIGN KEY (candidate_id)
        REFERENCES candidates(id)
        ON DELETE CASCADE,

    CONSTRAINT uk_candidate_skills_candidate_id_skill_name
        UNIQUE (candidate_id, skill_name)
);

CREATE INDEX idx_candidate_skills_candidate_id
ON candidate_skills(candidate_id);