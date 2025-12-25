--liquibase formatted sql

--changeset aleksokol3:1 dbms:postgresql
--preconditions onFail:HALT onError:HALT
-- Change description: (T-003) add auditing and versioning columns to employee table (created_at, modified_at, created_by, modified_by, version)
-- AUTHOR: Aleksey Sokolov
-- Date: 2025.11.13
-- Versioning strategy: Semantic Versioning
ALTER TABLE IF EXISTS employee
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMP WITH TIME ZONE;
ALTER TABLE IF EXISTS employee
    ADD COLUMN IF NOT EXISTS modified_at TIMESTAMP WITH TIME ZONE;
ALTER TABLE IF EXISTS employee
    ADD COLUMN IF NOT EXISTS created_by VARCHAR(256);
ALTER TABLE IF EXISTS employee
    ADD COLUMN IF NOT EXISTS modified_by VARCHAR(256);
ALTER TABLE IF EXISTS employee
    ADD COLUMN IF NOT EXISTS version BIGINT;
-- Rollback ALTER TABLE employee DROP COLUMN IF EXISTS created_at, DROP COLUMN IF EXISTS modified_at, DROP COLUMN IF EXISTS created_by, DROP COLUMN IF EXISTS modified_by, DROP COLUMN IF EXISTS version;