package org.crap.data.dao.sql.util.sql.support;

import java.util.Map;

import org.crap.data.dao.sql.param.PageFlagParam;
import org.crap.data.dao.sql.param.PageNumParam;
import org.crap.data.dao.sql.util.sql.support.sort.Sort;
import org.crap.data.dao.sql.util.sql.support.sort.SortFilter;
import org.crap.jrain.core.bean.result.criteria.Page;

/**
 * @since JDK 1.7
 * 
 * @author Crap
 * 
 * @version 1.4.3
 * 
 * 
 * 
 * @description SQL分页,排序条件生成类
 * 用于分页,排序SQL构造
 */
public class Profile implements SortFilter {
	
	private Page page;
	
	private SortFilter sortFilter;
	
	public Profile() {
		this.page = new Page(1);
	}
	
	public Profile(Map<?,?> params) {
		Object pageStr = params.get(new PageFlagParam().getValue());
		Integer page =  pageStr== null?null:Integer.valueOf(pageStr.toString());
		
		Object numStr = params.get(new PageNumParam().getValue());
		Integer num =  numStr== null?null:Integer.valueOf(numStr.toString());
		this.page = new Page(page, num);
		this.sortFilter = new Sort(params);
	}
	
	public Profile(Integer page, Integer num) {
		this(new Page(page, num), null);
	}
	
	public Profile(Page page) {
		this(page, null);
	}
	
	public Profile(Page page, SortFilter sortFilter) {
		this.page = page;
		this.sortFilter = sortFilter;
	}
	
	public String toSql(String sql) {
		if(this.sortFilter !=null)
			return sql + " " + this.sortFilter.toSql();
		return sql;
	}
	
	public String toSql() {
		if(this.sortFilter ==null)
			return "";
		return this.sortFilter.toSql();
	}
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public SortFilter getSortFilter() {
		return sortFilter;
	}

	public void setSortFilter(SortFilter sortFilter) {
		this.sortFilter = sortFilter;
	}	
}
