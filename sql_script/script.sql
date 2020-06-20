drop database if exists `banking_system`;
create database if not exists `banking_system`;
use `banking_system`;

#### BRANCH OFFICE TABLE ##############################
DROP TABLE IF EXISTS `branch_office`;

create table `branch_office` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` text not null,
  `information` text not null,

  primary key (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
#######################################################

#### TRANSACTION OFFICE TABLE #########################
DROP TABLE IF EXISTS `transaction_office`;

create table `transaction_office` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` text not null,
  `information` text not null,
  `branch_office_id` int(11) NOT NULL,
  
  constraint `FK_BRANCH_OFFICE_1` foreign key (`branch_office_id`)
  references `branch_office` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
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
  `password` varchar(255) NOT NULL,
  `fullname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `address` varchar(255),
  `status` bool default 1,
  `image` varchar(255) default 'https://image.flaticon.com/icons/svg/21/21104.svg',
  `id_card_number` varchar(50) not null,
  `created_at` date default null,
  `updated_at` date default null,

  `transaction_office_id` int(11) default null,
  `branch_office_id` int(11) default null,
  `membership_id` int(11) default null,
  
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
  `amount` bigint(20),
  `pin_code` char not null,
  `expired_date` date default null,
  `created_at` date default null,
  `updated_at` date default null,
  `attemped_login_failed` tinyint(1) default 0,
  
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
  `card_number` varchar(10) NOT NULL,
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
    `created_at` date not null,
    `amount_after_transaction` decimal not null,
    `description` text,
    
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
('ROLE_USER'),('ROLE_EMPLOYEE'),('ROLE_TRANSACTIONMANAGER'),
('ROLE_BRANCHMANAGER'),('ROLE_BANKMANAGER');

INSERT INTO `membership` (name) VALUES
('GOLD'),('PLATINUM'),('DIAMOND'),('NONE')

-- INSERT INTO `user` (username,password,fullname,email,phone,status,address)
-- VALUES 
-- ('user_1','$2y$12$IojDHLSwsag0uk4RPmY1Re7ek/b4ptRNAsPohxsB9DdAEDGUiHMb6','User Name 1','user1@gmail.com','123456789', 1, 'Hanoi'),
-- ('employee_1','$2a$10$8fIEgmzmL.wyfS/HZP927.QqclDoklNSWuEhjmc7C13quS7KTpitC','Employee Name 1','employee1@gmail.com','123422789', 1, 'Hanoi'),
-- ('user_2','$2a$10$8fIEgmzmL.wyfS/HZP927.QqclDoklNSWuEhjmc7C13quS7KTpitC','User Name 2','user2@gmail.com','123454589', 1, 'Hanoi');

-- INSERT INTO `users_roles` (user_id,role_id)
-- VALUES 
-- (1, 1),
-- (2, 2),
-- (3, 1);

