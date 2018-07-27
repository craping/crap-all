package org.crap.data.dao.sql.param;

import org.crap.jrain.core.validate.support.param.IntegerParam;

public class PageFlagParam extends IntegerParam {
	
	public PageFlagParam() {
		this.value = "curPage";
		this.required = false;
		this.defaultValue = 1;
		this.desc="页数";
	}
	
}
