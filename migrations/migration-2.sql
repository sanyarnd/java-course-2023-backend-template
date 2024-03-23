-- liquibase formatted sql

--changeset AlexCawl:1
CREATE TABLE IF NOT EXISTS link_content
(
    id   BIGINT REFERENCES link (id) ON DELETE CASCADE,
    raw  TEXT NOT NULL,
    hash INT,
    PRIMARY KEY (id)
);