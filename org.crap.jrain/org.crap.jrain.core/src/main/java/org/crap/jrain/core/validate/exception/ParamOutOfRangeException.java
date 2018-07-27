package org.crap.jrain.core.validate.exception;

import org.crap.jrain.core.error.support.Errors;
import org.crap.jrain.core.validate.support.Param;
import org.crap.jrain.core.validate.support.param.StringParam;

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
public class ParamOutOfRangeException extends ValidationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4556805811919636385L;

	public ParamOutOfRangeException(Param<?> param) {
		super(Errors.PARAM_OUT_OF_RANGE, param.getValue(), getRange(param));
		this.param = param;
//		this.marks = new String[]{param.getValue(), getRange(param)};
	}
	
	public ParamOutOfRangeException(Param<?> param, String range) {
		super(Errors.PARAM_OUT_OF_RANGE, param.getValue(), range);
//		this.marks = new String[]{param.getValue(), range};
	}
	
	@SuppressWarnings("rawtypes")
	protected static String getRange(Param param) {
		String limit = param.getType() == StringParam.class?"length":"value";
		if(param.getMin() != null)
			limit = param.getMin() + " ≤ " + limit;
		if(param.getMax() != null)
			limit = limit + " ≤ " + param.getMax();
		return "["+limit+"]";
	}
}
