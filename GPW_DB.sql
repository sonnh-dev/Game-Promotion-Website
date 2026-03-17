CREATE DATABASE IF NOT EXISTS `GPW_DB`;
USE `GPW_DB`;

-- =====================
-- Table: admins
-- =====================
DROP TABLE IF EXISTS `admins`;
CREATE TABLE `admins` (
  `id` INT AUTO_INCREMENT,
  `username` VARCHAR(100) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `role` VARCHAR(50) NOT NULL,
  `created_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE (`username`)
) ENGINE=InnoDB;

-- =====================
-- Table: news
-- =====================
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `id` INT AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `content` TEXT NOT NULL,
  `created_at` DATETIME NOT NULL,
  `viewed` INT NOT NULL,
  `admin_id` INT,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_news_admin`
    FOREIGN KEY (`admin_id`)
    REFERENCES `admins`(`id`)
) ENGINE=InnoDB;

-- =====================
-- Table: media
-- =====================
DROP TABLE IF EXISTS `media`;
CREATE TABLE `media` (
  `id` INT AUTO_INCREMENT,
  `url` VARCHAR(255) NOT NULL,
  `type` VARCHAR(50) NOT NULL,
  `entity_type` VARCHAR(50) NOT NULL,
  `entity_id` INT NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

-- =====================
-- Table: newsletter
-- =====================
DROP TABLE IF EXISTS `newsletter`;
CREATE TABLE `newsletter` (
  `id` INT AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `created_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE (`email`)
) ENGINE=InnoDB;