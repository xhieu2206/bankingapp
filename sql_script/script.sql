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
  `status` bool default 1,

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
  `status` bool default 1,
  `account_id` int(11) not null,
  `expired_date` date default null,
  `created_at` date default null,
  
  constraint `FK_ACCOUNT_ID` foreign key (`account_id`)
  references `account` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  primary key (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
########################################################

#### TRANSACTION TABLE #################################
DROP TABLE IF EXISTS `transaction`;

create table `transaction` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
    `account_id` int(11) not null,
    `amount` decimal not null,
    `amount_after_transaction` decimal not null,
    `description` text,
    `created_at` date not null,
    
    primary key (`id`),
    
    constraint `FK_ACCOUNT_ID_1` foreign key (`account_id`)
    references `account` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
    
    
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
########################################################

########################################################
#### ROLE ####
DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
########################################################

#### USERS_ROLES #######################################
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
########################################################

#### CHEQUE TABLE ######################################
drop table if exists `cheque`;

create table `cheque`(
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) NOT NULL,
  `status` bool default 1,
  `canceled` bool default 0,
  `created_at` date not null,
  `expired_date` date default null,
  `transaction_amount` decimal not null,
  
  constraint `FK_ACCOUNT_CHEQUE` foreign key (`account_id`)
  references `account` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION,

  primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
########################################################













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

INSERT INTO `user` (username,email,password,fullname,birthday,address,id_card_number,phone,membership_id)
VALUES
('username_1', 'username1@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'User Name 1','1994-06-22','Ha Noi','123123123001','3333333001',1),
('username_2', 'username2@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'User Name 2','1994-06-22','Ha Noi','123123123002','3333333002',1),
('username_3', 'username3@gmail.com', '$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6', 'User Name 3','1994-06-22','Ha Noi','123123123003','3333333002',1);

INSERT INTO `users_roles` (user_id,role_id)
VALUES 
(1, 1),
(2, 1),
(3, 1);