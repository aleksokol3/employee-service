--liquibase formatted sql

--changeset aleksokol3:1 dbms:postgresql
--preconditions onFail:HALT onError:HALT

-- Change description: create table employee
-- AUTHOR: Aleksey Sokolov
-- Date: 2025.11.04
-- Versioning strategy: Semantic Versioning
ALTER TABLE IF EXISTS employee
    ADD COLUMN IF NOT EXISTS patronymic VARCHAR(256);
ALTER TABLE IF EXISTS employee
    ALTER COLUMN hiring_date TYPE DATE;
-- Rollback ALTER TABLE employee DROP COLUMN IF EXISTS patronymic;
