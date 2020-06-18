drop database if exists `banking_system`;
create database if not exists `banking_system`;
use `banking_system`;

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `fullname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(50),
  `address` varchar(500),
  `status` bool,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

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

INSERT INTO `role` (name)
VALUES 
('ROLE_USER'),('ROLE_EMPLOYEE'),('ROLE_TRANSACTIONMANAGER'),
('ROLE_BRANCHMANAGER'),('ROLE_BANKMANAGER');

INSERT INTO `users_roles` (user_id,role_id)
VALUES 
(1, 1),
(2, 2),
(3, 1);

INSERT INTO `user` (username,password,fullname,email,phone,status, address)
VALUES 
('user_1','$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6','User Name 1','user1@gmail.com','123456789', 1, 'Hanoi'),
('employee_1','$2a$10$8fIEgmzmL.wyfS/HZP927.QqclDoklNSWuEhjmc7C13quS7KTpitC','Employee Name 1','employee1@gmail.com','123422789', 1, 'Hanoi'),
('user_2','$2a$10$8fIEgmzmL.wyfS/HZP927.QqclDoklNSWuEhjmc7C13quS7KTpitC','User Name 2','user2@gmail.com','123454589', 1, 'Hanoi');