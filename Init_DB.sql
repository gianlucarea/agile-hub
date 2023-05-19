DROP DATABASE IF EXISTS agile_hub_production;
CREATE DATABASE agile_hub_production;
USE agile_hub_production;
SET GLOBAL FOREIGN_KEY_CHECKS=0;
CREATE TABLE `Users`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `surname` varchar(45) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  `username` varchar(45) NOT NULL,
  `dateOfBirth` varchar(45) NOT NULL,
  `age` int NOT NULL,
  `type` enum('NORMALE','ADMIN','SOCIO','SOCIO_PLUS','MAESTRO') NOT NULL,
  `sport` enum('CALCETTO','PALLAVOLO','NUOTO','TENNIS','PADEL','BASKET') ,
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

CREATE TABLE `Booking`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `dateBooking` varchar(45) NOT NULL,
  `numberPlayers` int NOT NULL,
  `sport` enum('CALCETTO','PALLAVOLO','TENNIS','PADEL','BASKET') ,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_booking_UNIQUE` (`id`),
  INDEX par_ind (user_id),
  CONSTRAINT fk_user_booking FOREIGN KEY (user_id)
  REFERENCES Users(id)
  ON DELETE CASCADE  
  ON UPDATE CASCADE
  )ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Pitch`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `type` enum('CALCETTO','PALLAVOLO','TENNIS','PADEL','BASKET') ,
  PRIMARY KEY (`id`)
  )ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
  
