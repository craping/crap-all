package org.crap.jrain.core.validate.support;

import org.crap.jrain.core.validate.security.param.SecurityParam;

/** 
 * @project Crap
 * 
 * @author Crap
 * 
 * @Copyright 2013 - 2014 All rights reserved. 
 * 
 * 
 */
public class BarScreenWrap {
	
	private Param<?> [] params;
	
	private boolean security;
	
	private SecurityParam securityParams;
	
	private String desc;
	
	public Param<?>[] getParams() {
		return params;
	}
	public void setParams(Param<?>[] params) {
		this.params = params;
	}
	public boolean isSecurity() {
		return security;
	}
	public void setSecurity(boolean security) {
		this.security = security;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public SecurityParam getSecurityParams() {
		return securityParams;
	}
	public void setSecurityParams(SecurityParam securityParams) {
		this.securityParams = securityParams;
	}
}
