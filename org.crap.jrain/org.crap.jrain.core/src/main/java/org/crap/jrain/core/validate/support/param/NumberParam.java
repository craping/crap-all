package org.crap.jrain.core.validate.support.param;

import java.math.BigDecimal;

import org.crap.jrain.core.bean.result.Errcode;
import org.crap.jrain.core.error.support.Errors;
import org.crap.jrain.core.util.StringUtil;
import org.crap.jrain.core.validate.exception.ParamFormatException;
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
 */
public class NumberParam extends Param<Number> {
	
	@Override
	protected Errcode validateValue(Object param) throws ValidationException {
		if(!StringUtil.isNumber(param.toString()))
			throw new ParamFormatException(this);
		return Errors.OK;
	}

	@Override
	protected boolean validateMin(Object param) {
		if(this.min == null)
			return true;
		
		return new BigDecimal(param.toString()).compareTo(new BigDecimal(this.min.toString())) > -1;
	}

	@Override
	protected boolean validateMax(Object param) {
		if(this.max == null)
			return true;
		
		return new BigDecimal(param.toString()).compareTo(new BigDecimal(this.max.toString())) < 1;
	}

	@Override
	protected Number cast0(Object param) {
		return new BigDecimal(param.toString());
	}
	
}
