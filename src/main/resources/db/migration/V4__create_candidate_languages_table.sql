CREATE TABLE candidate_languages (
    id BIGSERIAL PRIMARY KEY,
    candidate_id BIGINT NOT NULL,
    language_name VARCHAR(100) NOT NULL,
    level VARCHAR(30) NOT NULL,
    created_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_candidate_languages_candidate
        FOREIGN KEY (candidate_id)
        REFERENCES candidates(id)
        ON DELETE CASCADE,

    CONSTRAINT uk_candidate_languages_candidate_language
        UNIQUE (candidate_id, language_name)
);

CREATE index idx_candidate_languages_candidate_id
ON candidate_languages(candidate_id);