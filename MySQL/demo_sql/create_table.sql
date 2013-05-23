CREATE DATABASE TMS;

USE TMS;

-- checked
-- Code Table Stedy ID BIGINT AUTO_INCREMENT
CREATE TABLE language_types (
	lang_id BIGINT AUTO_INCREMENT NOT NULL, -- 100, 200
	lang_code VARCHAR(120) NOT NULL, -- zh_CN, en_US
	lang_desc VARCHAR(120) NOT NULL, -- ÖÐÎÄ, English
	last_update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (lang_id)
);
ALTER TABLE language_types AUTO_INCREMENT = 100;

-- checked
-- Core Table Stedy ID BIGINT AUTO_INCREMENT
CREATE TABLE categories (
	category_id BIGINT AUTO_INCREMENT NOT NULL,
	category_code VARCHAR(50) NOT NULL, -- client, billable, internal
	category_desc VARCHAR(255) NOT NULL,
	lang_id BIGINT NOT NULL,
	start_date DATE NULL DEFAULT NULL,
	end_date DATE NULL DEFAULT NULL,
	last_update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (category_id)
);
ALTER TABLE categories AUTO_INCREMENT = 1000;


-- checked
-- Core Table Stedy ID BIGINT AUTO_INCREMENT
CREATE TABLE project_status (
	project_status_id BIGINT AUTO_INCREMENT NOT NULL,
	status_code VARCHAR(50) NOT NULL, -- applying, approved, rejected, generated, developing, finished
	status_desc VARCHAR(255) NOT NULL,
	lang_id BIGINT NOT NULL,
	start_date DATE NULL DEFAULT NULL,
	end_date DATE NULL DEFAULT NULL,
	last_update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (project_status_id)
);
ALTER TABLE project_status AUTO_INCREMENT = 1000;


-- checked
-- Core Table Stedy ID BIGINT AUTO_INCREMENT
CREATE TABLE user_claim_status (
	user_claim_status_id BIGINT AUTO_INCREMENT NOT NULL,
	status_code VARCHAR(50) NOT NULL, -- filled, applying, pre_approved, approved, rejected
	status_desc VARCHAR(255) NOT NULL,
	lang_id BIGINT NOT NULL,
	start_date DATE NULL DEFAULT NULL,
	end_date DATE NULL DEFAULT NULL,
	last_update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (user_claim_status_id)
);
ALTER TABLE user_claim_status AUTO_INCREMENT = 1000;


-- checked
-- Core Table Stedy ID BIGINT AUTO_INCREMENT
CREATE TABLE task_status (
	task_status_id BIGINT AUTO_INCREMENT NOT NULL,
	status_code VARCHAR(50) NOT NULL, -- applying, approved, rejected, generated, developing, finished
	status_desc VARCHAR(255) NOT NULL,
	lang_id BIGINT NOT NULL,
	start_date DATE NULL DEFAULT NULL,
	end_date DATE NULL DEFAULT NULL,
	last_update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (task_status_id)
);
ALTER TABLE task_status AUTO_INCREMENT = 1000;


-- checked
-- Core Table Stedy ID BIGINT AUTO_INCREMENT
-- Group class should has Set<FunctionPermission>
CREATE TABLE groups (
	group_id BIGINT AUTO_INCREMENT NOT NULL,
	group_code VARCHAR(50) NOT NULL,
	group_desc VARCHAR(255) NOT NULL,
	last_update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (group_id)
);
ALTER TABLE groups AUTO_INCREMENT = 1000;




-- Core Table Stedy ID UUID
-- Because function_code should be meaningful
-- Function class is atomic
CREATE TABLE functions (
	function_id VARCHAR(50) NOT NULL,
	function_parent_id VARCHAR(50) NOT NULL,
	function_code VARCHAR(50) NOT NULL,
	function_desc VARCHAR(255) NOT NULL,
	lang_id BIGINT NOT NULL,
	function_url TEXT NOT NULL,
	start_date DATE NULL DEFAULT NULL,
	end_date DATE NULL DEFAULT NULL,
	last_update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (function_id)
);




