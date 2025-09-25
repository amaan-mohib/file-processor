ALTER TABLE jobs
    ADD query TEXT;

ALTER TABLE jobs
    ADD started_at TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE jobs
    ADD updated_at TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE jobs
    DROP COLUMN manipulation_query;