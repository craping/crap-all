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
 * 
 */
public class ObjectRowMapper extends AbstractObjectRowMapper<Object> {

	public ObjectRowMapper(Class<?> mappedClass) {
		super(mappedClass);
	}

	@Override
	protected void mapRowHandler(ResultSet rs, Object entity) {}


}
