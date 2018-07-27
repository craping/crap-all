package org.crap.data.dao.sql.param;

import org.crap.jrain.core.validate.support.param.IntegerParam;

public class PageNumParam extends IntegerParam {
	
	public PageNumParam() {
		this.value = "pageSize";
		this.required = false;
		//this.defaultValue = new String[]{"20"};
		this.desc="每页显示记录数";
	}
}
