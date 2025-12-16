--liquibase formatted sql

--changeset aleksokol3:1 dbms:postgresql
--preconditions onFail:HALT onError:HALT
-- Change description: (T-005) enable similarity search - create pg_trgm extension and index for the required fields in the employee table
-- AUTHOR: Aleksey Sokolov
-- Date: 2025.12.16
-- Versioning strategy: Semantic Versioning
CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE INDEX trgm_idx_employee
    ON employee
        USING GIN (
                   last_name gin_trgm_ops,
                   first_name gin_trgm_ops,
                   patronymic gin_trgm_ops
            );