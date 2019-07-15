CREATE FUNCTION `mybatisXML`(`dbname` varchar(50)) RETURNS text CHARSET utf8
BEGIN
	#Routine body goes here...
	declare context text default '';
	declare tbl varchar(50);
	declare done boolean;

	declare contextCursor CURSOR FOR SELECT
  table_name
FROM
  INFORMATION_SCHEMA.TABLE_CONSTRAINTS
WHERE
  TABLE_SCHEMA = dbname;

	#select table_name from information_schema.tables where table_schema=dbname and table_type='base table';
	
	declare continue handler for NOT FOUND set done = true;

	open contextCursor;
	contextCursor:Loop
		fetch contextCursor into tbl;
		if done then
			leave contextCursor;  
		end if;
		
		set context = concat(context, '
<table tableName="',tbl,'" domainObjectName="',toCamel(tbl),'" 
	enableCountByExample="false" enableSelectByExample="false" 
	enableUpdateByExample="false" enableDeleteByExample="false">
</table>');
		set done = false;
	end Loop contextCursor;
	close contextCursor;

	RETURN context;
END