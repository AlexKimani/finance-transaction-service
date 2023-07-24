CREATE TABLE IF NOT EXISTS account_configs
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    account_type  VARCHAR(255)                          NOT NULL,
    account_minimum_limit DOUBLE PRECISION        NOT NULL DEFAULT 0.0,
    account_maximum_limit DOUBLE PRECISION        NOT NULL DEFAULT 0.0,
    created_at    TIMESTAMP                                      DEFAULT NOW(),
    updated_at    TIMESTAMP                             NULL
);