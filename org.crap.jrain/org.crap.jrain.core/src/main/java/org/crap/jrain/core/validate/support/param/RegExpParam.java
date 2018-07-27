package org.crap.jrain.core.validate.support.param;

import org.crap.jrain.core.bean.result.Errcode;
import org.crap.jrain.core.error.support.Errors;
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
 *	正则参数
 *	继承该类,将验证 {@link #regex} 
 */
public class RegExpParam extends StringParam {
	
	protected String regex;
	
	@Override
	protected Errcode validateValue(Object param) throws ValidationException {
		if(!param.toString().matches(regex))
			throw new ParamFormatException(this);
		return Errors.OK;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	};
}
