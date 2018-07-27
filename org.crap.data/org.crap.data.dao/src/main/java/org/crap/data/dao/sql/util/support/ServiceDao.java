package org.crap.data.dao.sql.util.support;

import java.util.List;
import java.util.Map;

import org.crap.data.dao.sql.mapper.AbstractObjectRowMapper;
import org.crap.data.dao.sql.orm.dao.support.CommonDao;
import org.crap.data.dao.sql.util.EntityMappingException;
import org.crap.data.dao.sql.util.IServiceDao;
import org.crap.data.dao.sql.util.sql.support.Profile;
import org.crap.data.dao.sql.util.sql.support.QueryBuilder;
import org.crap.jrain.core.bean.result.criteria.Data;
import org.crap.jrain.core.bean.result.criteria.DataResult;
import org.crap.jrain.core.bean.result.criteria.Page;
import org.crap.jrain.core.error.support.Errors;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("serviceDao")
public class ServiceDao extends CommonDao implements IServiceDao {

	@Override
	public DataResult queryForArrayDataResult(String sql) {

		return this.queryForArrayDataResult(sql, null);
	}

	@Override
	public DataResult queryForArrayDataResult(String sql, Object[] sqlParams) {

		DataResult dataResult = new DataResult(Errors.EXCEPTION_UNKNOW);
		
		List<Object[]> info = super.query(sql, sqlParams);
		
		dataResult.setData(new Data(info));
		dataResult.setErrcode(Errors.OK);
		
		return dataResult;	
	}

	@Override
	public DataResult queryForArrayDataResult(String sql, String countSql, Profile profile) {

		return this.queryForArrayDataResult(sql, countSql, null, profile);
	}

	@Override
	public DataResult queryForArrayDataResult(String sql, String countSql, Object[] sqlParams, Profile profile) {

		DataResult dataResult = new DataResult(Errors.EXCEPTION_UNKNOW);
		
		Page page = profile.getPage();
		List<Object[]> info = super.query(profile.toSql(sql), sqlParams, (page.getPage()-1)*page.getNum(), page.getNum());
		
		int totalnum = this.count(countSql, sqlParams);
		page.setTotalnum(totalnum);
		
		dataResult.setData(new Data(info, page));
		dataResult.setErrcode(Errors.OK);
		
		return dataResult;
	}
	
	@Override
	public DataResult queryForArrayDataResult(QueryBuilder builder) {
		
		DataResult dataResult = new DataResult(Errors.EXCEPTION_UNKNOW);
		
		Page page = builder.getProfile().getPage();
		Integer limit = page.getNum();
		Integer offset = (page.getPage()-1)*(limit == null? 0 : limit);
		
		List<Object[]> info = super.query(builder.getSql(), builder.getSqlParams(),offset, limit);
		
		List<Object[]> totalnumList = this.query(builder.getCountSql(), builder.getSqlParams());
		if(totalnumList == null){
			page.setTotalnum(0);
		}else if(totalnumList.size() == 1){
			page.setTotalnum(((Number)totalnumList.get(0)[0]).intValue());
		}else{
			page.setTotalnum(totalnumList.size());
		}
		
		dataResult.setData(new Data(info, page));
		dataResult.setErrcode(Errors.OK);
		
		return dataResult;
	}
	
	
	
	
	
	@Override
	public DataResult queryForMapDataResult(String sql) {

		return this.queryForMapDataResult(sql, null);
	}

	@Override
	public DataResult queryForMapDataResult(String sql, Object[] sqlParams) {

		DataResult dataResult = new DataResult(Errors.EXCEPTION_UNKNOW);
		
		List<Map<String, Object>> info = super.queryForList(sql, sqlParams);
		
		dataResult.setData(new Data(info));
		dataResult.setErrcode(Errors.OK);
		
		return dataResult;
	}

	@Override
	public DataResult queryForMapDataResult(String sql, String countSql, Profile profile) {

		return this.queryForMapDataResult(sql, countSql, null, profile);
	}

