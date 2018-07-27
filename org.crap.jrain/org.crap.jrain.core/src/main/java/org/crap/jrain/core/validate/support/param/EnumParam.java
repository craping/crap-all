package org.crap.jrain.core.validate.support.param;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
 *	枚举参数
 *	继承该类,将 {@link #E} 中所有属性 作为枚举范围验证
 *	类本身不参与验证
 */
public abstract class EnumParam extends Param<String> {
	
	protected Object E;
	
	protected StringBuffer msg = new StringBuffer();
	
	public EnumParam(Object E) {
		this.E = E;
		genEnums(E);
	}
	
	protected void genEnums(Object E) {
		if(E == null)
			return;
		
		List<String> eList = null;
		
		if(E instanceof Enum<?>[]){
			eList = AnalysisEnum((Enum<?>[]) E);
		}else if(E.getClass().isArray()){
			Object[] array = (Object[]) E;
			this.enums = new String[array.length];
			for (int i = 0; i < array.length; i++) {
				this.enums[i] = array[i].toString();
			}
		}else{
			eList = AnalysisClass(E);
		}
		this.enums = new String[eList.size()];
		eList.toArray(this.enums);
	}
	
	
	protected List<String> AnalysisClass(Object E) {
		List<String> eList = new ArrayList<>();
		
		Field[] fields = E.getClass().getFields();
		AccessibleObject.setAccessible(fields, true);
		for (Field field : fields) {
//			if(!field.getType().isPrimitive())
//				continue;
			try {
				String value = field.get(E).toString();
				eList.add(value);
				this.msg.append(value).append(":").append(field.getName()).append(",");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		if(this.msg.length() > 0)
			this.msg.deleteCharAt(this.msg.length() - 1);
		return eList;
	}
	
	protected List<String> AnalysisEnum(Enum<?>[] enums) {
		List<String> eList = new ArrayList<>();
		for (Enum<?> e : enums) {
			eList.add(String.valueOf(e.ordinal()));
			this.msg.append(e.ordinal()).append(":").append(e.name()).append(",");
		}
		if(this.msg.length() > 0)
			this.msg.deleteCharAt(this.msg.length() - 1);
		return eList;
	}
	
	@Override
	protected Errcode validateValue(Object paramString) throws ValidationException {
		return Errors.OK;
	}

	@Override
	protected boolean validateMin(Object paramString) {
		return true;
	}

	@Override
	protected boolean validateMax(Object paramString) {
		return true;
	}

	public Object getE() {
		return E;
	}

	public void setE(Object e) {
		E = e;
	}
	
	@Override
	public String toString() {
		return this.msg.toString();
	}
	
	@Override
	protected String cast0(Object param) {
		return param.toString();
	}
}
