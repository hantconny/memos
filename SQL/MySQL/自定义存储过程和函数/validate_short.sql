create function fn_offsetMonth (offsetMonth INT) 
returns date
begin

	declare currentDate date default CURRENT_DATE;
	declare resultDate date default CURRENT_DATE;
	
	set resultDate = DATE_ADD(CURRENT_DATE, INTERVAL offsetMonth MONTH);
	
	return resultDate;
	
end //

create function fn_validateShort(str varchar(50))
returns int
begin
	
	declare iResult int default 0;
	declare num1 int default 0;
	declare num2 int default 0;
	declare num3 int default 0;
	declare strRes varchar(50) default '';
	
	select str REGEXP '^[0-9]+[/]{1}[0-9]+[/]{1}[0-9]+$' INTO iResult;
	
	if iResult != 1 then return 0;
	end if;
	
	return 1;
	
end //

create procedure xp_calDateOne (IN str varchar(50)) 
top: begin
	declare iResult int default 0;
	declare offsetMonth varchar(50) default '';
	declare idx int default 0;
	
	set iResult = fn_validateShort(str);
	
	if iResult != 1 then select 'format error';
		leave top;
	end if;
	
	set idx = INSTR(str, '/');
	set offsetMonth = substring(str, 1, idx - 1);
	
	select fn_offsetMonth(offsetMonth);
	
end //


create procedure xp_calDateTwo (IN str varchar(50)) 
top: begin
	declare iResult int default 0;
	declare offsetMonth varchar(50) default '';
	declare idx int default 0;
	
	set iResult = fn_validateShort(str);
	
	if iResult != 1 then select 'format error';
		leave top;
	end if;
	
	set idx = INSTR(str, '/');
	set str = substring(str, idx + 1, length(str));
	
	set idx = INSTR(str, '/');
	set offsetMonth = substring(str, 1, idx - 1);
	
	select fn_offsetMonth(offsetMonth);
	
end //

create procedure xp_calDateThree (IN str varchar(50)) 
top: begin
	declare iResult int default 0;
	declare offsetMonth varchar(50) default '';
	declare idx int default 0;
	
	set iResult = fn_validateShort(str);
	
	if iResult != 1 then select 'format error';
		leave top;
	end if;
	
	set idx = INSTR(str, '/');
	set str = substring(str, idx + 1, length(str));
	select str;
	
	set idx = INSTR(str, '/');
	set str = substring(str, idx + 1, length(str));
	select str;
	
	set offsetMonth = substring(str, 1, idx - 1);
	
	select fn_offsetMonth(offsetMonth);
	
end //