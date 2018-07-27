package org.crap.data.dao.sql.util;

import org.crap.data.dao.sql.mapper.AbstractObjectRowMapper;
import org.crap.data.dao.sql.orm.dao.ICommonDao;
import org.crap.data.dao.sql.util.sql.support.Profile;
import org.crap.data.dao.sql.util.sql.support.QueryBuilder;
import org.crap.jrain.core.bean.result.criteria.DataResult;
import org.springframework.jdbc.core.RowMapper;

public interface IServiceDao extends ICommonDao {
	
	public DataResult queryForArrayDataResult(String sql);
	
	public DataResult queryForArrayDataResult(String sql, Object[] sqlParams);
	
	public DataResult queryForArrayDataResult(String sql, String countSql, Profile profile);
	
	public DataResult queryForArrayDataResult(String sql, String countSql, Object[] sqlParams, Profile profile);
	
	public DataResult queryForArrayDataResult(QueryBuilder builder);
	
	
	
	public DataResult queryForMapDataResult(String sql);
	
	public DataResult queryForMapDataResult(String sql, Object[] sqlParams);
	
	public DataResult queryForMapDataResult(String sql, String countSql, Profile profile);
	
	public DataResult queryForMapDataResult(String sql, String countSql, Object[] sqlParams, Profile profile);
	
	public DataResult queryForMapDataResult(QueryBuilder builder);
	
	
	
	public DataResult queryForDataResult(Class<?> entity);
	
	public DataResult queryForDataResult(Class<?> entity, Profile profile);
	
	public DataResult queryForDataResult(String sql, Class<?> entity);
	
	public DataResult queryForDataResult(String sql, Class<?> entity, Object[] sqlParams);
	
	public DataResult queryForDataResult(String sql, String countSql, Class<?> entity, Profile profile);
	
	public DataResult queryForDataResult(String sql, String countSql, Class<?> entity, Object[] sqlParams, Profile profile);
	
	public DataResult queryForDataResult(QueryBuilder builder, Class<?> entity);
	
	
	
	public DataResult queryForDataResult(AbstractObjectRowMapper<?> rowMapper);
	
	public DataResult queryForDataResult(AbstractObjectRowMapper<?> rowMapper, Profile profile);
	
	public DataResult queryForDataResult(String sql, RowMapper<?> rowMapper);
	
	public DataResult queryForDataResult(String sql, RowMapper<?> rowMapper, Object[] sqlParams);
	
	public DataResult queryForDataResult(String sql, String countSql, RowMapper<?> rowMapper, Profile profile);
	
	public DataResult queryForDataResult(String sql, String countSql, RowMapper<?> rowMapper, Object[] sqlParams, Profile profile);
	
	public DataResult queryForDataResult(QueryBuilder builder, RowMapper<?> rowMapper);
}
