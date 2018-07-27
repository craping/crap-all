package org.crap.jrain.core.validate.support.param;

import org.crap.jrain.core.bean.result.Errcode;
import org.crap.jrain.core.error.support.Errors;
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
 *	组合参数
 *	继承该类,将验证 {@link #params} 中所有参数
 *	类本身不参与验证
 */
public abstract class MultiParam<T extends Param<?>> extends ValidateParam<T> implements SingleParam {
	
	/** 多参数组合验证 */
	protected Param<T>[] params;
	
	public MultiParam() {
		this.required = false;
		this.multi = false;
	}
	
	@Override
	public boolean checkRequired(Object param) {
		return true;
	}
	
	@Override
	protected Errcode validateValue(Object param) throws ValidationException {
		return Errors.OK;
	}

	public Param<T>[] getParams() {
		return params;
	}

	public void setParams(Param<T>[] params) {
		this.params = params;
	}
	
	@Override
	protected T cast0(Object param) {
		return null;
	}
}
