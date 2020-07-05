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
    `created_at` datetime not null,
    
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
  `created_at` datetime not null,
  `expired_date` datetime default null,
  `withdraw_date` datetime default null,
  
  constraint `FK_ACCOUNT_CHEQUE` foreign key (`account_id`)
  references `account` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION,

  primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
##############################################################

#### LOAN INTEREST RATE TABLE ################################
drop table if exists `loan_interest_rate`;

create table `loan_interest_rate` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
    `interest_rate` decimal(2,1) not null,
    `months` int not null,
    primary key (`id`)
)  ENGINE=InnoDB DEFAULT CHARSET=latin1;
##############################################################

#### LOAN PROFILE TABLE TABLE ################################
drop table if exists `loan_profile`;

create table `loan_profile` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
    `amount` bigint(20),
    `description` text not null,
    `confirmed` boolean not null,
    `approved` boolean not null,
    `rejected` boolean default false,
    `rejected_reason` text default null,
    `status` varchar(100) not null,
    `created_at` date default null,

    `loan_interest_rate_id` int(11) not null,
    `account_id` int(11) not null,
    `user_id` int(11) not null,
    `transaction_office_id` int(11) default null,
    
    constraint `FK_USER_LOAN_PROFILE` foreign key (`user_id`)
    references `user` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
    
    constraint `FK_ACCOUNT_LOAN_PROFILE` foreign key (`account_id`)
    references `account` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
    
    constraint `FK_INTEREST_RATE_LOAN_PROFILE` foreign key (`loan_interest_rate_id`)
    references `loan_interest_rate` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
    
    constraint `FK_LOAN_PROFILE_TRANSACTION_OFFICE_ID` foreign key (`transaction_office_id`)
    references `transaction_office` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,

    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
##############################################################

#### ASSET TABLE #############################################
drop table if exists `asset`;

create table `asset` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `description` text not null,
    `price` bigint(20) not null,
    `loan_profile_id` int(11) NOT NULL,
    
    constraint `FK_ASSET_LOAN_PRIFILE` foreign key (`loan_profile_id`)
    references `loan_profile` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,

    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
##############################################################

#### IMAGES ASSET TABLE #############################################
drop table if exists `images_asset`;
create table `images_asset` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
    `url` varchar(255) not null,
    `asset_id` int(11) NOT NULL,
    
    constraint `FK_IMAGES_ASSET_ID` foreign key (`asset_id`)
    references `asset` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
    
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
##############################################################

