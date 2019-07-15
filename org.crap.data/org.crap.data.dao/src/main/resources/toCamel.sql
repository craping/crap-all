CREATE FUNCTION `toCamel`(`tablename` varchar(50)) RETURNS varchar(50) CHARSET utf8
BEGIN
	#Routine body goes here...
	declare i int;
	declare entityName varchar(50) DEFAULT '';
	declare c1 VARCHAR(2);
	declare c2 VARCHAR(2);

	set i=1;
	WHILE LENGTH(tablename) > 0 and i <= LENGTH(tablename) DO
		set c1 = SUBSTR(tablename,i,1);
		set c2 = SUBSTR(tablename,i-1,1);

		IF c1!='_' THEN
			SET entityName = concat(entityName, if(c2='_' or i=1,UPPER(c1), c1));
		END IF;
		
		set i = i+1;
	END WHILE;
	RETURN entityName;
END