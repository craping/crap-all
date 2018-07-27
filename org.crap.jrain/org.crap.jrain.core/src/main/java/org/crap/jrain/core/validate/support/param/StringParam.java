package org.crap.jrain.core.validate.support.param;

import org.crap.jrain.core.bean.result.Errcode;
import org.crap.jrain.core.error.support.Errors;
import org.crap.jrain.core.util.StringUtil;
import org.crap.jrain.core.validate.exception.ParamIllegalRangeException;
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
public class StringParam extends Param<String> {
	
	@Override
	protected Errcode validateValue(Object param) throws ValidationException {
		return Errors.OK;
	}

	@Override
	protected boolean validateMin(Object param) {
		if(this.min == null)
			return true;
		
		return param.toString().length() >= Integer.parseInt(this.min);
	}

	@Override
	protected boolean validateMax(Object param) {
		if(this.max == null)
			return true;
		
		return param.toString().length() <= Integer.parseInt(this.max);
	}
	
	@Override
	protected void checkMinLegitimate(String mapping) throws ValidationException {
		if(this.min != null && !StringUtil.isInteger(this.min))
			throw new ParamIllegalRangeException(String.format("[%s] param [%s] min:[%s] attribute is not legitimate", mapping.replace("$", "."), this.value, this.min));
	};
	
	@Override
	protected void checkMaxLegitimate(String mapping) throws ValidationException {	
		if(this.max != null && !StringUtil.isInteger(this.max))
			throw new ParamIllegalRangeException(String.format("[%s] param [%s] max:[%s] attribute is not legitimate", mapping.replace("$", "."), this.value, this.max));
	}

	@Override
	protected String cast0(Object param) {
		return param.toString();
	}

	
}
