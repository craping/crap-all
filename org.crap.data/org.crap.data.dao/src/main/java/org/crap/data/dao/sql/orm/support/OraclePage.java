package org.crap.data.dao.sql.orm.support;

import org.crap.data.dao.sql.orm.IPage;

/** 
 * @project Crap
 * 
 * @author Crap
 * 
 * @Copyright 2013 - 2014 All rights reserved. 
 * 
 * @email 422655094@qq.com
 * 
 */
public class OraclePage implements IPage {

	public String getLimitString(String paramString, Integer paramInt1, Integer paramInt2) {
		if(paramInt2 == null)
			return paramString;
		
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT * FROM (SELECT ROWNUM row$number,temp.* from")
		.append("(").append(paramString).append(") temp")
		.append(") T  WHERE T.row$number BETWEEN ")
		.append(paramInt1).append(" AND ")
		.append(paramInt1 + paramInt2-1);
		return sql.toString();
	}
}
