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
	
	public EncryptDataParam encryptDataParam;
	
	public EncryptSourceParam encryptSourceParam;
	
	public EncryptFlagParam encryptFlagParam;
	
	@SuppressWarnings("unchecked")
	public SecurityParam() {
		this.encryptDataParam = new EncryptDataParam();
		this.encryptSourceParam = new EncryptSourceParam();
		this.encryptFlagParam = new EncryptFlagParam();
		this.params = new Param[]{this.encryptDataParam, this.encryptSourceParam, this.encryptFlagParam};
	}

	@Override
	protected Param<?> cast0(Object param) {
		return (Param<?>)param;
	}

	public EncryptDataParam getEncryptDataParam() {
		return encryptDataParam;
	}

	public void setEncryptDataParam(EncryptDataParam encryptDataParam) {
		this.encryptDataParam = encryptDataParam;
	}

	public EncryptSourceParam getEncryptSourceParam() {
		return encryptSourceParam;
	}

	public void setEncryptSourceParam(EncryptSourceParam encryptSourceParam) {
		this.encryptSourceParam = encryptSourceParam;
	}

	public EncryptFlagParam getEncryptFlagParam() {
		return encryptFlagParam;
	}

	public void setEncryptFlagParam(EncryptFlagParam encryptFlagParam) {
		this.encryptFlagParam = encryptFlagParam;
	}
	
	
}
