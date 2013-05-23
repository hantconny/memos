-- Turn on Event
SET GLOBAL event_scheduler = ON;

DELIMITER //
CREATE EVENT e_yearly 
	ON SCHEDULE 
		EVERY 1 YEAR
	DO
		UPDATE common_code_user_rel 
			SET remain_hours=remain_hours+5, respective_year=YEAR(NOW()) 
			WHERE common_code_id='2';
//