-- REL-ENTITY Table ID BIGINT AUTO_INCREMENT
-- Group class has Set<FunctionPermission>
-- FunctionPermission has Function
CREATE TABLE function_permission (
	function_permission_id BIGINT AUTO_INCREMENT NOT NULL,
	function_id VARCHAR(50) NOT NULL,
	group_id BIGINT NOT NULL,
	permission VARCHAR(50) NOT NULL,
	start_date DATE NULL DEFAULT NULL,
	end_date DATE NULL DEFAULT NULL,
	last_update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (function_permission_id)
);




-- Core Table active ID UUID
-- User class should has Set<UserGroupRel>
-- UserGroupRel class should has Group
-- User class should has Set<CommonCodeUserRel>
-- CommonCodeUserRel should has CommonCode
CREATE TABLE users (
	user_id VARCHAR(50) NOT NULL,
	user_name VARCHAR(255) NOT NULL,
	user_serial_number BIGINT UNIQUE AUTO_INCREMENT NOT NULL,
	user_password VARCHAR(50) NOT NULL,
	start_date DATE NULL DEFAULT NULL,
	end_date DATE NULL DEFAULT NULL,
	last_update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (user_id)
);
ALTER TABLE users AUTO_INCREMENT = 150000;





-- REL-ENTITY Table ID BIGINT AUTO_INCREMENT
CREATE TABLE user_group_rel (
	user_group_rel_id BIGINT AUTO_INCREMENT NOT NULL,
	user_id VARCHAR(50) NOT NULL,
	group_id BIGINT NOT NULL,
	start_date DATE NULL DEFAULT NULL,
	end_date DATE NULL DEFAULT NULL,
	last_update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (user_group_rel_id)
);




-- user relationship
CREATE TABLE user_rel (
	user_rel_id BIGINT AUTO_INCREMENT NOT NULL,
	from_user_id VARCHAR(50) NOT NULL, -- lower level like employee
	to_user_id VARCHAR(50) NOT NULL, -- higher level like boss
	rel_type_id BIGINT NOT NULL,
	start_date DATE NULL DEFAULT NULL,
	end_date DATE NULL DEFAULT NULL,
	last_update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (user_rel_id)
);



-- relation type
CREATE TABLE rel_type(
	rel_type_id BIGINT AUTO_INCREMENT NOT NULL,
	rel_type_code VARCHAR(50) NOT NULL,  -- HR, Manager
	rel_type_desc VARCHAR(255) NOT NULL,
	start_date DATE NULL DEFAULT NULL,
	end_date DATE NULL DEFAULT NULL,
	last_update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (rel_type_id)
);
ALTER TABLE rel_type AUTO_INCREMENT = 1000;




-- Core Table Stedy ID BIGINT AUTO_INCREMENT
CREATE TABLE common_codes (
	common_code_id BIGINT AUTO_INCREMENT NOT NULL,
	common_code VARCHAR(50) NOT NULL,
	common_code_desc VARCHAR(255) NOT NULL,
	start_date DATE NULL DEFAULT NULL,
	end_date DATE NULL DEFAULT NULL,
	last_update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (common_code_id)
);





-- REL-ENTITY Table ID BIGINT AUTO_INCREMENT
CREATE TABLE common_code_user_rel (
	common_code_user_rel_id BIGINT AUTO_INCREMENT NOT NULL,
	common_code_id BIGINT NOT NULL,
	user_id VARCHAR(50) NOT NULL,
	total_hours BIGINT NOT NULL,
	years VARCHAR(4) NOT NULL,
	start_date DATE NULL DEFAULT NULL,
	end_date DATE NULL DEFAULT NULL,
	last_update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (common_code_user_rel_id)
);


-- 
CREATE TABLE invoices (
	invoice_id BIGINT AUTO_INCREMENT NOT NULL,
	project_id VARCHAR(50) NOT NULL,
	invoice_uri TEXT NOT NULL,
	last_update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (invoice_id)
);
ALTER TABLE invoices AUTO_INCREMENT = 10000;




