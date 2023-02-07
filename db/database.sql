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


create table if not exists `Job`.`Shifts`(
    `shiftCode` varchar(64),
    `jobName` varchar(32) not null,
    `JobPlace` varchar(32) not null,
    `jobDateTime` varchar(32) not null,
    `jobDescription` varchar(128) not null,
    `employer` varchar(32) not null,
    primary key (`shiftCode`),
    foreign key (`employer`) references `Job`.`Users`(`username`)
);


create table if not exists `Job`.`ShiftAppliance`(
     `applianceCode` varchar(128) primary key,
     `applianceDate` varchar(32) not null,
     `shiftDate` varchar(32) not null,
     `isAccepted` bool not null,
     `shiftEmployer` varchar(32) not null,
     `shiftEmployee` varchar(32) not null,
     foreign key (`shiftEmployee`) references `Job`.`Users`(`username`),
     foreign key (`shiftEmployer`) references Job.`Shifts`(`employer`)
);


create table if not exists `Job`.`Demises`
(
    `shiftAppliance` varchar(128) primary key,
    `motivation` varchar(128) not null,
    `shiftApplianceEmployee` varchar(32) not null,
    `shiftDate` varchar(32) not null,
    `accepted` bool not null,
    `sent` bool not null,
    foreign key (`shiftAppliance`) references `Job`.`ShiftAppliance`(`applianceCode`)
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

-- pushShift

USE `Job`;
DROP procedure IF EXISTS `pushShift`;

DELIMITER $$
USE `Job`$$
CREATE PROCEDURE `pushShift` (in shift_code varchar(64), in job_name varchar(32), in job_place varchar(32), in job_date_time varchar(32), in job_description varchar(128), in emp varchar(32))
BEGIN
    declare exit handler for sqlexception
        begin
            rollback;
            resignal;
        end;

    set transaction isolation level read uncommitted;
    start transaction;
    INSERT INTO `Job`.`Shifts` (`shiftCode`,`jobName`,`JobPlace`,`jobDateTime`,`jobDescription`,`employer`)
    VALUES (shift_code, job_name, job_place, job_date_time, job_description, emp);
    commit;
END$$

DELIMITER ;

-- pushAppliance

USE `Job`;
DROP procedure IF EXISTS `pushAppliance`;

DELIMITER $$
USE `Job`$$
CREATE PROCEDURE `pushAppliance` (in appliance_code varchar (128), in appliance_date varchar(32), in shift_date varchar(32), in is_accepted bool, in shift_employer varchar(32), in shift_employee varchar(32))
BEGIN
    declare exit handler for sqlexception
        begin
            rollback;
            resignal;
        end;

    set transaction isolation level read uncommitted;

    INSERT INTO `Job`.`ShiftAppliance`(`applianceCode`, `applianceDate`, `shiftDate`, `isAccepted`, `shiftEmployer`, `shiftEmployee`)
    VALUES (appliance_code, appliance_date, shift_date, is_accepted, shift_employer, shift_employee);

    commit;

END$$

DELIMITER ;

-- pushDemise

USE `Job`;
DROP procedure IF EXISTS `pushDemise`;

DELIMITER $$
USE `Job`$$
CREATE PROCEDURE `pushDemise` (in shift_appliance varchar (128), in motivate varchar(128), in shift_employer varchar(32),in a bool, in s bool)
BEGIN
    declare var_date varchar(32);
    declare exit handler for sqlexception
        begin
            rollback;
            resignal;
        end;

    set transaction isolation level read committed ;

    select `shiftDate` from `Job`.`ShiftAppliance` where `applianceCode` = shift_appliance into var_date;

    INSERT INTO `Job`.`Demises` (`shiftAppliance`, `motivation`, `shiftApplianceEmployee`, `shiftDate`, `accepted`, `sent`)
    VALUES (shift_appliance, motivate, shift_employer, var_date, a, s);

    commit;

END$$

DELIMITER ;


