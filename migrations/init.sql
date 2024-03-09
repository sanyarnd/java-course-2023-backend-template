CREATE TABLE IF NOT EXISTS telegram_chat
(
    id            BIGINT,
    registered_at TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS link
(
    id              BIGINT GENERATED ALWAYS AS IDENTITY,
    url             VARCHAR(2048) UNIQUE NOT NULL,
    last_updated_at TIMESTAMP WITH TIME ZONE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS chat_to_link_binding
(
    chat_id BIGINT REFERENCES telegram_chat (id) ON DELETE CASCADE,
    link_id BIGINT REFERENCES link (id) ON DELETE CASCADE,
    PRIMARY KEY (chat_id, link_id)
);
