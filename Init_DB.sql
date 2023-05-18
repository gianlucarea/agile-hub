DROP DATABASE IF EXISTS agile_hub_production;
CREATE DATABASE agile_hub_production;
USE agile_hub_production;

CREATE TABLE `Users`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `surname` varchar(45) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  `username` varchar(45) NOT NULL,
  `dateOfBirth` varchar(45) NOT NULL,
  `age` int NOT NULL,
  `type` enum('NORMALE','ADMIN','SOCIO','SOCIO_PLUS','MAESTRO') NOT NULL,
  `sport` enum('CALCETTO','PALLAVOLO','NUOTO','TENNIS','PADEL') ,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_user_UNIQUE` (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Teacher_Booking`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int Not Null,
  `teacher_id` int Not Null,
  `dayOfBooking` varchar(45) NOT NULL,
  `sport` enum('CALCETTO','PALLAVOLO','NUOTO','TENNIS','PADEL') ,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_teacher_booking_UNIQUE` (`id`),
  INDEX par_ind (user_id),  
  CONSTRAINT fk_user FOREIGN KEY (user_id)
  REFERENCES Users(id),
  CONSTRAINT fk_teacher FOREIGN KEY (teacher_id)  
  REFERENCES Users(id)  
  ON DELETE CASCADE  
  ON UPDATE CASCADE  

) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `agile_hub_production`.`Users`
(
`name`,
`surname`,
`password`,
`username`,
`dateOfBirth`,
`age`,
`type`)
VALUES
(
'Gianluca',
'Rea',
'cGFzc3dvcmQ=',
'aldino',
'26/09/1997',
25,
'NORMALE');

INSERT INTO `agile_hub_production`.`Users`
(
`name`,
`surname`,
`password`,
`username`,
`dateOfBirth`,
`age`,
`type`,
`sport`)
VALUES
(
'Francesco',
'Falone',
'cGFzc3dvcmQx',
'falone',
'26/09/1997',
25,
'ADMIN',
'PADEL');

INSERT INTO `agile_hub_production`.`Users`
(
`name`,
`surname`,
`password`,
`username`,
`dateOfBirth`,
`age`,
`type`)
VALUES
(
'Mario',
'Rossi',
'cGFzc3dvcmQ=',
'mrossi',
'26/10/1994',
28,
'SOCIO');


INSERT INTO `agile_hub_production`.`Users`
(
`name`,
`surname`,
`password`,
`username`,
`dateOfBirth`,
`age`,
`type`)
VALUES
(
'Luigi',
'Rossi',
'cGFzc3dvcmQ=',
'lrossi',
'26/10/1994',
28,
'SOCIO_PLUS');


INSERT INTO `agile_hub_production`.`Users`
(
`name`,
`surname`,
`password`,
`username`,
`dateOfBirth`,
`age`,
`type`,
`sport`)
VALUES
(
'Matteo',
'Teach',
'cGFzc3dvcmQ=',
'teach',
'22/01/1995',
28,
'MAESTRO',
'NUOTO');

INSERT INTO `agile_hub_production`.`Teacher_Booking`
(`user_id`,
`teacher_id`,
`dayOfBooking`,
`sport`)
VALUES
(
7,
8,
'30/05/2023',
'Nuoto');

INSERT INTO `agile_hub_production`.`Teacher_Booking`
(`user_id`,
`teacher_id`,
`dayOfBooking`,
`sport`)
VALUES
(
6,
8,
'25/05/2023',
'Nuoto');

