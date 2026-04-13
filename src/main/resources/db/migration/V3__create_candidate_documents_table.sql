CREATE TABLE candidate_documents (
    id BIGSERIAL PRIMARY KEY,
    candidate_id BIGINT NOT NULL,
    document_type VARCHAR(50) NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_url VARCHAR(1000) NOT NULL,
    mime_type VARCHAR(100),
    file_size BIGINT,
    uploaded_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_candidate_documents_candidate
        FOREIGN KEY (candidate_id)
        REFERENCES candidates(id)
        ON DELETE CASCADE,

    CONSTRAINT uk_candidate_documents_file_url
        UNIQUE (file_url)
);

CREATE INDEX idx_fk_candidate_documents_candidate_id
ON candidate_documents(candidate_id);

CREATE INDEX idx_candidate_documents_document_type
ON candidate_documents(document_type);