CREATE TABLE  `Time_Slot` (
`id` int NOT NULL AUTO_INCREMENT,
`time_slot` varchar(45) NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Time_Booking` (
`id` int NOT NULL AUTO_INCREMENT,
`pitch_id` INT NOT NULL,
`booking_id`INT NOT NULL,
`dateBooking` varchar(45) NOT NULL,
`time_id` int NOT NULL,
PRIMARY KEY (`id`, `pitch_id`,`dateBooking`,`time_id`),
INDEX par_ind (pitch_id),
CONSTRAINT fk_pitch FOREIGN KEY (pitch_id)
REFERENCES Pitch(id),
CONSTRAINT fk_booking_id FOREIGN KEY (booking_id)
REFERENCES Booking(id),
CONSTRAINT fk_time_id FOREIGN KEY (time_id)
REFERENCES Time_Slot(id)
ON DELETE CASCADE  
ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



INSERT INTO `agile_hub_production`.`Users`(`name`,`surname`,`password`,`username`,`dateOfBirth`,`age`,`type`)VALUES('Gianluca','Rea','cGFzc3dvcmQ=','aldino','26/09/1997',25,'NORMALE');
INSERT INTO `agile_hub_production`.`Users`(`name`,`surname`,`password`,`username`,`dateOfBirth`,`age`,`type`)VALUES('Francesco','Falone','cGFzc3dvcmQx','falone','26/09/1997',25,'ADMIN');
INSERT INTO `agile_hub_production`.`Users`(`name`,`surname`,`password`,`username`,`dateOfBirth`,`age`,`type`)VALUES('Mario','Rossi','cGFzc3dvcmQ=','mrossi','26/10/1994',29,'SOCIO');
INSERT INTO `agile_hub_production`.`Users`(`name`,`surname`,`password`,`username`,`dateOfBirth`,`age`,`type`)VALUES('Luigi','Rossi','cGFzc3dvcmQ=','lrossi','26/10/1994',29,'SOCIO_PLUS');
INSERT INTO `agile_hub_production`.`Users`(`name`,`surname`,`password`,`username`,`dateOfBirth`,`age`,`type`)VALUES('Giuseppe','Garibalid','cGFzc3dvcmQ=','garibaldi','26/01/1983',40,'SOCIO_PLUS');
INSERT INTO `agile_hub_production`.`Users`(`name`,`surname`,`password`,`username`,`dateOfBirth`,`age`,`type`)VALUES('Giuseppe','Verdi','cGFzc3dvcmQ=','verdi','21/02/1984',39,'SOCIO_PLUS');
INSERT INTO `agile_hub_production`.`Users`(`name`,`surname`,`password`,`username`,`dateOfBirth`,`age`,`type`,`sport`)VALUES('Jack','Sparrow','cGFzc3dvcmQ=','jack','20/04/1990',33,'MAESTRO','CALCETTO');
INSERT INTO `agile_hub_production`.`Users`(`name`,`surname`,`password`,`username`,`dateOfBirth`,`age`,`type`,`sport`)VALUES('Hector','Barbossa','cGFzc3dvcmQ=','hector','20/02/1993',30,'MAESTRO','PALLAVOLO');
INSERT INTO `agile_hub_production`.`Users`(`name`,`surname`,`password`,`username`,`dateOfBirth`,`age`,`type`,`sport`)VALUES('Will','Turner','cGFzc3dvcmQ=','will','10/01/1996',27,'MAESTRO','BASKET');
INSERT INTO `agile_hub_production`.`Users`(`name`,`surname`,`password`,`username`,`dateOfBirth`,`age`,`type`,`sport`)VALUES('Elisabeth','Swann','cGFzc3dvcmQ=','elisabeth','12/11/1994',29,'MAESTRO','TENNIS');
INSERT INTO `agile_hub_production`.`Users`(`name`,`surname`,`password`,`username`,`dateOfBirth`,`age`,`type`,`sport`)VALUES('Joshamee','Gibbs','cGFzc3dvcmQ=','gibbs','07/03/1991',32,'MAESTRO','PADEL');

INSERT INTO `agile_hub_production`.`Teacher_Booking`(`user_id`,`teacher_id`,`dayOfBooking`,`sport`) VALUES (1,7,'30/05/2023','CALCETTO');
INSERT INTO `agile_hub_production`.`Teacher_Booking`(`user_id`,`teacher_id`,`dayOfBooking`,`sport`) VALUES (3,8,'30/05/2023','PALLAVOLO');
INSERT INTO `agile_hub_production`.`Teacher_Booking`(`user_id`,`teacher_id`,`dayOfBooking`,`sport`) VALUES (4,9,'30/05/2023','PADEL');
INSERT INTO `agile_hub_production`.`Teacher_Booking`(`user_id`,`teacher_id`,`dayOfBooking`,`sport`) VALUES (5,10,'30/05/2023','TENNIS');
INSERT INTO `agile_hub_production`.`Teacher_Booking`(`user_id`,`teacher_id`,`dayOfBooking`,`sport`) VALUES (6,11,'30/05/2023','PADEL');

INSERT INTO `agile_hub_production`.`Time_Slot`(`time_slot`) VALUES ('8:00 - 9:00');
INSERT INTO `agile_hub_production`.`Time_Slot`(`time_slot`) VALUES ('9:00 - 10:00');
INSERT INTO `agile_hub_production`.`Time_Slot`(`time_slot`) VALUES ('10:00 - 11:00');
INSERT INTO `agile_hub_production`.`Time_Slot`(`time_slot`) VALUES ('12:00 - 13:00');
INSERT INTO `agile_hub_production`.`Time_Slot`(`time_slot`) VALUES ('13:00 - 14:00');
INSERT INTO `agile_hub_production`.`Time_Slot`(`time_slot`) VALUES ('14:00 - 15:00');
INSERT INTO `agile_hub_production`.`Time_Slot`(`time_slot`) VALUES ('15:00 - 16:00');
INSERT INTO `agile_hub_production`.`Time_Slot`(`time_slot`) VALUES ('16:00 - 17:00');
INSERT INTO `agile_hub_production`.`Time_Slot`(`time_slot`) VALUES ('17:00 - 18:00');
INSERT INTO `agile_hub_production`.`Time_Slot`(`time_slot`) VALUES ('18:00 - 19:00');
INSERT INTO `agile_hub_production`.`Time_Slot`(`time_slot`) VALUES ('19:00 - 20:00');
INSERT INTO `agile_hub_production`.`Time_Slot`(`time_slot`) VALUES ('20:00 - 21:00');
INSERT INTO `agile_hub_production`.`Time_Slot`(`time_slot`) VALUES ('21:00 - 22:00');
INSERT INTO `agile_hub_production`.`Time_Slot`(`time_slot`) VALUES ('22:00 - 23:00');