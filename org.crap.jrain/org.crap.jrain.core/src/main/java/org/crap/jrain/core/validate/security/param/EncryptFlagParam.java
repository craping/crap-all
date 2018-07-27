package org.crap.jrain.core.validate.security.param;

import org.crap.jrain.core.validate.DataBarScreen;
import org.crap.jrain.core.validate.support.param.IntegerParam;

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
public class EncryptFlagParam extends IntegerParam {
	
	private static String defaultKey;
	
	public EncryptFlagParam() {
		this.value = getDefaultKey();
		this.desc="密钥对标志";
		this.min = "0";
		this.max = String.valueOf(DataBarScreen.KPCOLLECTION.getTotal() - 1);
	}

	public static String getDefaultKey() {
		return defaultKey;
	}

	public static void setDefaultKey(String defaultKey) {
		EncryptFlagParam.defaultKey = defaultKey;
	}
	
	
}
