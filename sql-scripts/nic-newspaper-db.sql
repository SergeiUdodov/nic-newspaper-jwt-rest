DROP DATABASE  IF EXISTS `nic-newspaper-bd`;

CREATE DATABASE  IF NOT EXISTS `nic-newspaper-bd`;
USE `nic-newspaper-bd`;

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` char(80) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `user` (first_name,last_name,email,password)
VALUES 
('John','Doe','john@luv2code.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K'),
('Mary','Public','mary@luv2code.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K'),
('Susan','Adams','susan@luv2code.com', '$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K');


DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `role` (name)
VALUES 
('ROLE_USER'),
('ROLE_ADMIN');


DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  
  PRIMARY KEY (`user_id`,`role_id`),
  
  KEY `FK_ROLE_idx` (`role_id`),
  
  CONSTRAINT `FK_USER_05` FOREIGN KEY (`user_id`) 
  REFERENCES `user` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_ROLE` FOREIGN KEY (`role_id`) 
  REFERENCES `role` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `users_roles` (user_id,role_id)
VALUES 
(1, 1),
(2, 1),
(3, 1),
(3, 2);


DROP TABLE IF EXISTS `article`;

CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `header` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `article` (header,content,date)
VALUES 
('SpaceX wins Sentinel 6B radar satellite launch contract', 

'SpaceX has won a contract to launch the joint US-European Sentinel 6B radar satellite as early as November 2025.

Five years ago, NASA also chose SpaceX to launch Sentinel 6A, the first of two identical satellites designed to use radar altimeters to determine global sea levels more accurately than ever before. In October 2017, just half a year after SpaceX’s first Falcon 9 rocket booster reuse and well before the cost savings that followed were fully factored in, NASA awarded SpaceX $94 million to launch the 1.1-ton (~2500 lb) to a relatively low 1300-kilometer (~810 mi) orbit.

Five years and two months later, NASA has awarded SpaceX $97 million to launch a virtually identical satellite to the same orbit, from the same launch pad, with the same rocket. SpaceX, however, is far from the same company it was in 2017, and has effectively mastered Falcon booster and payload fairing reuse in the half-decade since.
    — NASA\'s Launch Services Program (@NASA_LSP) December 20, 2022',
current_time()),

('The World\s Largest Pizza Ever Weighed 26,883 lbs',
'According the keepers of human history over at the Guinness World Records, the largest circular pizza ever baked weighed was made in Norwood, South Africa by Norwood Hypermarket on December 8, 1990. It weighed 26,883 pounds.

The data is a bit sketchy, but here are relevant numbers: The pizza measured 122 feet, 8 inches in diameter, weighed 26,883 pounds, and contained 9,920 pounds of flour, 3,960 pounds of cheese, 1 763 pounds of mushrooms, 1,984 pounds of tomato puree, and 1,984 pounds of chopped tomatoes.',
current_time());




DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `article_id` int(11) NOT NULL,
  `text` varchar(1000) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  
  CONSTRAINT `FK_USER` FOREIGN KEY (`user_id`) 
  REFERENCES `user` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_ARTICLE` FOREIGN KEY (`article_id`) 
  REFERENCES `article` (`id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
  
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `comment` (user_id,article_id,text,date)
VALUES 
(1,1,'Greate article!', current_time()),
(2,1,'God bless America', current_time()),
(3,2,'I love big pizza', current_time()),
(1,2,'Greate pizza, greate article, greate me!', current_time());

