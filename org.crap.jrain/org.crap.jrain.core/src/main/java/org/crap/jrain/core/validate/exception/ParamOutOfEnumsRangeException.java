package org.crap.jrain.core.validate.exception;

import java.util.Arrays;

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
public class ParamOutOfEnumsRangeException extends ParamOutOfRangeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4875687366300431125L;

	public ParamOutOfEnumsRangeException(Param<?> param) {
		super(param, Arrays.toString(param.getEnums()));
	}
	
	public ParamOutOfEnumsRangeException(Param<?> param, String[] enums) {
		super(param, Arrays.toString(enums));
	}
}
