--
-- Table `ic_system_option`
--
DROP TABLE IF EXISTS `ic_system_option`;
CREATE TABLE `ic_system_option` (
  `option_key` varchar(64) NOT NULL,
  `option_value` text NOT NULL,
  CONSTRAINT `pk_system_option` PRIMARY KEY (`option_key`)
) ENGINE=InnoDB;

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
-- Table `ic_source_repository`
--
DROP TABLE IF EXISTS `ic_source_repository`;
CREATE TABLE `ic_source_repository` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `repository_type` varchar(16) NOT NULL,
  `path` varchar(128) NOT NULL,
  `modules` text NOT NULL,
  `last_change_date` datetime,
  CONSTRAINT `pk_source_repository` PRIMARY KEY (`id`)
) ENGINE=InnoDB;

--
-- Table `ic_project`
--
DROP TABLE IF EXISTS `ic_project`;
CREATE TABLE `ic_project` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `description` text,
  `source_repository_id` int unsigned,
  CONSTRAINT `pk_project` PRIMARY KEY (`id`),
  CONSTRAINT `fk_project_source_repository` FOREIGN KEY (`source_repository_id`) REFERENCES `ic_source_repository` (`id`)
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
-- Table `ic_milestone`
--
DROP TABLE IF EXISTS `ic_milestone`;
CREATE TABLE `ic_milestone` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `project_id` int unsigned NOT NULL,
  `name` varchar(128) NOT NULL,
  `due_date` datetime NOT NULL,
  `completed` bit NOT NULL DEFAULT b'0',
  CONSTRAINT `pk_milestone` PRIMARY KEY (`id`),
  CONSTRAINT `fk_milestone_project` FOREIGN KEY (`project_id`) REFERENCES `ic_project` (`id`) ON DELETE CASCADE
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
  `milestone_id` int unsigned,
  `priority` tinyint unsigned NOT NULL,
  `status` tinyint unsigned NOT NULL,
  `assigned_to_id` int unsigned,
  `created` datetime NOT NULL,
  `created_by` varchar(64) NOT NULL,
  CONSTRAINT `pk_ticket` PRIMARY KEY (`id`),
  CONSTRAINT `fk_ticket_project` FOREIGN KEY (`project_id`) REFERENCES `ic_project` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_ticket_user` FOREIGN KEY (`assigned_to_id`) REFERENCES `ic_user` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_ticket_component` FOREIGN KEY (`component_id`) REFERENCES `ic_component` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_ticket_milestone` FOREIGN KEY (`milestone_id`) REFERENCES `ic_milestone` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB;

--
-- Table `ic_ticket_change`
--
DROP TABLE IF EXISTS `ic_ticket_change`;
CREATE TABLE `ic_ticket_change` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `ticket_id` int unsigned NOT NULL,
  `username` varchar(64) NOT NULL,
  `change_date` datetime NOT NULL,
  CONSTRAINT `pk_ticket_change` PRIMARY KEY (`id`),
  CONSTRAINT `fk_ticket_change_ticket` FOREIGN KEY (`ticket_id`) REFERENCES `ic_ticket` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB;

--
-- Table `ic_ticket_change_field`
--
DROP TABLE IF EXISTS `ic_ticket_change_field`;
CREATE TABLE `ic_ticket_change_field` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `ticket_change_id` int unsigned NOT NULL,
  `field` varchar(64) NOT NULL,
  `old_value` text NULL,
  `new_value` text NULL,
  CONSTRAINT `pk_ticket_change_field` PRIMARY KEY (`id`),
  CONSTRAINT `fk_ticket_change_field_ticket_change` FOREIGN KEY (`ticket_change_id`) REFERENCES `ic_ticket_change` (`id`) ON DELETE CASCADE
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
-- Table `ic_repository_change`
--
DROP TABLE IF EXISTS `ic_repository_change`;
CREATE TABLE `ic_repository_change` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `project_id` int unsigned NOT NULL,
  `username` varchar(64) NOT NULL,
  `change_date` datetime NOT NULL,
  `comment` text NOT NULL,
  CONSTRAINT `pk_repository_change` PRIMARY KEY (`id`),
  CONSTRAINT `fk_repository_change_project` FOREIGN KEY (`project_id`) REFERENCES `ic_project` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB;

--
-- Table `ic_repository_change_file`
--
DROP TABLE IF EXISTS `ic_repository_change_file`;
CREATE TABLE `ic_repository_change_file` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `repository_change_id` int unsigned NOT NULL,
  `filename` text NOT NULL,
  `revision` varchar(16) NOT NULL,
  CONSTRAINT `pk_repository_change_file` PRIMARY KEY (`id`),
  CONSTRAINT `fk_repository_change_file_repository_change` FOREIGN KEY (`repository_change_id`) REFERENCES `ic_repository_change` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB;

--
-- Table `ic_repository_change_ticket`
--
DROP TABLE IF EXISTS `ic_repository_change_ticket`;
CREATE TABLE `ic_repository_change_ticket` (
  `repository_change_id` int unsigned NOT NULL,
  `ticket_id` int unsigned NOT NULL,
  CONSTRAINT `pk_repository_change_ticket` PRIMARY KEY (`repository_change_id`, `ticket_id`),
  CONSTRAINT `fk_repository_change_ticket_repository_change` FOREIGN KEY (`repository_change_id`) REFERENCES `ic_repository_change` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_repository_change_ticket_ticket` FOREIGN KEY (`ticket_id`) REFERENCES `ic_ticket` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB;

--
-- Data
--
INSERT INTO `ic_user` (`username`,`password`, `email_address`, `administrator`)
VALUES ('admin', 'a43997d1a9b7d28fb5abd020833f1fd1', 'admin@localhost', b'1');
