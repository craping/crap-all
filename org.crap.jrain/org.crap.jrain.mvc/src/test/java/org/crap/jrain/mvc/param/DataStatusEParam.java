package org.crap.jrain.mvc.param;

import org.crap.jrain.core.validate.support.param.EnumParam;


public class DataStatusEParam extends EnumParam {
	
	public DataStatusEParam() {
		super(new Constant.System.DataStatus() {});
	}

}
