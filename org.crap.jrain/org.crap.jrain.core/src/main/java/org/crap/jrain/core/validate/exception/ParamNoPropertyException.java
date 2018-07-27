package org.crap.jrain.core.validate.exception;

import org.crap.jrain.core.error.support.Errors;


/**
 * @author Crap
 *
 * @date 2016年8月19日 上午10:19:25
 *
 *
 */
public class ParamNoPropertyException extends ValidationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5816710204116127417L;
	/**
	 * 
	 */
	private final String paramName;
	
	public ParamNoPropertyException(String paramName) {
		super(Errors.PARAM_NO_PROPERTY, paramName);
		this.paramName = paramName;
	}
	
	public String getParamName() {
		return paramName;
	}

}
