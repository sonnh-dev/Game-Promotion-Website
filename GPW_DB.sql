CREATE DATABASE IF NOT EXISTS `GPW_DB`;
USE `GPW_DB`;

-- =====================
-- Table: users (thay admins)
-- =====================
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`
(
    `id`         INT AUTO_INCREMENT,
    `username`   VARCHAR(100) NOT NULL,
    `password`   VARCHAR(255) NOT NULL,
    `role`       VARCHAR(50)  NOT NULL,
    `created_at` DATETIME     NOT NULL,
    `email`      VARCHAR(45)  NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE (`username`)
) ENGINE = InnoDB;

-- =====================
-- Table: news
-- =====================
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news`
(
    `id`         INT AUTO_INCREMENT,
    `title`      VARCHAR(255) NOT NULL,
    `content`    TEXT         NOT NULL,
    `created_at` DATETIME     NOT NULL,
    `viewed`     INT          NOT NULL DEFAULT 0,
    `user_id`    INT,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_news_user`
        FOREIGN KEY (`user_id`)
            REFERENCES `users` (`id`)
            ON DELETE SET NULL
            ON UPDATE CASCADE
) ENGINE = InnoDB;

-- =====================
-- Table: media
-- =====================
DROP TABLE IF EXISTS `media`;
CREATE TABLE `media`
(
    `id`          INT AUTO_INCREMENT,
    `url`         VARCHAR(255) NOT NULL,
    `type`        VARCHAR(50)  NOT NULL,
    `entity_type` VARCHAR(50)  NOT NULL,
    `entity_id`   INT          NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

-- =====================
-- Table: character
-- =====================
DROP TABLE IF EXISTS `character`;
CREATE TABLE `character`
(
    `id`          INT          NOT NULL,
    `name`        VARCHAR(45)  NOT NULL,
    `media_id`    INT          NOT NULL,
    `description` TEXT         NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_character_media`
        FOREIGN KEY (`media_id`)
            REFERENCES `media` (`id`)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
) ENGINE = InnoDB;

-- =====================
-- Table: newsletter
-- =====================
DROP TABLE IF EXISTS `newsletter`;
CREATE TABLE `newsletter`
(
    `id`         INT AUTO_INCREMENT,
    `email`      VARCHAR(255) NOT NULL,
    `created_at` DATETIME     NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE (`email`)
) ENGINE = InnoDB;
