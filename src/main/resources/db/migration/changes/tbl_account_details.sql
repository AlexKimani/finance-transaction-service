CREATE TABLE IF NOT EXISTS account_details
(
    id                BIGINT(10) PRIMARY KEY AUTO_INCREMENT NOT NULL,
    user_id           BIGINT(10)                            NOT NULL,
    account_type_id   BIGINT(10)                            NOT NULL,
    actual_balance    DOUBLE(10, 2) DEFAULT 0.0,
    available_balance DOUBLE(10, 2) DEFAULT 0.0,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP NULL,
    FOREIGN KEY (user_id) REFERENCES user_details(id),
    FOREIGN KEY (account_type_id) REFERENCES account_configs(id)
);