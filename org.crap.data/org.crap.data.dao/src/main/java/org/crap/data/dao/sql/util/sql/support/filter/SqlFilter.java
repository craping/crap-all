package org.crap.data.dao.sql.util.sql.support.filter;

/**
 * @since JDK 1.7
 * 
 * @author Crap
 * 
 * @version 1.4.3
 * 
 * 
 * 
 * @description SQL过滤器
 */
public interface SqlFilter {

	public String toSql();
	
	public String toClearSql();
	
	public Object[] getValues();
}
