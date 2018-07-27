package org.crap.jrain.core.error.support;

import java.util.Locale;
import java.util.Properties;

import org.crap.jrain.core.Resource;
import org.crap.jrain.core.error.LangErrcode;


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
public enum Errors  implements LangErrcode {
	/** 操作成功 */
	OK(0, "ok"),
	/** 未知异常 */
	EXCEPTION_UNKNOW(-1),
	/** 参数为空 */
	PARAM_SERVICE_NOT_DEFINITION(1),
	PARAM_REQUIRED(1),
	PARAM_FORMAT_ERROR(1),
	PARAM_EMPTY(1),
	PARAM_OUT_OF_RANGE(1),
	PARAM_NO_PROPERTY(1),
	
	/** 数据错误 */
	DATA_TYPE_ERROR(1),
	/** 数据已存在 */
	DATA_EXIST(1),
	/** 数据不存在 */
	DATA_NOT_EXIST(1);
	
	public Locale defaultLanguage;	//默认语言
	{
		String prop = Resource.getSysProp("sys.defaultLanguage");
		if(defaultLanguage != null){
			String[] language = prop.split("_");
			if(language != null && language.length > 1){
				defaultLanguage = new Locale(language[0],language[1]);
			}else{
				defaultLanguage = Locale.getDefault();
			}
		}else{
			defaultLanguage = Locale.getDefault();
		}
	}
	private final int result;		//错误集
	private final int errcode;		//错误码
	private final String msg;		//错误描述
	
	private Errors(int result) {
		this(result, null);
	}
	
	private Errors(int result, String msg) {
		this.result = result;
		this.errcode = this.ordinal();
		String localMsg = matchMsg(name(), defaultLanguage);
		if(localMsg == null || localMsg.equals(""))
			this.msg = msg==null?("Error:["+name()+"]"):msg;
		else
			this.msg = localMsg;
	}
	
	private Errors(int result, int errcode) {
		this(result, errcode, null);
	}
	
	private Errors(int result, int errcode, String msg) {
		this.result = result;
		this.errcode = errcode;
		String localMsg = matchMsg(name(), defaultLanguage);
		if(localMsg == null || localMsg.equals(""))
			this.msg = msg==null?("Error:["+name()+"]"):msg;
		else
			this.msg = localMsg;
	}
	
	public int getResult() {
		return this.result;
	}
	public int getErrcode() {
		return this.errcode;
	}
	public String getMsg() {
		return this.msg;
	}
	public String getMsg(Locale locale){
		return matchMsg(name(), locale);
	}
	
	public LangErrcode LOCALE(final Locale locale){
		return new LangErrcode() {
			private static final long serialVersionUID = 1139795147093795851L;

			public int getResult() {
				return result;
			}
			
			public String getMsg() {
				return matchMsg(name(), locale);
			}
			
			public int getErrcode() {
				return errcode;
			}

			public LangErrcode LOCALE(Locale locale) {
				return Errors.this.LOCALE(locale);
			}
		};
	}
	
	private static String matchMsg(String key, String language){
		Properties p = Resource.getProp(language);
		if(p==null)
			return null;
		
		String msg = p.getProperty(key);
		
		return msg;
	}
	
	public static String matchMsg(String key, Locale locale){
		return matchMsg(key, locale.toString());
	}
}