-- 
CREATE TABLE customers (
	customer_id BIGINT AUTO_INCREMENT NOT NULL,
	customer_name VARCHAR(50) NOT NULL,
	last_update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (customer_id)
);
ALTER TABLE customers AUTO_INCREMENT = 10000;




-- REL-ENTITY Table ID BIGINT AUTO_INCREMENT
CREATE TABLE project_charge_rate (
	project_charge_rate_id BIGINT AUTO_INCREMENT NOT NULL,
	project_id VARCHAR(50) NOT NULL,
	charge_code_id BIGINT NOT NULL,
	rate_per_hour BIGINT NOT NULL,
	start_date DATE NULL DEFAULT NULL,
	end_date DATE NULL DEFAULT NULL,
	last_update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (project_charge_rate_id)
);



-- Core Table Stedy ID BIGINT AUTO_INCREMENT
CREATE TABLE charge_codes (
	charge_code_id BIGINT AUTO_INCREMENT NOT NULL,
	charge_code VARCHAR(50) NOT NULL,
	charge_code_desc VARCHAR(255) NOT NULL,
	start_date DATE NULL DEFAULT NULL,
	end_date DATE NULL DEFAULT NULL,
	last_update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (charge_code_id)
);





-- REL-ENTITY Table
CREATE TABLE project_users (
	project_users_id BIGINT AUTO_INCREMENT NOT NULL,
	project_id VARCHAR(50) NOT NULL,
	user_id VARCHAR(50) NOT NULL,
	project_charge_rate_id BIGINT NOT NULL,
	start_date DATE NULL DEFAULT NULL,
	end_date DATE NULL DEFAULT NULL,
	last_update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (project_users_id)
);



-- Main Table ID UUID
CREATE TABLE projects (
	project_id VARCHAR(50) NOT NULL,
	project_code VARCHAR(255) NOT NULL DEFAULT 'PENDING',
	-- project_parent_id VARCHAR(50) NULL,
	project_root_id VARCHAR(50) NULL, -- only for PCR
	project_name VARCHAR(255) NOT NULL,
	project_desc TEXT NOT NULL,
	inner_type_id BIGINT NOT NULL, -- Project stands for project, PCR stands for PCR
	customer_id BIGINT NOT NULL,
	applicant_id VARCHAR(50) NOT NULL,
	approver_id VARCHAR(50) NULL DEFAULT NULL,
	project_status_id BIGINT NOT NULL,
	reject_reason TEXT NULL DEFAULT NULL,
	category_id BIGINT NOT NULL,
	invoice_frequency VARCHAR(50) NULL DEFAULT '7',
	total_hours BIGINT NOT NULL,
	start_date DATETIME NULL DEFAULT NULL,
	end_date DATETIME NULL DEFAULT NULL,
	last_update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (project_id)
);






CREATE TABLE tasks (
	task_id VARCHAR(50) NOT NULL,
	task_code VARCHAR(255) NOT NULL,
	task_desc VARCHAR(255) NOT NULL,
	task_parent_id VARCHAR(50) NULL,
	task_root_id VARCHAR(50) NULL,
	project_id VARCHAR(50) NOT NULL,
	manager_id VARCHAR(50) NOT NULL,
	task_status_id BIGINT NOT NULL,
	total_hours BIGINT NOT NULL,
	start_date DATE NULL DEFAULT NULL,
	end_date DATE NULL DEFAULT NULL,
	last_update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (task_id)
);









-- Sub Table
CREATE TABLE user_claim_hour (
	user_claim_hour_id VARCHAR(50) NOT NULL,
	claim_code_id VARCHAR(50) NOT NULL, -- taskId, common_code_id
	charge_code_id BIGINT NULL DEFAULT NULL, -- if null claim common_code, if non-null claim task
	applicant_id VARCHAR(50) NOT NULL,
	claim_hour BIGINT NOT NULL,
	claim_desc TEXT NOT NULL,
	claim_date DATE NOT NULL,
	approver_id VARCHAR(50) NULL DEFAULT NULL,
	user_claim_status_id BIGINT NOT NULL,
	reject_reason TEXT NULL DEFAULT NULL,
	last_update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (user_claim_hour_id)
);






