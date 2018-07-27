package org.crap.jrain.core.validate.security.param;

import org.crap.jrain.core.validate.support.Param;
import org.crap.jrain.core.validate.support.param.MultiParam;

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
public class SecurityParam extends MultiParam<Param<?>> {
	
	
	@SuppressWarnings("unchecked")
	public SecurityParam() {
		this.params = new Param[]{new EncryptDataParam(), new EncryptSourceParam(), new EncryptFlagParam()};
	}

	@Override
	protected Param<?> cast0(Object param) {
		return (Param<?>)param;
	}
}
