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
 *	时间参数
 *	继承该类,将按照 {@link #format} 格式化验证参数
 */
public class TimeParam extends StringParam {
	
	protected String format;
	
	@Override
	protected Errcode validateValue(Object param) throws ValidationException {
		if(!StringUtil.isDate(param.toString(), format))
			throw new ParamFormatException(this);
		return Errors.OK;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
}