-- checked
CREATE TABLE reviews (
	review_id VARCHAR(50) NOT NULL,
	project_id VARCHAR(50) NOT NULL,
	user_id VARCHAR(50) NOT NULL,
	review_by_id VARCHAR(50) NOT NULL,
	review TEXT NOT NULL,
	review_date DATE NOT NULL,
	last_update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (review_id)
);



CREATE TABLE inner_types (
	inner_type_id BIGINT AUTO_INCREMENT NOT NULL,
	inner_type_code VARCHAR(50) NOT NULL,
	inner_type_desc VARCHAR(255) NOT NULL,
	last_update_date TIMESTAMP NOT NULL,
	PRIMARY KEY (inner_type_id)
);



ALTER TABLE user_rel ADD CONSTRAINT user_rel_users_FK1 FOREIGN KEY (to_user_id)
	REFERENCES users (user_id)
	ON DELETE RESTRICT;

ALTER TABLE project_charge_rate ADD CONSTRAINT project_charge_rate_projects_FK FOREIGN KEY (project_id)
	REFERENCES projects (project_id)
	ON DELETE RESTRICT;

ALTER TABLE tasks ADD CONSTRAINT tasks_users_FK FOREIGN KEY (manager_id)
	REFERENCES users (user_id)
	ON DELETE RESTRICT;

ALTER TABLE project_users ADD CONSTRAINT project_users_projects_FK FOREIGN KEY (project_id)
	REFERENCES projects (project_id)
	ON DELETE RESTRICT;

ALTER TABLE projects ADD CONSTRAINT projects_users_FK FOREIGN KEY (applicant_id)
	REFERENCES users (user_id)
	ON DELETE RESTRICT;

ALTER TABLE user_rel ADD CONSTRAINT user_rel_rel_type_FK FOREIGN KEY (rel_type_id)
	REFERENCES rel_type (rel_type_id)
	ON DELETE RESTRICT;

ALTER TABLE tasks ADD CONSTRAINT tasks_projects_FK FOREIGN KEY (project_id)
	REFERENCES projects (project_id)
	ON DELETE RESTRICT;

ALTER TABLE user_rel ADD CONSTRAINT user_rel_users_FK FOREIGN KEY (from_user_id)
	REFERENCES users (user_id)
	ON DELETE RESTRICT;

ALTER TABLE user_group_rel ADD CONSTRAINT user_group_rel_groups_FK FOREIGN KEY (group_id)
	REFERENCES groups (group_id)
	ON DELETE RESTRICT;

ALTER TABLE projects ADD CONSTRAINT projects_users_FK1 FOREIGN KEY (approver_id)
	REFERENCES users (user_id)
	ON DELETE RESTRICT;

ALTER TABLE reviews ADD CONSTRAINT reviews_users_FK FOREIGN KEY (user_id)
	REFERENCES users (user_id)
	ON DELETE RESTRICT;
	
ALTER TABLE reviews ADD CONSTRAINT reviews_users_FK1 FOREIGN KEY (review_by_id)
	REFERENCES users (user_id)
	ON DELETE RESTRICT;	

ALTER TABLE projects ADD CONSTRAINT projects_project_status_FK FOREIGN KEY (project_status_id)
	REFERENCES project_status (project_status_id)
	ON DELETE RESTRICT;

ALTER TABLE functions ADD CONSTRAINT functions_language_types_FK FOREIGN KEY (lang_id)
	REFERENCES language_types (lang_id)
	ON DELETE RESTRICT;

ALTER TABLE project_charge_rate ADD CONSTRAINT project_charge_rate_charge_codes_FK FOREIGN KEY (charge_code_id)
	REFERENCES charge_codes (charge_code_id)
	ON DELETE RESTRICT;

ALTER TABLE project_users ADD CONSTRAINT project_users_users_FK FOREIGN KEY (user_id)
	REFERENCES users (user_id)
	ON DELETE RESTRICT;

ALTER TABLE projects ADD CONSTRAINT projects_customers_FK FOREIGN KEY (customer_id)
	REFERENCES customers (customer_id)
	ON DELETE RESTRICT;

