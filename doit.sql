-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema doit
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema doit
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `doit` DEFAULT CHARACTER SET utf8 ;
USE `doit` ;

-- -----------------------------------------------------
-- Table `doit`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `doit`.`user` (
  `user_pk` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(50) NOT NULL,
  `password` TEXT NOT NULL,
  `nickname` VARCHAR(45) NOT NULL,
  `mileage` INT NULL DEFAULT 0,
  `user_role` VARCHAR(50) NULL,
  `auth_key` VARCHAR(20) NULL,
  `feed_open` VARCHAR(20) NULL,
  `group_open` VARCHAR(20) NULL,
  PRIMARY KEY (`user_pk`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `nickname_UNIQUE` (`nickname` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `doit`.`group`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `doit`.`group` (
  `group_pk` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `content` TEXT NULL,
  `score` INT NULL,
  `total_num` INT NULL,
  `max_num` INT NOT NULL,
  `leader` INT NOT NULL,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  `status` VARCHAR(45) NULL,
  PRIMARY KEY (`group_pk`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `doit`.`user_1`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `doit`.`user_1` (
  `username` VARCHAR(16) NOT NULL,
  `email` VARCHAR(255) NULL,
  `password` VARCHAR(32) NOT NULL,
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP);


-- -----------------------------------------------------
-- Table `doit`.`group_has_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `doit`.`group_has_user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `group_pk` INT NOT NULL,
  `user_pk` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_group_has_user_user1_idx` (`user_pk` ASC) VISIBLE,
  INDEX `fk_group_has_user_group1_idx` (`group_pk` ASC) VISIBLE,
  CONSTRAINT `fk_group_has_user_group1`
    FOREIGN KEY (`group_pk`)
    REFERENCES `doit`.`group` (`group_pk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_group_has_user_user1`
    FOREIGN KEY (`user_pk`)
    REFERENCES `doit`.`user` (`user_pk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `doit`.`feed`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `doit`.`feed` (
  `feed_pk` INT NOT NULL AUTO_INCREMENT,
  `media` BLOB NULL,
  `content` TEXT NULL,
  `auth_date` DATETIME NULL,
  `auth_cnt` INT NULL DEFAULT 0,
  `auth_check` VARCHAR(45) NULL DEFAULT 'false',
  `create_date` DATETIME NULL DEFAULT current_timestamp(),
  `update_date` DATETIME NULL,
  `feed_type` VARCHAR(20) NULL,
  `status` VARCHAR(45) NULL,
  `group_pk` INT NOT NULL,
  `user_pk` INT NOT NULL,
  PRIMARY KEY (`feed_pk`),
  INDEX `fk_feed_group1_idx` (`group_pk` ASC) VISIBLE,
  INDEX `fk_feed_user1_idx` (`user_pk` ASC) VISIBLE,
  CONSTRAINT `fk_feed_group1`
    FOREIGN KEY (`group_pk`)
    REFERENCES `doit`.`group` (`group_pk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_feed_user1`
    FOREIGN KEY (`user_pk`)
    REFERENCES `doit`.`user` (`user_pk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `doit`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `doit`.`comment` (
  `comment_pk` INT NOT NULL AUTO_INCREMENT,
  `content` MEDIUMTEXT NULL,
  `feed_pk` INT NOT NULL,
  `user_pk` INT NOT NULL,
  PRIMARY KEY (`comment_pk`),
  INDEX `fk_comment_user1_idx` (`user_pk` ASC) VISIBLE,
  CONSTRAINT `fk_comment_user1`
    FOREIGN KEY (`user_pk`)
    REFERENCES `doit`.`user` (`user_pk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `doit`.`hashtag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `doit`.`hashtag` (
  `tag_pk` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NULL,
  `cnt` INT NULL,
  PRIMARY KEY (`tag_pk`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `doit`.`group_has_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `doit`.`group_has_tag` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `group_pk` INT NOT NULL,
  `tag_pk` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_group_has_tag_tag1_idx` (`tag_pk` ASC) VISIBLE,
  INDEX `fk_group_has_tag_group1_idx` (`group_pk` ASC) VISIBLE,
  CONSTRAINT `fk_group_has_tag_group1`
    FOREIGN KEY (`group_pk`)
    REFERENCES `doit`.`group` (`group_pk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_group_has_tag_tag1`
    FOREIGN KEY (`tag_pk`)
    REFERENCES `doit`.`hashtag` (`tag_pk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `doit`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `doit`.`product` (
  `product_pk` INT NOT NULL AUTO_INCREMENT,
  `user_pk` INT NOT NULL,
  `category` VARCHAR(45) NULL,
  `title` VARCHAR(45) NULL,
  `content` LONGTEXT NULL,
  `image` BLOB NULL,
  PRIMARY KEY (`product_pk`),
  INDEX `fk_product_user1_idx` (`user_pk` ASC) VISIBLE,
  CONSTRAINT `fk_product_user1`
    FOREIGN KEY (`user_pk`)
    REFERENCES `doit`.`user` (`user_pk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `doit`.`commit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `doit`.`commit` (
  `commit_pk` INT NOT NULL,
  `date` DATETIME NULL,
  `user_pk` INT NOT NULL,
  `group_pk` INT NOT NULL,
  PRIMARY KEY (`commit_pk`),
  INDEX `fk_commit_user1_idx` (`user_pk` ASC) VISIBLE,
  INDEX `fk_commit_group1_idx` (`group_pk` ASC) VISIBLE,
  CONSTRAINT `fk_commit_user1`
    FOREIGN KEY (`user_pk`)
    REFERENCES `doit`.`user` (`user_pk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_commit_group1`
    FOREIGN KEY (`group_pk`)
    REFERENCES `doit`.`group` (`group_pk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `doit`.`feed_has_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `doit`.`feed_has_user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `feed_pk` INT NOT NULL,
  `user_pk` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_feed_has_user_user1_idx` (`user_pk` ASC) VISIBLE,
  INDEX `fk_feed_has_user_feed1_idx` (`feed_pk` ASC) VISIBLE,
  CONSTRAINT `fk_feed_has_user_feed1`
    FOREIGN KEY (`feed_pk`)
    REFERENCES `doit`.`feed` (`feed_pk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_feed_has_user_user1`
    FOREIGN KEY (`user_pk`)
    REFERENCES `doit`.`user` (`user_pk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `doit`.`mileage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `doit`.`mileage` (
  `mileage_pk` INT NOT NULL AUTO_INCREMENT,
  `content` TEXT NULL,
  `date` DATETIME NULL,
  `user_pk` INT NOT NULL,
  PRIMARY KEY (`mileage_pk`),
  INDEX `fk_mileage_user1_idx` (`user_pk` ASC) VISIBLE,
  CONSTRAINT `fk_mileage_user1`
    FOREIGN KEY (`user_pk`)
    REFERENCES `doit`.`user` (`user_pk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `doit`.`profile`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `doit`.`profile` (
  `profile_pk` INT NOT NULL AUTO_INCREMENT,
  `file_name` VARCHAR(200) NULL,
  `file_oriname` VARCHAR(200) NULL,
  `fileUrl` VARCHAR(200) NULL,
  `user_pk` INT NOT NULL,
  PRIMARY KEY (`profile_pk`),
  INDEX `fk_profile_user1_idx` (`user_pk` ASC) VISIBLE,
  CONSTRAINT `fk_profile_user1`
    FOREIGN KEY (`user_pk`)
    REFERENCES `doit`.`user` (`user_pk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