	@Override
	public DataResult queryForMapDataResult(String sql, String countSql, Object[] sqlParams, Profile profile) {

		DataResult dataResult = new DataResult(Errors.EXCEPTION_UNKNOW);
		
		Page page = profile.getPage();
		List<Map<String, Object>> info = super.queryForList(profile.toSql(sql), sqlParams, (page.getPage()-1)*page.getNum(), page.getNum());
		
		int totalnum = this.count(countSql, sqlParams);
		page.setTotalnum(totalnum);
		
		dataResult.setData(new Data(info, page));
		dataResult.setErrcode(Errors.OK);
		
		return dataResult;
	}
	
	@Override
	public DataResult queryForMapDataResult(QueryBuilder builder) {
		
		DataResult dataResult = new DataResult(Errors.EXCEPTION_UNKNOW);
		
		Page page = builder.getProfile().getPage();
		Integer limit = page.getNum();
		Integer offset = (page.getPage()-1)*(limit == null? 0 : limit);
		
		List<Map<String, Object>> info = super.queryForList(builder.getSql(), builder.getSqlParams(), offset, limit);
		
		List<Object[]> totalnumList = this.query(builder.getCountSql(), builder.getSqlParams());
		if(totalnumList == null){
			page.setTotalnum(0);
		}else if(totalnumList.size() == 1){
			page.setTotalnum(((Number)totalnumList.get(0)[0]).intValue());
		}else{
			page.setTotalnum(totalnumList.size());
		}
		
		dataResult.setData(new Data(info, page));
		dataResult.setErrcode(Errors.OK);
		
		return dataResult;
	}
	
	
	
	

	@Override
	public DataResult queryForDataResult(Class<?> entity) {

		String table = getMapper().getTableName(entity);
		if(table == null)
			throw new EntityMappingException(entity);
		String sql = "select * from " + table;
		
		return this.queryForDataResult(sql, entity);
	}
	
	@Override
	public DataResult queryForDataResult(Class<?> entity, Profile profile) {
		
		String table = getMapper().getTableName(entity);
		if(table == null)
			throw new EntityMappingException(entity);
		String sql = "select * from " + table;
		String countSql = "select count(0) from " + table;
		
		return this.queryForDataResult(sql, countSql, entity, profile);
	}
	
	@Override
	public DataResult queryForDataResult(String sql, Class<?> entity) {

		return this.queryForDataResult(sql, entity, null);
	}

	@Override
	public DataResult queryForDataResult(String sql, Class<?> entity, Object[] sqlParams) {

		DataResult dataResult = new DataResult(Errors.EXCEPTION_UNKNOW);

		List<?> info = super.queryEntityList(sql, entity, sqlParams);
	
		dataResult.setData(new Data(info));
		dataResult.setErrcode(Errors.OK);
		
		return dataResult;
	}

	@Override
	public DataResult queryForDataResult(String sql, String countSql, Class<?> entity, Profile profile) {
		
		return this.queryForDataResult(sql, countSql, entity,null, profile);
	}

	@Override
	public DataResult queryForDataResult(String sql, String countSql, Class<?> entity, Object[] sqlParams, Profile profile) {
		
		DataResult dataResult = new DataResult(Errors.EXCEPTION_UNKNOW);
		
		Page page = profile.getPage();
		List<?> info = super.queryEntityList(profile.toSql(sql), entity, sqlParams, (page.getPage()-1)*page.getNum(), page.getNum());
		
		int totalnum = this.count(countSql, sqlParams);
		page.setTotalnum(totalnum);
		
		dataResult.setData(new Data(info, page));
		dataResult.setErrcode(Errors.OK);
		
		return dataResult;
	}
	
