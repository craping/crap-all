package org.crap.jrain.core.validate.exception;

import org.crap.jrain.core.error.support.Errors;
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
public class ParamFormatException extends ValidationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9177645561417152633L;
	
	public ParamFormatException(Param<?> param) {
		super(Errors.PARAM_FORMAT_ERROR, param.getValue());
	}
	
	public ParamFormatException(Param<?> param, Throwable cause) {
		super(Errors.PARAM_FORMAT_ERROR, cause, param.getValue());
	}
}
