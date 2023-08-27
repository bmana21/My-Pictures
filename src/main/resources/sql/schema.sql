DROP DATABASE IF EXISTS pictures_db;
CREATE DATABASE pictures_db;

USE pictures_db;

-- Users
CREATE TABLE IF NOT EXISTS user
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    email        VARCHAR(255) NOT NULL,
    username     VARCHAR(255) NOT NULL,
    passwordHash VARCHAR(255) NOT NULL,
    firstname    VARCHAR(255) NOT NULL,
    surname      VARCHAR(255) NOT NULL,
    phoneNumber  VARCHAR(255)
);


-- Adding some testing data:
-- email: test@test.com     password: test
INSERT INTO user (email, username, passwordHash, firstname, surname, phoneNumber)
VALUES ('test@test.com', 'test', 'a94a8fe5ccb19ba61c4c0873d391e987982fbbd3', 'test_firstname',
        'test_surname' '0123456789', '2000-09-11');