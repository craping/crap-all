package org.crap.data.dao.sql.param;

import org.crap.jrain.core.validate.support.Param;
import org.crap.jrain.core.validate.support.param.IntegerParam;
import org.crap.jrain.core.validate.support.param.MultiParam;


public class PageParam extends MultiParam<IntegerParam> {
	
	@SuppressWarnings("unchecked")
	public PageParam() {
		this.params = new Param[]{new PageFlagParam(), new PageNumParam()};
	}
}
