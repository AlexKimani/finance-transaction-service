CREATE TABLE IF NOT EXISTS user_details
(
    id            BIGINT(10) PRIMARY KEY AUTO_INCREMENT NOT NULL,
    surname       VARCHAR(100)                          NOT NULL,
    other_names   VARCHAR(255)                          NOT NULL,
    id_number     BIGINT(15)                            NOT NULL,
    phone_number  BIGINT(15)                            NOT NULL,
    user_identity VARCHAR(255) UNIQUE                   NOT NULL,
    created_at    TIMESTAMP DEFAULT NOW(),
    updated_at    TIMESTAMP                             NULL
);