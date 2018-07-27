package org.crap.data.dao.sql.orm.dao;

import org.crap.data.dao.sql.orm.IMapper;
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
public abstract interface IBaseDao {
	
	public abstract IPage getPage();
	
	public abstract IMapper getMapper();
}
