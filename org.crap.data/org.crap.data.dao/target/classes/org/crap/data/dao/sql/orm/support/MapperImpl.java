package org.crap.data.dao.sql.orm.support;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.crap.data.dao.sql.mapper.support.ArrayMapper;
import org.crap.data.dao.sql.mapper.support.ObjectRowMapper;
import org.crap.data.dao.sql.orm.IMapper;
import org.crap.data.dao.sql.service.Mapper;
import org.crap.jrain.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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
@Repository
public class MapperImpl implements IMapper {

	@Autowired
	private SqlSession sqlSession;

	@Autowired
	private JdbcTemplate jdbcTemplate;


	@Value("${mapperPackage}")
	private String mapperPackage;

	/**
	 * entity execute methods
	 */
	@SuppressWarnings("unchecked")
	public Serializable save(Object paramObject) {
		String mapperName = mapperPackage + "." + paramObject.getClass().getSimpleName() + "Mapper";
		Class<Mapper<Object>> clazz = null;
		try {
			clazz = (Class<Mapper<Object>>) Class.forName(mapperName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Mapper<Object> mapper = sqlSession.getMapper(clazz);
		return mapper.insertSelective(paramObject);
	}

	@SuppressWarnings("unchecked")
	public Serializable[] save(Object... paramObjects) {
		Serializable[] sArray = new Serializable[paramObjects.length];
		String mapperName = mapperPackage + "." + paramObjects[0].getClass().getSimpleName() + "Mapper";
		
		Class<Mapper<Object>> clazz = null;
		try {
			clazz = (Class<Mapper<Object>>) Class.forName(mapperName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Mapper<Object> mapper = sqlSession.getMapper(clazz);
		for (int i = 0; i < paramObjects.length; i++) {
			sArray[i] = mapper.insertSelective(paramObjects[i]);
		}
		return sArray;
	}

	public Serializable[] save(Collection<?> paramCollectionOfObject) {
		if(paramCollectionOfObject.size() > 0)
			return save(paramCollectionOfObject.toArray());
		else
			return new Serializable[]{};
	}

	/*public void saveOrUpdate(Object paramObject) {

		Session session = sessionFactory.getCurrentSession();
		sqlSession.merge(paramObject);
	}

	public void saveOrUpdate(Object... paramObjects) {
		Session session = sessionFactory.getCurrentSession();
		for (Object paramObject : paramObjects) {
			sqlSession.merge(paramObject);
		}
	}

	public void saveOrUpdate(Collection<?> paramCollectionOfObject) {
		Session session = sessionFactory.getCurrentSession();
		for (Object paramObject : paramCollectionOfObject) {
			sqlSession.merge(paramObject);
		}
	}*/

	@SuppressWarnings("unchecked")
	public void update(Object paramObject) {
		String mapperName = mapperPackage + "." + paramObject.getClass().getSimpleName() + "Mapper";
		Class<Mapper<Object>> clazz = null;
		try {
			clazz = (Class<Mapper<Object>>) Class.forName(mapperName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Mapper<Object> mapper = sqlSession.getMapper(clazz);
		mapper.updateByPrimaryKeySelective(paramObject);
	}

	@SuppressWarnings("unchecked")
	public void update(Object... paramObjects) {
		String mapperName = mapperPackage + "." + paramObjects[0].getClass().getSimpleName() + "Mapper";

		Class<Mapper<Object>> clazz = null;
		try {
			clazz = (Class<Mapper<Object>>) Class.forName(mapperName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Mapper<Object> mapper = sqlSession.getMapper(clazz);
		for (Object paramObject : paramObjects) {
			mapper.updateByPrimaryKeySelective(paramObject);
		}
	}

	public void update(Collection<?> paramCollectionOfObject) {
		if(paramCollectionOfObject.size() > 0)
			update(paramCollectionOfObject.toArray());
	}

	@SuppressWarnings("unchecked")
	public void remove(Class<?> entity, Object paramObject) {
		String mapperName = mapperPackage + "." + entity.getSimpleName() + "Mapper";
		Class<Mapper<Object>> clazz = null;
		try {
			clazz = (Class<Mapper<Object>>) Class.forName(mapperName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Mapper<Object> mapper = sqlSession.getMapper(clazz);
		mapper.deleteByPrimaryKey(paramObject);
	}

	@SuppressWarnings("unchecked")
	public void remove(Class<?> entity, Object... paramObjects) {
		String mapperName = mapperPackage + "." + entity.getSimpleName() + "Mapper";
		Class<Mapper<Object>> clazz = null;
		try {
			clazz = (Class<Mapper<Object>>) Class.forName(mapperName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Mapper<Object> mapper = sqlSession.getMapper(clazz);
		for (Object paramObject : paramObjects) {
			mapper.deleteByPrimaryKey(paramObject);
		}
	}

	public void remove(Class<?> entity, Collection<?> paramCollectionOfObject) {
		if(paramCollectionOfObject.size() > 0)
			remove(entity, paramCollectionOfObject.toArray());
	}

	/**
	 * query single methods
	 */
	public Object queryUnique(String paramString, Object[] paramArrayOfObject) {
		return jdbcTemplate.queryForObject(paramString, paramArrayOfObject, Object.class);
	}

	public <T> T queryUnique(String paramString, Class<T> paramClass, Object[] paramArrayOfObject) {
		return jdbcTemplate.queryForObject(paramString, paramArrayOfObject, paramClass);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> paramClass, Serializable paramSerializable) {

		String mapperName = mapperPackage + "." + paramClass.getSimpleName() + "Mapper";

		Class<Mapper<Object>> clazz = null;
		try {
			clazz = (Class<Mapper<Object>>) Class.forName(mapperName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (T) sqlSession.getMapper(clazz).selectByPrimaryKey(paramSerializable);
	}

	/**
	 * query limit List<?> methods
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> query(String paramString, Class<T> paramClass, Object[] paramArrayOfObject) {
		return (List<T>) this.query(paramString, new ObjectRowMapper(paramClass), paramArrayOfObject);
	}

	public <T> List<T> query(String paramString, RowMapper<T> rowMapper, Object[] paramArrayOfObject) {
		return jdbcTemplate.query(paramString, rowMapper, paramArrayOfObject);
	}

	public List<Object[]> query(String paramString, Object[] paramArrayOfObject) {
		return jdbcTemplate.query(paramString, new ArrayMapper(), paramArrayOfObject);
	}

	public List<Map<String, Object>> queryForList(String paramString, Object[] paramArrayOfObject) {
		return jdbcTemplate.queryForList(paramString, paramArrayOfObject);
	}

	/**
	 * query all List<?> methods
	 */
	public <T> List<T> loadAll(Class<T> paramClass) {
		return query("select * from " + getTableName(paramClass), paramClass, null);
	}

	/**
	 * execute methods
	 */
	public int execute(String paramString, Object[] paramArrayOfObject) {
		return jdbcTemplate.update(paramString, paramArrayOfObject);
	}

	public int[] batchExecute(String paramString, Object[] paramArrayOfObject) {

		return jdbcTemplate.batchUpdate(new String[] {});// .update(paramString,
															// paramArrayOfObject);
	}

	public int count(String paramString, Object[] paramObject) {
		return ((Number) queryUnique(paramString, paramObject)).intValue();
	}

	public SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 驼峰命名转为表名
	 * 
	 * @author Crap
	 * @param str
	 *            字符串
	 * @return String
	 */
	public String getTableName(Class<?> paramClass) {
		return StringUtil.toHungarian(paramClass.getSimpleName());
	}

	@Override
	public <T> T queryUnique(String paramString, Object[] paramArrayOfObject, Class<T> paramClass) {
		return jdbcTemplate.queryForObject(paramString, paramArrayOfObject, paramClass);
	}
}
