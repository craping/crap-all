package org.crap.jrain.core.validate.support;

import org.crap.jrain.core.bean.result.Errcode;
import org.crap.jrain.core.error.support.Errors;
import org.crap.jrain.core.validate.Validation;
import org.crap.jrain.core.validate.exception.ParamIllegalRangeException;
import org.crap.jrain.core.validate.exception.ParamOutOfEnumsRangeException;
import org.crap.jrain.core.validate.exception.ParamOutOfRangeException;
import org.crap.jrain.core.validate.exception.ValidationException;

  
/**  
* @ClassName: Param  
* @Description: 单个参数校验处理类
* @author Crap  
* @date 2017年11月10日  
*  
* @param <T>  
*/  
    
public abstract class Param<T> implements Validation<Object> {
	/** 参数所属接口名 */
	protected String mapping;
	
	/** 参数名 */
	protected String value;
	/** 描述 */
	protected String desc;
	/** 是否为必要 */
	protected Boolean required;
	/** 是否为空 */
	protected Boolean empty;
	/** 是否有多个值 */
	protected Boolean multi;
	/** 默认值 */
	//protected List<String> defaultValue;
	protected T defaultValue;
	/** 参数取值范围(如果这个值被设定,min,max 则失效) */
	protected String[] enums;
	/** 参数最小值 */
	protected String min;
	/** 参数最大值 */
	protected String max;
	
	protected Class<? extends Param<T>> type;
	
	/**
	 * 参数必要性检查
	 * @author Crap
	 * @param param
	 * @return boolean
	 */
	public boolean checkRequired(Object param){
		return (required == null || required)?(param != null):true;
	}
	
	/**
	 * 参数为空检查
	 * @author Crap
	 * @param param
	 * @return boolean
	 */
	public boolean checkEmpty(Object param){
		return (param != null && (empty == null || !empty))?(!param.equals("")):true;
	}
	
	/**
	 * 验证接口
	 * @param param 需要验证的参数值
	 * @return Errcode
	 * @throws ValidationException
	 */
	@Override
	public Errcode validate(Object param, Object... data) throws ValidationException {
		if(this.enums != null && this.enums.length > 0) {
			return validateEnumsRange(param);
		}
		Errcode errcode = validateValue(param);
		if(!errcode.equals(Errors.OK))
			return errcode;
		return validateMinMaxRange(param);
		//return validateRange(param);
	}
	
	/**
	 * 验证参数是否存在范围内
	 * @author Crap
	 * @param param 需要验证的参数
	 * @return Errcode
	 * @exception ParamOutOfRangeException,ParamOutOfEnumsRangeException
	 * @throws ValidationException
	 */
	/*private Errcode validateRange(T param) throws ValidationException {
		if(this.enums == null || this.enums.length == 0) {
			//未设置枚举验证则验证min,max
			return validateMinMaxRange(param);
		}
		return validateEnumsRange(param);
	}*/
	
	
	/**
	 * 验证参数是否在最小(Min)最大(Max)值内
	 * @author Crap
	 * @param param 需要验证的参数
	 * @return Errcode
	 * @exception ParamOutOfRangeException
	 * @throws ValidationException
	 */
	protected Errcode validateMinMaxRange(Object param) throws ValidationException {
		if(!validateMin(param) || !validateMax(param))
			throw new ParamOutOfRangeException(this);
		return Errors.OK;
	}
	
	
	/**
	 * 验证参数是否在枚举值范围内
	 * @author Crap
	 * @param param 需要验证的参数
	 * @return Errcode
	 * @exception ParamOutOfEnumsRangeException
	 * @throws ValidationException
	 */
	protected Errcode validateEnumsRange(Object param) throws ValidationException {
		for (int i = 0; i < this.enums.length; i++) {
			if(this.enums[i].equals(String.valueOf(param)))
				return Errors.OK;
		}
		throw new ParamOutOfEnumsRangeException(this);
	}
	
	/**
	 * 检查参数验证规则的最小(Min)范围值是否合法
	 * @author Crap
	 * @param mapping 方法标记明
	 * @exception ParamIllegalRangeException
	 * @throws ValidationException
	 */
	protected void checkMinLegitimate(String mapping) throws ValidationException {
		try{
			if(this.min != null)
				validateValue(this.min);
		} catch (ValidationException e) {
			throw new ParamIllegalRangeException(String.format("param [%s] min:[%s] attribute is not legitimate", this.value, this.min), e.getCause());
		}
	}
	
	/**
	 * 检查参数验证规则的最大(Max)范围值是否合法
	 * @author Crap
	 * @param mapping 方法标记明
	 * @exception ParamIllegalRangeException
	 * @throws ValidationException
	 */
	protected void checkMaxLegitimate(String mapping) throws ValidationException {
		try{
			if(this.max != null)
				validateValue(this.max);
		} catch (ValidationException e) {
			throw new ParamIllegalRangeException(String.format("param [%s] max:[%s] attribute is not legitimate", this.value, this.max), e.getCause());
		}
	}
	
	/**
	 * 参数取值范围值合法性检查
	 * @author Crap
	 * @param mapping 需要被检查的方法的 mapping
	 * @throws ParamIllegalRangeException
	 */
	public void checkRangeLegitimate(String mapping) {
		try {
			for (int i = 0; i < this.enums.length; i++) {
				if(validateValue(this.enums[i]) != Errors.OK)
					throw new ParamIllegalRangeException(String.format("[%s] param [%s] enums:[%s] attribute is not legitimate", mapping.replace("$", "."), this.value, this.enums[i]));
			}
			checkMinLegitimate(mapping);
			checkMaxLegitimate(mapping);
		} catch (ValidationException e) {
			throw new ParamIllegalRangeException(e.getMessage(), e.getCause());
		}
	}
	
	/**
	 * 验证参数是否否符合类型,具体参见实现类
	 * @param param 需要验证的参数值
	 * @return Errcode
	 * @throws ValidationException
	 */
	protected abstract Errcode validateValue(Object param) throws ValidationException;
	
	/**
	 * 验证参数是否符合min值,具体参见实现类
	 * @author Crap
	 * @return boolean
	 */
	protected abstract boolean validateMin(Object param);
	
	/**
	 * 验证参数是否符合max值,具体参见实现类
	 * @author Crap
	 * @return boolean
	 */
	protected abstract boolean validateMax(Object param);
	
	  
	/**  
	* @Title: getT  
	* @Description: 返回该类型转换类
	* @param @return    参数  
	* @return Class<T>    返回类型  
	* @throws  
	*/  
	public T cast(Object param) {
		if(param == null)
			return null;
		return cast0(param);
	}

	protected abstract T cast0(Object param);

	public String getMapping() {
		return mapping;
	}

	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

	public Boolean getRequired() {
		return required;
	}

	public Boolean getMulti() {
		return multi;
	}

	public Boolean isMulti() {
		return multi;
	}

	public void setMulti(Boolean multi) {
		this.multi = multi;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Boolean isRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	/*public List<String> getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(List<String> defaultValue) {
		this.defaultValue = defaultValue;
	}*/

	public T getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(T defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Boolean getEmpty() {
		return empty;
	}

	public void setEmpty(Boolean empty) {
		this.empty = empty;
	}

	public String[] getEnums() {
		return enums;
	}

	public void setEnums(String[] enums) {
		this.enums = enums;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public Class<? extends Param<T>> getType() {
		return type;
	}

	public void setType(Class<? extends Param<T>> type) {
		this.type = type;
	}

}
