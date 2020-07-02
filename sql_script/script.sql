drop database if exists `banking_system`;
create database if not exists `banking_system`;
use `banking_system`;

#### BRANCH OFFICE TABLE ##############################
DROP TABLE IF EXISTS `branch_office`;

create table `branch_office` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) not null,
  `address` text not null,
  `phone` varchar(20) not null,
  `information` text not null,

  unique key `UNIQUE_PHONE_BRANCH` (`phone`),
  unique key `UNIQUE_BRANCH_NAME` (`name`),
  primary key (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
#######################################################

#### TRANSACTION OFFICE TABLE #########################
DROP TABLE IF EXISTS `transaction_office`;

create table `transaction_office` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) not null,
  `address` text not null,
  `phone` varchar(20) not null,
  `information` text not null,
  `branch_office_id` int(11) NOT NULL,
  
  constraint `FK_BRANCH_OFFICE_1` foreign key (`branch_office_id`)
  references `branch_office` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  unique key `UNIQUE_PHONE_TRANSACTION` (`phone`),
  unique key `TRANSACTION_BRANCH_UNIQUE` (`name`, `branch_office_id`),
  primary key (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
#######################################################

#### MEMBERSHIP #######################################
DROP TABLE IF EXISTS `membership`;

create table `membership`(
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) not null,
  
  primary key (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
#######################################################

#### USER TABLE #######################################
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `fullname` varchar(255) NOT NULL,
  `birthday` date default null,
  `address` varchar(255),
  `gender` varchar (10) not null default 'male',
  `image` varchar(255) default 'https://image.flaticon.com/icons/svg/21/21104.svg',
  `id_card_number` varchar(50) not null,
  `phone` varchar(50) not null,
  `attemped_login_failed` tinyint(1) default 0,
  `created_at` date default null,
  `updated_at` date default null,
  `status` bool not null,
  `locked` bool not null,

  `transaction_office_id` int(11) default null,
  `branch_office_id` int(11) default null,
  `membership_id` int(11) not null,
  
  unique key `EMAIL_UNIQUE` (`email`),
  unique key `USERNAME_UNIQUE` (`username`),
  unique key `ID_CARD_NUMBER` (`id_card_number`),
  
  constraint `FK_MEMBERSHIP_1` foreign key (`membership_id`)
  references `membership` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  constraint `FK_BRANCH_OFFICE_2` foreign key (`branch_office_id`)
  references `branch_office` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION,

  constraint `FK_TRANSACTION_OFFICE_1` foreign key (`transaction_office_id`)
  references `transaction_office` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION,

  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
########################################################

#### ACCOUNT TABLE #####################################
DROP TABLE IF EXISTS `account`;

create table `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_number` varchar(50) not null,
  `user_id` int(11) not null,
  `status` bool default 1,
  `otp_tranfer_enabled` boolean not null,
  `amount` bigint(20) default 20000000,
  `pin_code` varchar(100) not null default 'b59c67bf196a4758191e42f76670ceba',
  `expired_date` date default null,
  `created_at` date default null,
  `updated_at` date default null,
  
  unique key `UNIQUE_ACCOUNT_NUMBER` (`account_number`),
  primary key (`id`),
  
  constraint `FK_USER_ACCOUNT` foreign key (`user_id`)
  references `user` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION
  
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
########################################################

#### CARD TABLE ########################################
DROP TABLE IF EXISTS `card`;

create table `card`(
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `card_number` varchar(15) NOT NULL,
  `status` bool not null default 1,
  `account_id` int(11) not null,
  `expired_at` date default null,
  `created_at` date default null,
  
  constraint `FK_ACCOUNT_ID` foreign key (`account_id`)
  references `account` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  primary key (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
########################################################

#### TRANSACTION TYPE TABLE ############################
DROP TABLE IF EXISTS `transaction_type`;

create table `transaction_type`(
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255),
  
  unique key `UNIQUE_NAME_TRANSACTION_TYPE` (`name`),
  primary key (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
########################################################
  
#### TRANSACTION TABLE #################################
DROP TABLE IF EXISTS `transaction`;

create table `transaction` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
    `account_id` int(11) not null,
    `transaction_type_id` int(11) not null default 1,
    `amount` decimal not null,
    `amount_after_transaction` decimal not null,
    `description` text not null,
    `created_at` date not null,
    
    primary key (`id`),
    
    constraint `FK_ACCOUNT_ID_1` foreign key (`account_id`)
    references `account` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
    
    constraint `FK_TRANSACTION_TYPE_ID` foreign key (`transaction_type_id`)
    references `transaction_type` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
    
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
########################################################

#### TRANSACTION QUEUE TABLE #################################
DROP TABLE IF EXISTS `transaction_queue_internal`;
create table `transaction_queue_internal` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
    `otp_code` varchar(100) not null,
    `tranfer_account_id` int(11) NOT NULL,
    `receiver_account_id` int(11) NOT NULL,
    `amount` decimal not null,
    `description` text not null,
    `expried_at` timestamp not null,
    
    primary key (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
##############################################################

##############################################################
#### ROLE ####
DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
##############################################################

#### USERS_ROLES #############################################
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
##############################################################

#### CHEQUE TABLE ############################################
drop table if exists `cheque`;

create table `cheque`(
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) NOT NULL,
  `reciever_fullname` varchar(255) not null,
  `reciever_id_card_number` varchar(50) not null,
  `transaction_amount` decimal not null,
  `status` bool default 1,
  `canceled` bool not null,
  `created_at` date not null,
  `expired_date` date default null,
  
  constraint `FK_ACCOUNT_CHEQUE` foreign key (`account_id`)
  references `account` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION,

  primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
##############################################################

#### LOAN INTEREST RATE TABLE ################################
drop table if exists `loan_interest_rate`;

-- create table `loan_interest_rate` (
-- 	`id` int(11) NOT NULL AUTO_INCREMENT,
--     `interest_rate` 
--     primary key (`id`)
-- )  ENGINE=InnoDB DEFAULT CHARSET=latin1;
-- ##############################################################

-- #### LOAN PROFILE TABLE TABLE ################################
-- drop table if exists `loan_profile`;

-- create table `loan_profile` (
-- 	`id` int(11) NOT NULL AUTO_INCREMENT,
--     `user_id` int(11) not null,
--     `account_id` int(11) not null,
--     `amount` bigint(20),
--     `loan_interest_rate_id` int(11) not null,
--     
--     constraint `FK_USER_LOAN_PROFILE` foreign key (`user_id`)
--     references `user` (`id`)
--     ON DELETE NO ACTION ON UPDATE NO ACTION,
--     
--     constraint `FK_ACCOUNT_LOAN_PROFILE` foreign key (`account_id`)
--     references `account` (`id`)
--     ON DELETE NO ACTION ON UPDATE NO ACTION,
--     
--     constraint `FK_INTEREST_RATE_LOAN_PROFILE` foreign key (`loan_interest_rate_id`)
--     references `loan_interest_rate` (`id`)
--     ON DELETE NO ACTION ON UPDATE NO ACTION,
--     
--     primary key (`id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
-- ##############################################################
























############################################## INSERT DATA ##########################################################
INSERT INTO `role` (name)
VALUES 
('ROLE_USER'),
('ROLE_EMPLOYEE'),
('ROLE_TRANSACTIONMANAGER'),
('ROLE_BRANCHMANAGER'),
('ROLE_BANKMANAGER');

INSERT INTO `membership` (name) VALUES
('GOLD'),('PLATINUM'),('DIAMOND'),('NONE');

INSERT INTO `branch_office` (name,phone,address,information)
VALUES
('Ha Noi', "1111111110", "Ha Noi", "1st Branch Office"),
('Nghe An', "1111111111", "Nghe An", "2nd Branch Office"),
('Ho Chi Minh', "1111111112", "Ho Chi Minh", "3rd Branch Office");

INSERT INTO `transaction_office` (name,phone,address,information,branch_office_id)
VALUES
("Chua Lang", "2222222220", "80 Chua Lang", "1st Transaction Office", 1),
("Doi Can", "2222222221", "100 Doi Can", "1st Transaction Office", 1),
("Thanh Xuan", "2222222222", "Somewhere in Thanh Xuan", "1st Transaction Office", 1);

INSERT INTO `user` (username, email, password, fullname, birthday, address, id_card_number, phone, membership_id, created_at, updated_at, status, locked)
VALUES
('xuanhieu_1', 'xuanhieu1@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'Nguyen Xuan Hieu','1994-06-22','Ha Noi','123123123001','+84963558935',1,'2015-12-12','2015-12-12', 1, 0),
('minhduc_1', 'minhduc1@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'Nguyen Minh Duc','1994-06-22','Ha Noi','123123123002','+84966423895',1,'2015-12-12','2015-12-12', 1, 0),
('hoanghung_1', 'hoanghung1@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'Hoang Van Hung','1994-06-22','Ha Noi','123123123003','3333333003',1,'2015-12-12','2015-12-12', 1, 0);

INSERT INTO `user` (username, email, password, fullname, birthday, address, id_card_number, phone, membership_id, created_at, updated_at, status, locked, transaction_office_id)
VALUES
('employee_1', 'employee1@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'Employee 1','1994-06-22','Ha Noi','123123123011','+8412312311',4,'2015-12-12','2015-12-12', 1, 0, 1),
('employee_2', 'employee2@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'Employee 2','1994-06-22','Ha Noi','123123123012','+8412312312',4,'2015-12-12','2015-12-12', 1, 0, 1),
('employee_3', 'employee3@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'Employee 3','1994-06-22','Ha Noi','123123123013','+8412312313',4,'2015-12-12','2015-12-12', 1, 0, 1);

INSERT INTO `users_roles` (user_id,role_id)
VALUES 
(1, 1),
(2, 1),
(3, 1),
(4, 2),
(5, 2),
(6, 2);

INSERT INTO `account` (amount, account_number, user_id, created_at, updated_at, expired_date, otp_tranfer_enabled)
VALUES
# accounts of Xuan Hieu
(1000000, 444411111001, 1, '2020-10-10', '2020-10-10', '2025-10-10', 1), 
(1000000, 444411111002, 1, '2020-10-10', '2020-10-10', '2025-10-10', 0), 
(1000000, 444411111003, 1, '2020-10-10', '2020-10-10', '2025-10-10', 0), 
(1000000, 444411111004, 1, '2020-10-10', '2020-10-10', '2025-10-10', 0), 
(1000000, 444411111005, 1, '2020-10-10', '2020-10-10', '2025-10-10', 0),
# accounts of Minh Duc
(1000000, 444411112001, 2, '2020-10-10', '2020-10-10', '2025-10-10', 1), 
(1000000, 444411112002, 2, '2020-10-10', '2020-10-10', '2025-10-10', 0), 
(1000000, 444411112003, 2, '2020-10-10', '2020-10-10', '2025-10-10', 0), 
(1000000, 444411112004, 2, '2020-10-10', '2020-10-10', '2025-10-10', 0), 
(1000000, 444411112005, 2, '2020-10-10', '2020-10-10', '2025-10-10', 0),
# accounts of Hoang Hung
(1000000, 444411113001, 3, '2020-10-10', '2020-10-10', '2025-10-10', 1), 
(1000000, 444411113002, 3, '2020-10-10', '2020-10-10', '2025-10-10', 0), 
(1000000, 444411113003, 3, '2020-10-10', '2020-10-10', '2025-10-10', 0), 
(1000000, 444411113004, 3, '2020-10-10', '2020-10-10', '2025-10-10', 0), 
(1000000, 444411113005, 3, '2020-10-10', '2020-10-10', '2025-10-10', 0);

insert into `transaction_type` (name)
values
('transfer_internal'),
('transfer_external'),
('receive_internal');

insert into `card` (card_number, expired_at, created_at, account_id) values
(555511111001, '2025-10-10', '2020-10-10', 1),
(555511111002, '2025-10-10', '2020-10-10', 2),
(555511111003, '2025-10-10', '2020-10-10', 3),
(555511111004, '2025-10-10', '2020-10-10', 4),
(555511111005, '2025-10-10', '2020-10-10', 5),

(555511112001, '2025-10-10', '2020-10-10', 6),
(555511112002, '2025-10-10', '2020-10-10', 7),
(555511112003, '2025-10-10', '2020-10-10', 8),
(555511112004, '2025-10-10', '2020-10-10', 9),
(555511112005, '2025-10-10', '2020-10-10', 10),

(555511113001, '2025-10-10', '2020-10-10', 11),
(555511113002, '2025-10-10', '2020-10-10', 12),
(555511113003, '2025-10-10', '2020-10-10', 13),
(555511113004, '2025-10-10', '2020-10-10', 14),
(555511113005, '2025-10-10', '2020-10-10', 15);

insert into `transaction` (amount, amount_after_transaction, description, created_at, account_id, transaction_type_id) values
(-100000, 1900000, 'test', '2020-06-25', 1, 1),
(-100000, 1800000, 'test', '2020-06-25', 1, 1),
(-100000, 1700000, 'test', '2020-06-25', 1, 1),
(-100000, 1600000, 'test', '2020-06-25', 1, 1),
(-100000, 1500000, 'test', '2020-06-25', 1, 1),
(-100000, 1400000, 'test', '2020-06-25', 1, 1),
(-100000, 1300000, 'test', '2020-06-25', 1, 1),
(-100000, 1200000, 'test', '2020-06-25', 1, 1),
(-100000, 1100000, 'test', '2020-06-25', 1, 1),
(-100000, 1000000, 'test', '2020-06-25', 1, 1),

(100000, 100000, 'test', '2020-06-25', 6, 3),
(100000, 200000, 'test', '2020-06-25', 6, 3),
(100000, 300000, 'test', '2020-06-25', 6, 3),
(100000, 400000, 'test', '2020-06-25', 6, 3),
(100000, 500000, 'test', '2020-06-25', 6, 3),
(100000, 600000, 'test', '2020-06-25', 6, 3),
(100000, 700000, 'test', '2020-06-25', 6, 3),
(100000, 800000, 'test', '2020-06-25', 6, 3),
(100000, 900000, 'test', '2020-06-25', 6, 3),
(100000, 1000000, 'test', '2020-06-25', 6, 3);

insert into `cheque` (reciever_fullname, reciever_id_card_number, transaction_amount, status, canceled, created_at, expired_date, account_id)
values
("LE THI XUAN HOA", "123123124001", 100000, 0, 0, '2020-06-29', '2020-07-03', 1),
("NGUYEN XUAN DONG", "123123124002", 100000, 0, 0, '2020-06-29', '2020-07-03', 1),
("NGUYEN MINH DUC", "123123124003", 100000, 0, 0, '2020-06-29', '2020-07-03', 1),
("NGUYEN VAN A", "123123124004", 100000, 0, 0, '2020-06-29', '2020-07-03', 1),
("NGUYEN VAN B", "123123124005", 100000, 0, 0, '2020-06-29', '2020-07-03', 1);

