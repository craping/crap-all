package org.crap.jrain.core.validate.security.param;

import org.crap.jrain.core.validate.support.param.StringParam;

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
public class EncryptDataParam extends StringParam {
	
	private static String defaultKey;
	
	public EncryptDataParam() {
		this.value = getDefaultKey();
		this.desc="加密数据";
	}

	public static String getDefaultKey() {
		return defaultKey;
	}

	public static void setDefaultKey(String defaultKey) {
		EncryptDataParam.defaultKey = defaultKey;
	}
	
}
