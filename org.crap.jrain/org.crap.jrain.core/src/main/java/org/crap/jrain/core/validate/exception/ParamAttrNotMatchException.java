package org.crap.jrain.core.validate.exception;

import org.crap.jrain.core.validate.annotation.Attribute;

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
public class ParamAttrNotMatchException extends RuntimeException {

	private static final long serialVersionUID = 7784187390684148373L;

	public ParamAttrNotMatchException(Class<?> cls, Attribute attr) {
		super(String.format("param attribute [%s.%s] do not match the type of %s", cls.getName(), attr.name(), String.class.getName()));
	}
}
