DROP TABLE if EXISTS user_jpa_entity CASCADE;
DROP TABLE if EXISTS user_category;

CREATE TABLE user_jpa_entity
(
    id            BIGINT NOT NULL AUTO_INCREMENT,
    nickname      VARCHAR(10)  DEFAULT NULL,
    password      VARCHAR(15)  DEFAULT NULL,
    email         VARCHAR(55)  DEFAULT NULL,
    image_url     VARCHAR(255) DEFAULT NULL,
    provider      VARCHAR(10)  DEFAULT NULL,
    provider_type VARCHAR(10)  DEFAULT NULL,
    age           INT          DEFAULT NULL,
    gender        VARCHAR(6)   DEFAULT NULL,
    mbti          VARCHAR(4)   DEFAULT NULL,
    provider_id   VARCHAR(255) DEFAULT NULL,
    created_date  TIMESTAMP    DEFAULT NULL,
    modified_date TIMESTAMP    DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE user_category
(
    user_id  BIGINT       NOT NULL,
    category VARCHAR(255) NOT NULL
);

