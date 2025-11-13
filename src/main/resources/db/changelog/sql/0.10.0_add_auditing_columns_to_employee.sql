--liquibase formatted sql

--changeset aleksokol3:1 dbms:postgresql
--preconditions onFail:HALT onError:HALT
-- Change description: (T-001) add created_at column
-- AUTHOR: Aleksey Sokolov
-- Date: 2025.11.13
-- Versioning strategy: Semantic Versioning
ALTER TABLE IF EXISTS employee
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMP WITH TIME ZONE;
-- Rollback ALTER TABLE employee DROP COLUMN IF EXISTS created_at;

--changeset aleksokol3:2 dbms:postgresql
--preconditions onFail:HALT onError:HALT
-- Change description: (T-001) add modified_at column
-- AUTHOR: Aleksey Sokolov
-- Date: 2025.11.13
-- Versioning strategy: Semantic Versioning
ALTER TABLE IF EXISTS employee
    ADD COLUMN IF NOT EXISTS modified_at TIMESTAMP WITH TIME ZONE;
-- Rollback ALTER TABLE employee DROP COLUMN IF EXISTS modified_at;

--changeset aleksokol3:3 dbms:postgresql
--preconditions onFail:HALT onError:HALT
-- Change description: (T-001) add created_by column
-- AUTHOR: Aleksey Sokolov
-- Date: 2025.11.13
-- Versioning strategy: Semantic Versioning
ALTER TABLE IF EXISTS employee
    ADD COLUMN IF NOT EXISTS created_by VARCHAR(256);
-- Rollback ALTER TABLE employee DROP COLUMN IF EXISTS created_by;

--changeset aleksokol3:4 dbms:postgresql
--preconditions onFail:HALT onError:HALT
-- Change description: (T-001) add modified_by column
-- AUTHOR: Aleksey Sokolov
-- Date: 2025.11.13
-- Versioning strategy: Semantic Versioning
ALTER TABLE IF EXISTS employee
    ADD COLUMN IF NOT EXISTS modified_by VARCHAR(256);
-- Rollback ALTER TABLE employee DROP COLUMN IF EXISTS modified_by;

--changeset aleksokol3:5 dbms:postgresql
--preconditions onFail:HALT onError:HALT
-- Change description: (T-001) add version column
-- AUTHOR: Aleksey Sokolov
-- Date: 2025.11.13
-- Versioning strategy: Semantic Versioning
ALTER TABLE IF EXISTS employee
    ADD COLUMN IF NOT EXISTS version BIGINT;
-- Rollback ALTER TABLE employee DROP COLUMN IF EXISTS version;