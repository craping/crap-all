package org.crap.data.dao.sql.mapper.support;

import java.sql.ResultSet;

import org.crap.data.dao.sql.mapper.AbstractObjectRowMapper;

/**
 * @since JDK 1.7
 * 
 * @author Crap
 * 
 * @version 1.4.3
 * 
 * @copyright 2013 - 2014 深圳亿码擎天科技有限公司 All rights reserved.
 */
public class ObjectRowMapper extends AbstractObjectRowMapper<Object> {

	public ObjectRowMapper(Class<?> mappedClass) {
		super(mappedClass);
	}

	@Override
	protected void mapRowHandler(ResultSet rs, Object entity) {}


}
