DROP DATABASE IF EXISTS animal_database;
CREATE DATABASE animal_database;
USE animal_database;

/* *****************************************************************************
	Create statement for table Animal
***************************************************************************** */
DROP TABLE IF EXISTS Animal;
CREATE TABLE Animal(
    Animal_id VARCHAR(10) NOT NULL,
    Animal_name VARCHAR(100) NOT NULL,
    Animal_species VARCHAR(25) NOT NULL,
    Animal_gender VARCHAR(6) NOT NULL,
    Animal_age INT NOT NULL,
    Animal_fixed BOOLEAN NOT NULL,
    Animal_legs INT NOT NULL,
    Animal_weight DECIMAL(8,2) NOT NULL,
    Animal_date_added DATE NOT NULL,
    Animal_last_feeding_time DATETIME NOT NULL,
    PRIMARY KEY(Animal_id))
;

/* *****************************************************************************
	Build Stored Procedure sp_add_Animal
***************************************************************************** */
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_add_Animal$$
CREATE PROCEDURE sp_add_Animal(
    IN p_Animal_id VARCHAR(10),
    IN p_Animal_name VARCHAR(100),
    IN p_Animal_species VARCHAR(25),
    IN p_Animal_gender VARCHAR(6),
    IN p_Animal_age INT,
    IN p_Animal_fixed BOOLEAN,
    IN p_Animal_legs INT,
    IN p_Animal_weight DECIMAL(8,2),
    IN p_Animal_date_added DATE,
    IN p_Animal_last_feeding_time DATETIME


)
BEGIN
    INSERT INTO Animal(
        Animal_id,
        Animal_name,
        Animal_species,
        Animal_gender,
        Animal_age,
        Animal_fixed,
        Animal_legs,
        Animal_weight,
        Animal_date_added,
        Animal_last_feeding_time
    )
    VALUES (
        p_Animal_id,
        p_Animal_name,
        p_Animal_species,
        p_Animal_gender,
        p_Animal_age, 
        p_Animal_fixed, 
        p_Animal_legs, 
        p_Animal_weight,
        p_Animal_date_added,
        p_Animal_last_feeding_time
    );
END$$
DELIMITER ;


/* *****************************************************************************
	Build Stored Procedure sp_get_an_Animal
***************************************************************************** */
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_get_an_Animal$$
CREATE PROCEDURE sp_get_an_Animal( 
	IN p_original_Animal_id VARCHAR(10)
) 
BEGIN
	SELECT
        Animal_id,
        Animal_name,
        Animal_species,
        Animal_gender,
        Animal_age,
        Animal_fixed,
        Animal_legs,
        Animal_weight,
        Animal_date_added,
        Animal_last_feeding_time
	FROM Animal
    WHERE Animal_id = p_original_Animal_id;
END$$
DELIMITER ;

/* *****************************************************************************
	Add some data
***************************************************************************** */
CALL sp_add_Animal('1','Hank', 'dog', 'Male', 4, True, 4, '30.42', '2019-04-21', '2008-11-11 13:23:44');
CALL sp_add_Animal('2','Sammy', 'dog', 'Female', 4, False, 4, '120.53', '2007-07-18', '2008-11-11 13:23:44' );
CALL sp_add_Animal('3','Ellie', 'dog', 'Female', 3, False, 4, '70.32', '2015-01-18', '2008-11-11 13:23:44');

/* *****************************************************************************
                               END OF SCRIPT	
***************************************************************************** */
call sp_get_an_Animal('1');
