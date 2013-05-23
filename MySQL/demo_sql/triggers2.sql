-- when manager approve user claim hour
-- sub hour from table project_hour
DELIMITER //
CREATE TRIGGER tg_user_claim_hour_approve_hour
AFTER UPDATE ON user_claim_hour
FOR EACH ROW
BEGIN
IF new.status_id=3 then
set @claim_hour = new.claim_hour;
set @project_id = new.project_id;
UPDATE project_hours SET approved_hours=approved_hours + @claim_hour, remained_hours=remained_hours - @claim_hour WHERE project_id=@project_id;
END IF;
END//


CREATE TRIGGER tg_pcr_approve_pcr
AFTER UPDATE ON pcr
FOR EACH ROW
BEGIN
IF new.status_id=3 then
set @apply_hours = new.apply_hours;
set @project_id = new.project_id;
UPDATE project_hours SET remained_hours=remained_hours + @apply_hours, total_hours=total_hours + @apply_hours WHERE project_id=@project_id;
END IF;
END//




