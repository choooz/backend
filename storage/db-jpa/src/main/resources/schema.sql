DROP TABLE if EXISTS user_jpa_entity CASCADE;
DROP TABLE if EXISTS user_category;

CREATE TABLE user_jpa_entity
(
    user_id       bigint AUTO_INCREMENT,
    nickname      varchar(10)  DEFAULT NULL,
    password      varchar(15)  DEFAULT NULL,
    email         varchar(55)  DEFAULT NULL,
    image_url     varchar(255) DEFAULT NULL,
    provider      varchar(10)  DEFAULT NULL,
    provider_type varchar(10)  DEFAULT NULL,
    age           int          DEFAULT NULL,
    gender        varchar(6)   DEFAULT NULL,
    mbti          varchar(4)   DEFAULT NULL,
    provider_id   varchar(255) DEFAULT NULL,
    created_date  timestamp    DEFAULT NULL,
    modified_date timestamp    DEFAULT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE user_category
(
    `user_id`  BIGINT      NOT NULL,
    `category` VARCHAR(50) NOT NULL
);

