package org.crap.jrain.core.validate.exception;

import org.crap.jrain.core.ErrcodeException;
import org.crap.jrain.core.bean.result.Errcode;
import org.crap.jrain.core.validate.support.Param;

/** 
 * 
 * @author Crap
 * 
 * @Copyright 2013 - 2014 All rights reserved. 
 * 
 */
public class ValidationException extends ErrcodeException {
	
	private static final long serialVersionUID = 917046328449572946L;
	
	protected Param<?> param;

	public ValidationException(Errcode errcode) {
		super(errcode);
	}
	
	public ValidationException(Errcode errcode, Throwable cause) {
		super(errcode, cause);
	}
	
	public ValidationException(Errcode errcode, Object... marks) {
		super(errcode, marks);
	}
	
	public ValidationException(Errcode errcode, Throwable cause, Object... marks) {
		super(errcode, cause, marks);
	}

	public Param<?> getParam() {
		return param;
	}

	public void setParam(Param<?> param) {
		this.param = param;
	}
}
