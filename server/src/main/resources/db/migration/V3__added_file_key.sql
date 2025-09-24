CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

ALTER TABLE files
    ADD file_key UUID DEFAULT uuid_generate_v4();

ALTER TABLE files
    ALTER COLUMN file_key SET NOT NULL;

ALTER TABLE files
    ADD CONSTRAINT uc_files_filekey UNIQUE (file_key);