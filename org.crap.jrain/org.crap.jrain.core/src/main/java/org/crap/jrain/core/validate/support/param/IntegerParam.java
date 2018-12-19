package org.crap.jrain.core.validate.support.param;

import org.crap.jrain.core.bean.result.Errcode;
import org.crap.jrain.core.error.support.Errors;
import org.crap.jrain.core.util.StringUtil;
import org.crap.jrain.core.validate.exception.ParamFormatException;
import org.crap.jrain.core.validate.exception.ValidationException;

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
public class IntegerParam extends NumberParam {
	
	@Override
	protected Errcode validateValue(Object param) throws ValidationException {
		if(!StringUtil.isInteger(param.toString()))
			throw new ParamFormatException(this);
		return Errors.OK;
	}
	
	@Override
	protected Integer cast0(Object param) {
		return Integer.valueOf(param.toString());
	}	
}
