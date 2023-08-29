DROP DATABASE IF EXISTS pictures_db;
CREATE DATABASE pictures_db;

USE pictures_db;

-- Users
CREATE TABLE IF NOT EXISTS user
(
    userId       BIGINT PRIMARY KEY AUTO_INCREMENT,
    email        VARCHAR(255) NOT NULL,
    username     VARCHAR(255) NOT NULL,
    passwordHash VARCHAR(255) NOT NULL,
    firstname    VARCHAR(255) NOT NULL,
    surname      VARCHAR(255) NOT NULL,
    phoneNumber  VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS album
(
    userId   BIGINT,
    albumId  BIGINT PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(512) NOT NULL,
    saveName VARCHAR(512) NOT NULL,
    FOREIGN KEY (userId) REFERENCES user (userId)
);

CREATE TABLE IF NOT EXISTS photo
(
    userId   BIGINT,
    albumId  BIGINT,
    photoId  BIGINT PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(512) NOT NULL,
    saveName VARCHAR(512) NOT NULL,
    FOREIGN KEY (userId) REFERENCES user (userId),
    FOREIGN KEY (albumId) REFERENCES album (albumId)
);