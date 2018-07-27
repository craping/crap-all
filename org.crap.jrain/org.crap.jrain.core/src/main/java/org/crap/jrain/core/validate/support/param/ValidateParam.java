package org.crap.jrain.core.validate.support.param;

import org.crap.jrain.core.bean.result.Errcode;
import org.crap.jrain.core.error.support.Errors;
import org.crap.jrain.core.validate.exception.ValidationException;
import org.crap.jrain.core.validate.support.Param;


/** 
 * @project Crap
 * 
 * @author Crap
 * 
 * @Copyright 2013 - 2014 All rights reserved. 
 * 
 * @email 422655094@qq.com
 * 
 * 	验证参数
 *  继承该类 只有validateValue 功能
 */
public abstract class ValidateParam<T> extends Param<T> {

	@Override
	protected boolean validateMin(Object param) {
		return true;
	}

	@Override
	protected boolean validateMax(Object param) {
		return true;
	}
	
	@Override
	protected Errcode validateEnumsRange(Object param) throws ValidationException {
		return Errors.OK;
	}
	
	@Override
	public void checkRangeLegitimate(String mapping) {
	}
}
