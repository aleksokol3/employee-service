--liquibase formatted sql

--changeset aleksokol3:1 dbms:postgresql
--preconditions onFail:HALT onError:HALT

-- Change description: create table employee
-- AUTHOR: Aleksey Sokolov
-- Date: 2025.11.03
-- Versioning strategy: Semantic Versioning
CREATE TABLE employee
(
    id          VARCHAR(36) PRIMARY KEY,
    first_name  VARCHAR(256)             NOT NULL,
    last_name   VARCHAR(256)             NOT NULL,
    age         INT                      NOT NULL CHECK ( age > 0 ),
    salary      DECIMAL(19, 4)           NOT NULL CHECK ( salary >= 0 ),
    hiring_date TIMESTAMP WITH TIME ZONE NOT NULL
);
--rollback DROP TABLE IF EXISTS employee
