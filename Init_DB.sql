DROP DATABASE IF EXISTS agile_hub_test;
CREATE DATABASE agile_hub_test;
USE agile_hub_test;

CREATE TABLE `Users`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `surname` varchar(45) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  `username` varchar(45) NOT NULL,
  `age` int NOT NULL,
  `type` enum('NORMALE','ADMIN','SOCIO','SOCIO_PLUS','MAESTRO') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_user_UNIQUE` (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `agile_hub_test`.`Users`
(
`name`,
`surname`,
`password`,
`username`,
`age`,
`type`)
VALUES
(
'Gianluca',
'Rea',
'cGFzc3dvcmQ=',
'aldino',
25,
'NORMALE');