#### NOTIFICATION TABLE TABLE ################################
drop table if exists `notification`;
create table `notification`(
	`id` int(11) NOT NULL AUTO_INCREMENT,
    `message` varchar(1000) not null,
    `created_at` datetime not null,
    `is_read` boolean default 0,
    
    `user_id` int(11) NOT NULL,
    
    constraint `FK_NOTIFICATION_USER_ID` foreign key (`user_id`)
    references `user` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,

    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
##############################################################

#### LOAN PROFILE QUEUE TABLE ################################
drop table if exists `loan_profile_queue`;
create table `loan_profile_queue`(
	`id` int(11) NOT NULL AUTO_INCREMENT,
    `otp_code` varchar(100) not null,
	`expried_at` timestamp not null,
    `loan_profile_id` int(11) NOT NULL,
     
    primary key (`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
##############################################################
























############################################## INSERT DATA ##########################################################
insert into `loan_interest_rate` (interest_rate, months) values
(5.5, 6), (6, 9), (6.5, 12), (7, 15), (7.5, 18), (8, 21), (8.5, 24);

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
('Ho Chi Minh', "1111111112", "Ho Chi Minh", "3rd Branch Office"),
('Ninh Binh', "1111111113", "Ninh Binh", "4th Branch Office"),
('Ha Nam', "1111111115", "Ninh Binh", "5th Branch Office"),
('Ha Bac', "1111111116", "Ninh Bac", "6th Branch Office"),
('Ha Tay', "1111111117", "Ha Tay", "6th Branch Office");

INSERT INTO `transaction_office` (name,phone,address,information,branch_office_id)
VALUES
("Chua Lang", "2222222220", "80 Chua Lang", "1st Transaction Office", 1),
("Doi Can", "2222222221", "100 Doi Can", "2nd Transaction Office", 1),
("Thanh Xuan", "2222222222", "Somewhere in Thanh Xuan", "3rd Transaction Office", 1),
("Cua Nam", "2222222223", "Somewhere in Cua Nam", "4th Transaction Office", 1),
("Thang Long", "2222222224", "Somewhere in Thang Long", "5th Transaction Office", 2),
("Bach Dang", "2222222225", "Somewhere in Bach Dang", "6th Transaction Office", 2),
("Thai Nguyen", "2222222226", "Somewhere in Thai Nguyen", "7th Transaction Office", 2);

-- INSERT FOR USER
INSERT INTO `user` (username, email, password, fullname, birthday, address, id_card_number, phone, membership_id, created_at, updated_at, status, locked)
VALUES
('xuanhieu_1', 'xuanhieu1@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'Nguyen Xuan Hieu','1994-06-22','Ha Noi','123123123001','0963558935',1,'2015-12-12','2015-12-12', 1, 0),
('minhduc_1', 'minhduc1@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'Nguyen Minh Duc','1994-06-22','Ha Noi','123123123002','0966423895',1,'2015-12-12','2015-12-12', 1, 0),
('hoanghung_1', 'hoanghung1@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'Hoang Van Hung','1994-06-22','Ha Noi','123123123003','0325357329',1,'2015-12-12','2015-12-12', 1, 0),
('username_1', 'username1@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'User Name Mot','1994-06-22','Ha Noi','123123123004','0912311111',1,'2015-12-12','2015-12-12', 1, 0),
('username_2', 'username2@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'User Name Hai','1994-06-22','Ha Noi','123123123005','0912311112',1,'2015-12-12','2015-12-12', 1, 0),
('username_3', 'username3@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'User Name Ba','1994-06-22','Ha Noi','123123123006','0912311113',1,'2015-12-12','2015-12-12', 1, 0),
('username_4', 'username4@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'User Name Bon','1994-06-22','Ha Noi','123123123007','0912311114',1,'2015-12-12','2015-12-12', 1, 0),
('username_5', 'username5@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'User Name Nam','1994-06-22','Ha Noi','123123123008','0912311115',1,'2015-12-12','2015-12-12', 1, 0),
('username_6', 'username6@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'User Name Sau','1994-06-22','Ha Noi','123123123009','0912311116',1,'2015-12-12','2015-12-12', 1, 0),
('username_7', 'username7@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'User Name Bay','1994-06-22','Ha Noi','123123123010','0912311117',1,'2015-12-12','2015-12-12', 1, 0);

-- INSERT FOR EMPLOYEE
INSERT INTO `user` (username, email, password, fullname, birthday, address, id_card_number, phone, membership_id, created_at, updated_at, status, locked, transaction_office_id)
VALUES
('employee_1', 'employee1@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'Employee Mot','1995-06-21','Ha Noi','123123123011','0123123111',4,'2015-12-12','2015-12-12', 1, 0, 1),
('employee_2', 'employee2@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'Employee Hai','1995-06-21','Ha Noi','123123123012','0123123112',4,'2015-12-12','2015-12-12', 1, 0, 1),
('employee_3', 'employee3@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'Employee Ba','1995-06-21','Ha Noi','123123123013','0123123113',4,'2015-12-12','2015-12-12', 1, 0, 1),
('employee_4', 'employee4@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'Employee Bon','1995-06-21','Ha Noi','123123123014','0123123114',4,'2015-12-12','2015-12-12', 1, 0, 1),
('employee_5', 'employee5@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'Employee Nam','1995-06-21','Ha Noi','123123123015','0123123115',4,'2015-12-12','2015-12-12', 1, 0, 2),
('employee_6', 'employee6@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'Employee Sau','1995-06-21','Ha Noi','123123123016','0123123116',4,'2015-12-12','2015-12-12', 1, 0, 2),
('employee_7', 'employee7@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'Employee Bay','1995-06-21','Ha Noi','123123123017','0123123117',4,'2015-12-12','2015-12-12', 1, 0, 2);

-- INSERT FOR TRANSACTION OFFICE MANAGER ROLE
INSERT INTO `user` (username, email, password, fullname, birthday, address, id_card_number, phone, membership_id, created_at, updated_at, status, locked, transaction_office_id) VALUES
('transaction_manager_1', 'transactionM1@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'Transaction Mot', '1993-01-01', 'Ha Noi', '123123124011', '0123123211', 4, '2015-12-12','2015-12-12', 1, 0, 1),
('transaction_manager_2', 'transactionM2@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'Transaction Hai', '1993-01-01', 'Ha Noi', '123123124012', '0123123212', 4, '2015-12-12','2015-12-12', 1, 0, 1),
('transaction_manager_3', 'transactionM3@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'Transaction Ba', '1993-01-01', 'Ha Noi', '123123124013', '0123123213', 4, '2015-12-12','2015-12-12', 1, 0, 1),
('transaction_manager_4', 'transactionM4@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'Transaction Bon', '1993-01-01', 'Ha Noi', '123123124014', '0123123214', 4, '2015-12-12','2015-12-12', 1, 0, 1),
('transaction_manager_5', 'transactionM5@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'Transaction Nam', '1993-01-01', 'Ha Noi', '123123124015', '0123123215', 4, '2015-12-12','2015-12-12', 1, 0, 1),
('transaction_manager_6', 'transactionM6@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'Transaction Sau', '1993-01-01', 'Ha Noi', '123123124016', '0123123216', 4, '2015-12-12','2015-12-12', 1, 0, 1),
('transaction_manager_7', 'transactionM7@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'Transaction Bay', '1993-01-01', 'Ha Noi', '123123124017', '0123123217', 4, '2015-12-12','2015-12-12', 1, 0, 1);

-- INSERT FOR BRANCH OFFICE MANAGER ROLE
INSERT INTO `user` (username, email, password, fullname, birthday, address, id_card_number, phone, membership_id, created_at, updated_at, status, locked, branch_office_id) VALUES
('branch_manager_1', 'branchmanager1@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'Branch Mot', '1990-01-01', 'Ha Noi', '123153124011', '0123153211', 4, '2015-12-12','2015-12-12', 1, 0, 1),
('branch_manager_2', 'branchmanager2@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'Branch Hai', '1990-01-01', 'Ha Noi', '123153124012', '0123153212', 4, '2015-12-12','2015-12-12', 1, 0, 2);
INSERT INTO `users_roles` (user_id,role_id) VALUES 
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1),
(6, 1),
(7, 1),
(8, 1),
(9, 1),
(10, 1),
(11, 2),
(12, 2),
(13, 2),
(14, 2),
(15, 2),
(16, 2),
(17, 2),
(18, 3),
(19, 3),
(20, 3),
(21, 3),
(22, 3),
(23, 3),
(24, 3),
(25, 4),
(26, 4);

INSERT INTO `account` (amount, account_number, user_id, created_at, updated_at, expired_date, otp_tranfer_enabled, status)
VALUES
# accounts of Xuan Hieu
(1000000, 444411111001, 1, '2020-10-10', '2020-10-10', '2025-10-10', 1, 1), 
(2000000, 444411111002, 1, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1), 
(3000000, 444411111005, 1, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1),
(4000000, 444411111003, 1, '2013-10-10', '2013-10-10', '2022-10-10', 0, 1), 
(5000000, 444411111004, 1, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1),
# accounts of Minh Duc
(1000000, 444411112001, 2, '2020-10-10', '2020-10-10', '2025-10-10', 1, 1), 
(2000000, 444411112002, 2, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1), 
(3000000, 444411112003, 2, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1), 
(4000000, 444411112004, 2, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1), 
(5000000, 444411112005, 2, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1),
# accounts of Hoang Hung
(1000000, 444411113001, 3, '2020-10-10', '2020-10-10', '2025-10-10', 1, 1), 
(2000000, 444411113002, 3, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1), 
(3000000, 444411113003, 3, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1), 
(4000000, 444411113004, 3, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1), 
(5000000, 444411113005, 3, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1),
# accounts of username_1
(1000000, 444411114001, 4, '2020-10-10', '2020-10-10', '2025-10-10', 1, 1), 
(2000000, 444411114002, 4, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1), 
(3000000, 444411114003, 4, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1), 
(4000000, 444411114004, 4, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1), 
(5000000, 444411114005, 4, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1),
# accounts of username_2
(1000000, 444411115001, 5, '2020-10-10', '2020-10-10', '2025-10-10', 1, 1), 
(2000000, 444411115002, 5, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1), 
(3000000, 444411115003, 5, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1), 
(4000000, 444411115004, 5, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1), 
(5000000, 444411115005, 5, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1),
# accounts of username_3
(1000000, 444411116001, 6, '2020-10-10', '2020-10-10', '2025-10-10', 1, 1), 
(2000000, 444411116002, 6, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1), 
(3000000, 444411116003, 6, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1), 
(4000000, 444411116004, 6, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1), 
(5000000, 444411116005, 6, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1),
# accounts of username_4
(1000000, 444411117001, 7, '2020-10-10', '2020-10-10', '2025-10-10', 1, 1), 
(2000000, 444411117002, 7, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1), 
(3000000, 444411117003, 7, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1), 
(4000000, 444411117004, 7, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1), 
(5000000, 444411117005, 7, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1),
# accounts of username_5
(1000000, 444411118001, 8, '2020-10-10', '2020-10-10', '2025-10-10', 1, 1), 
(2000000, 444411118002, 8, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1), 
(3000000, 444411118003, 8, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1), 
(4000000, 444411118004, 8, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1), 
(5000000, 444411118005, 8, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1),
# accounts of username_6
(1000000, 444411119001, 9, '2020-10-10', '2020-10-10', '2025-10-10', 1, 1), 
(2000000, 444411119002, 9, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1), 
(3000000, 444411119003, 9, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1), 
(4000000, 444411119004, 9, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1), 
(5000000, 444411119005, 9, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1),
# accounts of username_7
(1000000, 444411111101, 4, '2020-10-10', '2020-10-10', '2025-10-10', 1, 1), 
(2000000, 444411111102, 4, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1), 
(3000000, 444411111103, 4, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1), 
(4000000, 444411111104, 4, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1), 
(5000000, 444411111105, 4, '2020-10-10', '2020-10-10', '2025-10-10', 0, 1);

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

insert into `loan_profile` (amount, description, confirmed, approved, rejected, rejected_reason, status, created_at, loan_interest_rate_id, account_id, user_id, transaction_office_id) values
(50000000, 'Test Description', true, false, false, '', 'CONFIRMED', '2020-06-28', 2, 11, 3, 1), -- 1
(100000000, 'Test Description', true, false, false, '', 'CONFIRMED', '2020-06-28', 4, 12, 3, 1), -- 2
(150000000, 'Test Description', true, false, false, '', 'CONFIRMED', '2020-06-28', 5, 13, 3, 1), -- 3
(20000000, 'Test Description', true, false, false, '', 'CONFIRMED', '2020-06-28', 2, 14, 3, 1), -- 4
(80000000, 'Test Description', false, false, false, '', 'CREATED', '2020-06-28', 6, 15, 3, 1), -- 5
(90000000, 'Test Description', false, false, false, '', 'CREATED', '2020-06-28', 2, 16, 4, 1), -- 6
(100000000, 'Test Description', false, false, false, '', 'CREATED', '2020-06-28', 2, 17, 4, 1), -- 7
(70000000, 'Test Description', false, false, false, '', 'CREATED', '2020-06-28', 6, 18, 4, 1), -- 8
(62000000, 'Test Description', true, false, false, '', 'APPROVED BY TRANSACTION MANAGER', '2020-06-28', 2, 19, 4, 1), -- 9
(89000000, 'Test Description', true, false, true, 'Too much money', 'REJECTED', '2020-06-28', 2, 20, 4, 1), -- 10
(15000000, 'Test Description', true, true, false, '', 'CONFIRMED', '2020-06-28', 4, 21, 5, 1), -- 11
(100000000, 'Test Description', true, true, false, '', 'APPROVED', '2020-06-28', 4, 22, 5, 1), -- 12
(70000000, 'Test Description', true, false, true, 'Too much money', 'REJECTED', '2020-06-28', 2, 23, 5, 1), -- 13
(62000000, 'Test Description', true, true, false, '', 'APPROVED', '2020-06-28', 2, 24, 5, 1), -- 14
(89000000, 'Test Description', true, false, false, 'Too much money', 'REJECTED', '2020-06-28', 3, 25, 5, 1), -- 15
(15000000, 'Test Description', true, true, false, '', 'APPROVED', '2020-06-28', 1, 26, 6, 1); -- 16
