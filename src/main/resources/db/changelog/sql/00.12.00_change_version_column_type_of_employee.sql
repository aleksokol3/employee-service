--liquibase formatted sql

--changeset aleksokol3:1 dbms:postgresql
--preconditions onFail:HALT onError:HALT
-- Change description: (T-004) change version column of employee table from bigint to int
-- AUTHOR: Aleksey Sokolov
-- Date: 2025.12.06
-- Versioning strategy: Semantic Versioning
ALTER TABLE IF EXISTS employee
    ALTER COLUMN version TYPE INT