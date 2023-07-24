CREATE TABLE IF NOT EXISTS transaction_types
(
    id               BIGINT(10) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    transaction_type VARCHAR(255)           NOT NULL,
    transaction_fee  DOUBLE(10, 2)          NOT NULL DEFAULT 0.0,
    transaction_tax  DECIMAL                NOT NULL DEFAULT 0.0,
    created_at       TIMESTAMP                       DEFAULT NOW(),
    updated_at       TIMESTAMP              NULL
);