create function fn_validateLong(str varchar(50))
returns int
begin
	
	declare iResult int default 0;
	declare num1 int default 0;
	declare num2 int default 0;
	declare num3 int default 0;
	declare num4 int default 0;
	declare num5 int default 0;
	declare num6 int default 0;
	declare sumRes int default 0;
	declare idx int default 0;
	
	select str REGEXP '^[0-9]+[/]{1}[0-9]+[/]{1}[0-9]+[/]{2}[0-9]+[/]{1}[0-9]+[/]{1}[0-9]+$' INTO iResult;
	
	if iResult != 1 then return 0;
	end if;
	
	set idx = INSTR(str, '/');
	set num1 = cast(substring(str, 1, idx - 1) as signed integer);
	
	set str = substring(str, idx + 1, length(str) - idx);
	set idx = INSTR(str, '/');
	set num2 = cast(substring(str, 1, idx - 1) as signed integer);
	
	set str = substring(str, idx + 1, length(str) - idx);
	set idx = INSTR(str, '/');
	set num3 = cast(substring(str, 1, idx - 1) as signed integer);
	
	set idx = INSTR(str, '/');
	set str = substring(str, idx + 2, length(str) - idx);
	set idx = INSTR(str, '/');
	set num4 = cast(substring(str, 1, idx - 1) as signed integer);
	
	set str = substring(str, idx + 1, length(str) - idx);
	set idx = INSTR(str, '/');
	set num5 = cast(substring(str, 1, idx - 1) as signed integer);
	
	set str = substring(str, idx + 1, length(str) - idx);
	set num6 = cast(str as signed integer);
	
	set sumRes = num1 + num2 + num3 + num4 + num5 + num6;
	if sumRes != 10 then return 0;
	end if;
	
	return 1;
end //

create procedure xp_getSumOne(IN str varchar(50))
top: begin

	declare iResult int default 0;
	declare num1 int default 0;
	declare sumRes int default 0;
	declare idx int default 0;

	set iResult = fn_validateLong(str);

	if iResult != 1 then select 'format error';
		leave top;
	end if;

	set idx = INSTR(str, '/');
	set num1 = cast(substring(str, 1, idx - 1) as signed integer);
	set sumRes = num1;
	
	select sumRes;
	
end //

create procedure xp_getSumTwo(IN str varchar(50))
top: begin

	declare iResult int default 0;
	declare num1 int default 0;
	declare num2 int default 0;
	declare sumRes int default 0;
	declare idx int default 0;

	set iResult = fn_validateLong(str);

	if iResult != 1 then select 'format error';
		leave top;
	end if;

	set idx = INSTR(str, '/');
	set num1 = cast(substring(str, 1, idx - 1) as signed integer);
	
	set str = substring(str, idx + 1, length(str) - idx);
	set idx = INSTR(str, '/');
	set num2 = cast(substring(str, 1, idx - 1) as signed integer);
	
	set sumRes = num1 + num2;
	
	select sumRes;
	
end //


create procedure xp_getSumThree(IN str varchar(50))
top: begin

	declare iResult int default 0;
	declare num1 int default 0;
	declare num2 int default 0;
	declare num3 int default 0;
	declare sumRes int default 0;
	declare idx int default 0;

	set iResult = fn_validateLong(str);

	if iResult != 1 then select 'format error';
		leave top;
	end if;

	set idx = INSTR(str, '/');
	set num1 = cast(substring(str, 1, idx - 1) as signed integer);
	
	set str = substring(str, idx + 1, length(str) - idx);
	set idx = INSTR(str, '/');
	set num2 = cast(substring(str, 1, idx - 1) as signed integer);
	
	set str = substring(str, idx + 1, length(str) - idx);
	set idx = INSTR(str, '/');
	set num3 = cast(substring(str, 1, idx - 1) as signed integer);
	
	set sumRes = num1 + num2 + num3;
	
	select sumRes;
	
end //


