-- DATABASE JOB INIT --

drop schema if exists `Job`;
CREATE schema `Job`;

use `Job`;

CREATE TABLE `Users` (
    `username` varchar(32) NOT NULL,
    `password` varchar(64) NOT NULL,
    `name` varchar(32) NOT NULL,
    `surname` varchar(32) NOT NULL,
    `cityOfBirth` varchar(32) NOT NULL,
    `dateOfBirth` date NOT NULL,
    `tel` varchar(16) DEFAULT NULL,
    `userType` varchar(16) NOT NULL,
    PRIMARY KEY (`username`)
);


create table if not exists `Job`.`shift`(
    `shiftCode` varchar(64) primary key,
    `jobName` varchar(32) not null,
    `JobPlace` varchar(32) not null,
    `jobDateTime` datetime not null,
    `jobDescription` varchar(128) not null,
    `employer` varchar(32) not null

);

-- ------------------------------------------------------------------------
--                           STORED PROCEDURES                           --
-- ------------------------------------------------------------------------

-- NEW_USER

USE `Job`;
DROP procedure IF EXISTS `Job`.`new_user`;
;

DELIMITER $$
USE `Job`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `new_user`(in username varchar (32), in pwd varchar(64), in usr_name varchar(32), in surname varchar(32), cityOfBirth varchar (32), in dateOfBirth varchar(32), in userType int)
BEGIN
	declare exit handler for sqlexception
    begin 
        rollback;
        resignal;
    end;

    set transaction isolation level read uncommitted;
    start transaction;
	INSERT INTO `Job`.`Users` (`username`, `password`, `name`, `surname`, `cityOfBirth`, `dateOfBirth`, `userType`)
	VALUES(username, pwd, usr_name, surname, cityOfBirth, dateOfBirth, userType);
    commit;

END$$

DELIMITER ;

-- LOGIN

USE `Job`;
DROP procedure IF EXISTS `login`;

DELIMITER $$
USE `Job`$$
CREATE PROCEDURE `login` (in username varchar(32), in pwd varchar(64))
BEGIN
   declare exit handler for sqlexception
    begin 
        rollback;
        resignal;
    end;

    set transaction isolation level read committed;
    start transaction;
    
    select `username`, `password` from `Users` where `Users`.`username` = username and `Users`.`password` = pwd;
    commit;
END$$

DELIMITER ;