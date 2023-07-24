CREATE TABLE IF NOT EXISTS transactions
(
    id                       BIGINT(10) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    account_id               BIGINT(10)             NOT NULL,
    transaction_type_id      BIGINT(10)             NOT NULL,
    destination_account      BIGINT(10)             NOT NULL,
    transaction_amount       DOUBLE(10, 2)          NOT NULL DEFAULT 0.0,
    transaction_tax          DOUBLE(10, 2)                   DEFAULT 0.0,
    transaction_reference_id VARCHAR(255)           NOT NULL,
    transaction_receipt      VARCHAR(255)           NOT NULL,
    created_at               TIMESTAMP                       DEFAULT NOW(),
    updated_at               TIMESTAMP              NULL,
    FOREIGN KEY (transaction_type_id) REFERENCES transaction_types(id),
    FOREIGN KEY (account_id) REFERENCES account_details(id)
);