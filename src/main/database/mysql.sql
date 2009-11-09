--
-- Table `ic_user`
--
DROP TABLE IF EXISTS `ic_user`;
CREATE TABLE `ic_user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL,
  `password` varchar(32) NOT NULL,
  `email_address` varchar(128) NOT NULL,
  `administrator` bit NOT NULL DEFAULT b'0',
  CONSTRAINT `pk_user` PRIMARY KEY (`id`)
) ENGINE=InnoDB;

--
-- Table `ic_project`
--
DROP TABLE IF EXISTS `ic_project`;
CREATE TABLE `ic_project` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `description` text,
  CONSTRAINT `pk_project` PRIMARY KEY (`id`)
) ENGINE=InnoDB;

--
-- Table `ic_component`
--
DROP TABLE IF EXISTS `ic_component`;
CREATE TABLE `ic_component` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `project_id` int unsigned NOT NULL,
  `name` varchar(128) NOT NULL,
  CONSTRAINT `pk_component` PRIMARY KEY (`id`),
  CONSTRAINT `fk_component_project` FOREIGN KEY (`project_id`) REFERENCES `ic_project` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB;

--
-- Table `ic_ticket`
--
DROP TABLE IF EXISTS `ic_ticket`;
CREATE TABLE `ic_ticket` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `project_id` int unsigned NOT NULL,
  `name` varchar(128) NOT NULL,
  `description` text,
  `component_id` int unsigned,
  `priority` tinyint unsigned NOT NULL,
  `status` tinyint unsigned NOT NULL,
  `assigned_to_id` int unsigned,
  `created` datetime NOT NULL,
  `created_by` varchar(64) NOT NULL,
  CONSTRAINT `pk_ticket` PRIMARY KEY (`id`),
  CONSTRAINT `fk_ticket_project` FOREIGN KEY (`project_id`) REFERENCES `ic_project` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_ticket_user` FOREIGN KEY (`assigned_to_id`) REFERENCES `ic_user` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_ticket_component` FOREIGN KEY (`component_id`) REFERENCES `ic_component` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB;

--
-- Table `ic_comment`
--
DROP TABLE IF EXISTS `ic_comment`;
CREATE TABLE `ic_comment` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `ticket_id` int unsigned NOT NULL,
  `content` text NOT NULL,
  `created` datetime NOT NULL,
  `created_by` varchar(64) NOT NULL,
  CONSTRAINT `pk_comment` PRIMARY KEY (`id`),
  CONSTRAINT `fk_comment_ticket` FOREIGN KEY (`ticket_id`) REFERENCES `ic_ticket` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB;

--
-- Table `ic_attachment`
--
DROP TABLE IF EXISTS `ic_attachment`;
CREATE TABLE `ic_attachment` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `ticket_id` int unsigned NOT NULL,
  `file_name` varchar(128) NOT NULL,
  `description` text,
  `file_size` int unsigned NOT NULL,
  `content_type` varchar(128) NOT NULL,
  `content` longblob NOT NULL,
  `created` datetime NOT NULL,
  `created_by` varchar(64) NOT NULL,
  CONSTRAINT `pk_attachment` PRIMARY KEY (`id`),
  CONSTRAINT `fk_attachment_ticket` FOREIGN KEY (`ticket_id`) REFERENCES `ic_ticket` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB;

--
-- Data
--
INSERT INTO `ic_user` (`username`,`password`, `email_address`, `administrator`)
VALUES ('admin', 'a43997d1a9b7d28fb5abd020833f1fd1', 'admin@localhost', b'1');
