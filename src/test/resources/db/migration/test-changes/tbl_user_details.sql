CREATE TABLE user_details
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    surname       VARCHAR(100) NOT NULL,
    other_names   VARCHAR(255) NOT NULL,
    id_number     BIGINT NOT NULL,
    phone_number  BIGINT NOT NULL,
    user_identity VARCHAR(255) NOT NULL,
    created_at    TIMESTAMP DEFAULT NOW(),
    updated_at    TIMESTAMP NULL
);