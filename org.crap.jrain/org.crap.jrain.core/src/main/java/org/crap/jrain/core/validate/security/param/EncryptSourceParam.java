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
public class EncryptSourceParam extends StringParam {
	
	private static String defaultKey;
	
	public EncryptSourceParam() {
		this.value = getDefaultKey();
		this.desc="加密源";
		this.enums = new String[]{"java","js","android"};
	}

	public static String getDefaultKey() {
		return defaultKey;
	}

	public static void setDefaultKey(String defaultKey) {
		EncryptSourceParam.defaultKey = defaultKey;
	}
	
	
}