ALTER TABLE user_claim_status ADD CONSTRAINT user_claim_status_language_types_FK FOREIGN KEY (lang_id)
	REFERENCES language_types (lang_id)
	ON DELETE RESTRICT;

ALTER TABLE categories ADD CONSTRAINT categories_language_types_FK FOREIGN KEY (lang_id)
	REFERENCES language_types (lang_id)
	ON DELETE RESTRICT;

ALTER TABLE user_claim_hour ADD CONSTRAINT user_claim_hour_user_claim_status_FK FOREIGN KEY (user_claim_status_id)
	REFERENCES user_claim_status (user_claim_status_id)
	ON DELETE RESTRICT;

ALTER TABLE user_claim_hour ADD CONSTRAINT user_claim_hour_users_FK1 FOREIGN KEY (applicant_id)
	REFERENCES users (user_id)
	ON DELETE RESTRICT;

ALTER TABLE user_group_rel ADD CONSTRAINT user_group_rel_users_FK FOREIGN KEY (user_id)
	REFERENCES users (user_id)
	ON DELETE RESTRICT;

ALTER TABLE common_code_user_rel ADD CONSTRAINT common_code_user_rel_common_codes_FK FOREIGN KEY (common_code_id)
	REFERENCES common_codes (common_code_id)
	ON DELETE RESTRICT;

ALTER TABLE projects ADD CONSTRAINT projects_categories_FK FOREIGN KEY (category_id)
	REFERENCES categories (category_id)
	ON DELETE RESTRICT;

ALTER TABLE user_claim_hour ADD CONSTRAINT user_claim_hour_users_FK FOREIGN KEY (approver_id)
	REFERENCES users (user_id)
	ON DELETE RESTRICT;

ALTER TABLE invoices ADD CONSTRAINT invoices_projects_FK FOREIGN KEY (project_id)
	REFERENCES projects (project_id)
	ON DELETE RESTRICT;

ALTER TABLE tasks ADD CONSTRAINT tasks_task_status_FK FOREIGN KEY (task_status_id)
	REFERENCES task_status (task_status_id)
	ON DELETE RESTRICT;

ALTER TABLE function_permission ADD CONSTRAINT function_permission_groups_FK FOREIGN KEY (group_id)
	REFERENCES groups (group_id)
	ON DELETE RESTRICT;

ALTER TABLE projects ADD CONSTRAINT projects_inner_types_FK FOREIGN KEY (inner_type_id)
	REFERENCES inner_types (inner_type_id)
	ON DELETE RESTRICT;

ALTER TABLE function_permission ADD CONSTRAINT function_permission_functions_FK FOREIGN KEY (function_id)
	REFERENCES functions (function_id)
	ON DELETE RESTRICT;

ALTER TABLE project_status ADD CONSTRAINT project_status_language_types_FK FOREIGN KEY (lang_id)
	REFERENCES language_types (lang_id)
	ON DELETE RESTRICT;

ALTER TABLE common_code_user_rel ADD CONSTRAINT common_code_user_rel_users_FK FOREIGN KEY (user_id)
	REFERENCES users (user_id)
	ON DELETE RESTRICT;

ALTER TABLE task_status ADD CONSTRAINT task_status_language_types_FK FOREIGN KEY (lang_id)
	REFERENCES language_types (lang_id)
	ON DELETE RESTRICT;

ALTER TABLE project_users ADD CONSTRAINT project_users_project_charge_rate_FK FOREIGN KEY (project_charge_rate_id)
	REFERENCES project_charge_rate (project_charge_rate_id)
	ON DELETE RESTRICT;

ALTER TABLE reviews ADD CONSTRAINT reviews_projects_FK FOREIGN KEY (project_id)
	REFERENCES projects (project_id)
	ON DELETE RESTRICT;

ALTER TABLE user_claim_hour ADD CONSTRAINT user_claim_hour_charge_codes_FK FOREIGN KEY (charge_code_id)
	REFERENCES charge_codes (charge_code_id)
	ON DELETE RESTRICT;