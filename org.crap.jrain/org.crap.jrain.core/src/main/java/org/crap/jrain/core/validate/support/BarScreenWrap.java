package org.crap.jrain.core.validate.support;


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
	
	private Param<?> [] securityParams;
	
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
	public Param<?>[] getSecurityParams() {
		return securityParams;
	}
	public void setSecurityParams(Param<?>[] securityParams) {
		this.securityParams = securityParams;
	}
}
