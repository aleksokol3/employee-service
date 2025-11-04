--liquibase formatted sql

--changeset aleksokol3:1 dbms:postgresql
--preconditions onFail:HALT onError:HALT

-- Change description: create table employee
-- AUTHOR: Aleksey Sokolov
-- Date: 2025.11.04
-- Versioning strategy: Semantic Versioning
ALTER TABLE employee
    ADD COLUMN patronymic VARCHAR(256);
ALTER TABLE employee
ALTER COLUMN hiring_date TYPE DATE
--rollback ALTER TABLE employee DROP COLUMN IF EXISTS patronymic;