	@Override
	public DataResult queryForDataResult(QueryBuilder builder, Class<?> entity) {
		DataResult dataResult = new DataResult(Errors.EXCEPTION_UNKNOW);
		
		Page page = builder.getProfile().getPage();
		Integer limit = page.getNum();
		Integer offset = (page.getPage()-1)*(limit == null? 0 : limit);
		List<?> info = super.queryEntityList(builder.getSql(), entity, builder.getSqlParams(), offset, limit);
		Integer totalnum = 0;
		try {
			List<Object[]> totalnumList = this.query(builder.getCountSql(), builder.getSqlParams());
			if(totalnumList == null){
				totalnum = 0;
			}else if(totalnumList.size() == 1){
				Object[] object = totalnumList.get(0);
				if(null != object && object.length > 0 && object[0] instanceof Long){
					totalnum =Integer.parseInt(object[0].toString());
				}else {
					List<?> infoAll = super.query(builder.getSql(), builder.getSqlParams());
					if(null != infoAll && infoAll.size() > 0){
						totalnum = infoAll.size();
					}
				}
			}else{
				totalnum = totalnumList.size();
			}
		} catch (Exception e) {
			List<?> infoAll = super.query(builder.getSql(), builder.getSqlParams());
			if(null != infoAll && infoAll.size() > 0){
				totalnum = infoAll.size();
			}
		}
		page.setTotalnum(totalnum);
		dataResult.setData(new Data(info, page));
		dataResult.setErrcode(Errors.OK);
		
		return dataResult;
	}
	
	

	
	
	@Override
	public DataResult queryForDataResult(AbstractObjectRowMapper<?> rowMapper) {
		
		String table = getMapper().getTableName(rowMapper.getMappedClass());
		if(table == null)
			throw new EntityMappingException(rowMapper.getMappedClass());
		String sql = "select * from " + table;
		
		return this.queryForDataResult(sql, rowMapper);
	}

	@Override
	public DataResult queryForDataResult(AbstractObjectRowMapper<?> rowMapper, Profile profile) {
		
		String table = getMapper().getTableName(rowMapper.getMappedClass());
		if(table == null)
			throw new EntityMappingException(rowMapper.getMappedClass());
		String sql = "select * from " + table;
		String countSql = "select count(0) from " + table;
		
		return this.queryForDataResult(sql, countSql, rowMapper, profile);
	}

	@Override
	public DataResult queryForDataResult(String sql, RowMapper<?> rowMapper) {

		return this.queryForDataResult(sql, rowMapper, null);
	}

	@Override
	public DataResult queryForDataResult(String sql, RowMapper<?> rowMapper, Object[] sqlParams) {
		
		DataResult dataResult = new DataResult(Errors.EXCEPTION_UNKNOW);

		List<?> info = super.queryEntityList(sql, rowMapper, sqlParams);
	
		dataResult.setData(new Data(info));
		dataResult.setErrcode(Errors.OK);
		
		return dataResult;
	}

	@Override
	public DataResult queryForDataResult(String sql, String countSql, RowMapper<?> rowMapper, Profile profile) {

		return this.queryForDataResult(sql, countSql, rowMapper, null, profile);
	}

	@Override
	public DataResult queryForDataResult(String sql, String countSql, RowMapper<?> rowMapper, Object[] sqlParams, Profile profile) {
		
		DataResult dataResult = new DataResult(Errors.EXCEPTION_UNKNOW);
		
		Page page = profile.getPage();
		List<?> info = super.queryEntityList(profile.toSql(sql), rowMapper, sqlParams, (page.getPage()-1)*page.getNum(), page.getNum());
		
		int totalnum = this.count(countSql, sqlParams);
		page.setTotalnum(totalnum);
		
		dataResult.setData(new Data(info, page));
		dataResult.setErrcode(Errors.OK);
		
		return dataResult;
	}

	@Override
	public DataResult queryForDataResult(QueryBuilder builder, RowMapper<?> rowMapper) {
		
		DataResult dataResult = new DataResult(Errors.EXCEPTION_UNKNOW);
		
		Page page = builder.getProfile().getPage();
		Integer limit = page.getNum();
		Integer offset = (page.getPage()-1)*(limit == null? 0 : limit);
		
		List<?> info = super.queryEntityList(builder.getSql(), rowMapper, builder.getSqlParams(), offset, limit);
		
		List<Object[]> totalnumList = this.query(builder.getCountSql(), builder.getSqlParams());
		if(totalnumList == null){
			page.setTotalnum(0);
		}else if(totalnumList.size() == 1){
			page.setTotalnum(((Number)totalnumList.get(0)[0]).intValue());
		}else{
			page.setTotalnum(totalnumList.size());
		}
		
		dataResult.setData(new Data(info, page));
		dataResult.setErrcode(Errors.OK);
		
		return dataResult;
	}
	
}
