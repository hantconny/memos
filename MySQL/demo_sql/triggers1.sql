-- Run in mysql command line
-- INSERT TRIGGER START_DATE

DELIMITER //
CREATE TRIGGER tg_category_start_date
BEFORE INSERT ON categories
FOR EACH ROW
BEGIN
	set new.start_date=now();
END; //

CREATE TRIGGER tg_charge_codes_start_date
BEFORE INSERT ON charge_codes
FOR EACH ROW
BEGIN
	set new.start_date=now();
END; //

CREATE TRIGGER tg_function_permission_start_date
BEFORE INSERT ON function_permission
FOR EACH ROW
BEGIN
	set new.start_date=now();
END; //

CREATE TRIGGER tg_functions_start_date
BEFORE INSERT ON functions
FOR EACH ROW
BEGIN
	set new.start_date=now();
END; //

CREATE TRIGGER tg_project_charge_rate_start_date
BEFORE INSERT ON project_charge_rate
FOR EACH ROW
BEGIN
	set new.start_date=now();
END; //

CREATE TRIGGER tg_project_users_start_date
BEFORE INSERT ON project_users
FOR EACH ROW
BEGIN
	set new.start_date=now();
END; //

CREATE TRIGGER tg_user_claim_status_start_date
BEFORE INSERT ON user_claim_status
FOR EACH ROW
BEGIN
	set new.start_date=now();
END; //

CREATE TRIGGER tg_task_status_start_date
BEFORE INSERT ON task_status
FOR EACH ROW
BEGIN
	set new.start_date=now();
END; //

CREATE TRIGGER tg_project_status_start_date
BEFORE INSERT ON project_status
FOR EACH ROW
BEGIN
	set new.start_date=now();
END; //

CREATE TRIGGER tg_user_group_rel_start_date
BEFORE INSERT ON user_group_rel
FOR EACH ROW
BEGIN
	set new.start_date=now();
END; //

CREATE TRIGGER tg_users_start_date
BEFORE INSERT ON users
FOR EACH ROW
BEGIN
	set new.start_date=now();
END; //

CREATE TRIGGER tg_user_rel_start_date
BEFORE INSERT ON user_rel
FOR EACH ROW
BEGIN
	set new.start_date=now();
END; //

CREATE TRIGGER tg_rel_type_start_date
BEFORE INSERT ON rel_type
FOR EACH ROW
BEGIN
	set new.start_date=now();
END; //

CREATE TRIGGER tg_common_codes_start_date
BEFORE INSERT ON common_codes
FOR EACH ROW
BEGIN
	set new.start_date=now();
END; //

CREATE TRIGGER tg_common_code_user_rel_start_date
BEFORE INSERT ON common_code_user_rel
FOR EACH ROW
BEGIN
	set new.start_date=now();
END; //