create procedure xp_getSumFour(IN str varchar(50))
top: begin

	declare iResult int default 0;
	declare num1 int default 0;
	declare num2 int default 0;
	declare num3 int default 0;
	declare num4 int default 0;
	declare sumRes int default 0;
	declare idx int default 0;

	set iResult = fn_validateLong(str);

	if iResult != 1 then select 'format error';
		leave top;
	end if;

	set idx = INSTR(str, '/');
	set num1 = cast(substring(str, 1, idx - 1) as signed integer);
	
	set str = substring(str, idx + 1, length(str) - idx);
	set idx = INSTR(str, '/');
	set num2 = cast(substring(str, 1, idx - 1) as signed integer);
	
	set str = substring(str, idx + 1, length(str) - idx);
	set idx = INSTR(str, '/');
	set num3 = cast(substring(str, 1, idx - 1) as signed integer);
	
	set idx = INSTR(str, '/');
	set str = substring(str, idx + 2, length(str) - idx);
	set idx = INSTR(str, '/');
	set num4 = cast(substring(str, 1, idx - 1) as signed integer);
	
	set sumRes = num1 + num2 + num3 + num4;
	
	select sumRes;
	
end //



create procedure xp_getSumFive(IN str varchar(50))
top: begin

	declare iResult int default 0;
	declare num1 int default 0;
	declare num2 int default 0;
	declare num3 int default 0;
	declare num4 int default 0;
	declare num5 int default 0;
	declare sumRes int default 0;
	declare idx int default 0;

	set iResult = fn_validateLong(str);

	if iResult != 1 then select 'format error';
		leave top;
	end if;

	set idx = INSTR(str, '/');
	set num1 = cast(substring(str, 1, idx - 1) as signed integer);
	
	set str = substring(str, idx + 1, length(str) - idx);
	set idx = INSTR(str, '/');
	set num2 = cast(substring(str, 1, idx - 1) as signed integer);
	
	set str = substring(str, idx + 1, length(str) - idx);
	set idx = INSTR(str, '/');
	set num3 = cast(substring(str, 1, idx - 1) as signed integer);
	
	set idx = INSTR(str, '/');
	set str = substring(str, idx + 2, length(str) - idx);
	set idx = INSTR(str, '/');
	set num4 = cast(substring(str, 1, idx - 1) as signed integer);
	
	set str = substring(str, idx + 1, length(str) - idx);
	set idx = INSTR(str, '/');
	set num5 = cast(substring(str, 1, idx - 1) as signed integer);
	
	set sumRes = num1 + num2 + num3 + num4 + num5;
	
	select sumRes;
	
end //



create procedure xp_getSumSix(IN str varchar(50))
top: begin

	declare iResult int default 0;
	declare num1 int default 0;
	declare num2 int default 0;
	declare num3 int default 0;
	declare num4 int default 0;
	declare num5 int default 0;
	declare num6 int default 0;
	declare sumRes int default 0;
	declare idx int default 0;

	set iResult = fn_validateLong(str);

	if iResult != 1 then select 'format error';
		leave top;
	end if;

	set idx = INSTR(str, '/');
	set num1 = cast(substring(str, 1, idx - 1) as signed integer);
	
	set str = substring(str, idx + 1, length(str) - idx);
	set idx = INSTR(str, '/');
	set num2 = cast(substring(str, 1, idx - 1) as signed integer);
	
	set str = substring(str, idx + 1, length(str) - idx);
	set idx = INSTR(str, '/');
	set num3 = cast(substring(str, 1, idx - 1) as signed integer);
	
	set idx = INSTR(str, '/');
	set str = substring(str, idx + 2, length(str) - idx);
	set idx = INSTR(str, '/');
	set num4 = cast(substring(str, 1, idx - 1) as signed integer);
	
	set str = substring(str, idx + 1, length(str) - idx);
	set idx = INSTR(str, '/');
	set num5 = cast(substring(str, 1, idx - 1) as signed integer);
	
	set str = substring(str, idx + 1, length(str) - idx);
	set num6 = cast(str as signed integer);
	
	set sumRes = num1 + num2 + num3 + num4 + num5 + num6;
	
	select sumRes;
	
end //