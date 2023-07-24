CREATE TABLE IF NOT EXISTS transactions
(
    id                       BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    account_id               BIGINT             NOT NULL,
    transaction_type_id      BIGINT             NOT NULL,
    destination_account      BIGINT             NOT NULL,
    transaction_amount       DOUBLE PRECISION          NOT NULL DEFAULT 0.0,
    transaction_tax          DOUBLE PRECISION                  DEFAULT 0.0,
    transaction_reference_id VARCHAR(255)           NOT NULL,
    transaction_receipt      VARCHAR(255)           NOT NULL,
    created_at               TIMESTAMP                       DEFAULT NOW(),
    updated_at               TIMESTAMP              NULL,
    FOREIGN KEY (transaction_type_id) REFERENCES transaction_types(id),
    FOREIGN KEY (account_id) REFERENCES account_details(id)
);