package org.crap.jrain.core.validate.exception;

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
public class ParamIllegalRangeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5725294121770860398L;
	
	public ParamIllegalRangeException() {
		super();
	}
	
	public ParamIllegalRangeException(String msg) {
		super(msg);
	}
	
	public ParamIllegalRangeException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public ParamIllegalRangeException(Throwable cause) {
		super(cause);
	}
}
