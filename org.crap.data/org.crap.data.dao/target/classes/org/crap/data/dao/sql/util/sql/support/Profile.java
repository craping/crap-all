package org.crap.data.dao.sql.util.sql.support;

import org.crap.data.dao.sql.param.PageFlagParam;
import org.crap.data.dao.sql.param.PageNumParam;
import org.crap.data.dao.sql.util.sql.support.sort.Sort;
import org.crap.data.dao.sql.util.sql.support.sort.SortFilter;
import org.crap.jrain.core.bean.result.criteria.Page;

import net.sf.json.JSONObject;

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
	
	/*public Profile(Map<String,String> params) {
		this(new Page(params), new Sort(params));
	}
	*/
	public Profile(JSONObject params) {
		this(new Page(params.getInt(new PageFlagParam().getValue()), params.getInt(new PageNumParam().getValue())), new Sort(params));
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
