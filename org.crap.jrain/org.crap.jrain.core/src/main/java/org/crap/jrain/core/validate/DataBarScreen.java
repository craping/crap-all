package org.crap.jrain.core.validate;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.crap.jrain.core.bean.result.Errcode;
import org.crap.jrain.core.error.support.Errors;
import org.crap.jrain.core.util.ClassUtil;
import org.crap.jrain.core.validate.annotation.Attribute;
import org.crap.jrain.core.validate.annotation.BarScreen;
import org.crap.jrain.core.validate.annotation.Parameter;
import org.crap.jrain.core.validate.exception.ParamAttrNotMatchException;
import org.crap.jrain.core.validate.exception.ParamEmptyException;
import org.crap.jrain.core.validate.exception.ParamFormatException;
import org.crap.jrain.core.validate.exception.ParamIllegalRangeException;
import org.crap.jrain.core.validate.exception.ParamRequiredException;
import org.crap.jrain.core.validate.exception.ValidationException;
import org.crap.jrain.core.validate.security.Cipher;
import org.crap.jrain.core.validate.security.DefaultKeyPairCollection;
import org.crap.jrain.core.validate.security.KeyPairCollection;
import org.crap.jrain.core.validate.security.Security;
import org.crap.jrain.core.validate.security.component.CryptoCipher;
import org.crap.jrain.core.validate.security.param.EncryptSourceParam;
import org.crap.jrain.core.validate.security.param.SecurityParam;
import org.crap.jrain.core.validate.support.BarScreenWrap;
import org.crap.jrain.core.validate.support.Param;
import org.crap.jrain.core.validate.support.param.MultiParam;
import org.crap.jrain.core.validate.support.param.SingleParam;
import org.crap.jrain.core.validate.support.param.ValidateParam;


  
/**  
* @ClassName: DataBarScreen  
* @Description: 数据屏障类，消息分发的请求数据必须经过
* @author Crap  
* @date 2017年11月10日  
*  
* @param <T>  
*/  
    
public abstract class DataBarScreen<T extends Map<?, ?>> implements Validation<T>, Security {
	
	private static Logger log = LogManager.getLogger(DataBarScreen.class);
	
	protected static final Map<String, BarScreenWrap> vMap = new HashMap<String, BarScreenWrap>();
	
	public static KeyPairCollection KPCOLLECTION = new DefaultKeyPairCollection();
	
	/**
	 * 通过{@Parameter} 生成 {@Param}抽象参数验证类型
	 * @author Crap
	 * @param parameter 参数注解
	 * @return ParamValidation
	 * @throws InstantiationException,IllegalAccessException
	 */
	protected static Param<?> generateParamValidation(Parameter parameter, String mapping) {
		Param<?> aParam = null;
		try {	
			Class<? extends Param<?>> param = parameter.type();
			aParam = param.getDeclaredConstructor().newInstance();
			
			Field[] fields = ClassUtil.getDeclaredFields(param);
			AccessibleObject.setAccessible(fields, true);
			
			log.info("regist [{}.{}] to Validation ", mapping.replace("$", "."), parameter.value());
			for (Field field : fields) {
				try {
					if(field.get(aParam) == null) {
						if(!SingleParam.class.isAssignableFrom(param) && field.getName().equals("value") && parameter.value().equals(""))
							throw new ParamIllegalRangeException(String.format("[%s] Parameter[type:%s] attribute [value] is \"\"", mapping.replace("$", "."), parameter.type().getName()));
						bindParamAttribute(aParam, parameter, field);
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					log.error("error", e);
				} catch (InvocationTargetException e) {
					e.getTargetException().printStackTrace();
					log.error("error", e.getTargetException());
				}
			}
			
			aParam.checkRangeLegitimate(mapping);
			aParam.setMapping(mapping);
			
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			log.error("error", e);
		}
		return aParam;
	}
	
	/**
	 * 绑定验证参数类型属性
	 * @author Crap
	 * @param aParam {@Param} 实体
	 * @param param {@Parameter} 实体
	 * @param field {@Param} 属性
	 * @throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	 */
	protected static void bindParamAttribute(Param<?> aParam, Parameter param, Field field) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Attribute[] attrs = param.attribute();
		
		for (int i = 0; i < attrs.length; i++) {
			Attribute attr = attrs[i];
			if(field.getName().equals(attr.name())) {
				if(!String.class.isAssignableFrom(field.getType()))
					throw new ParamAttrNotMatchException(field.getDeclaringClass(), attr);
				
				field.set(aParam, attr.value());
				return;
			}
		}
		
		try {
			Method method = Parameter.class.getDeclaredMethod(field.getName());
			Object value = method.invoke(param);
			
			if(field.getType().equals(String.class) && value.equals("")) {
				field.set(aParam, null);
				return;
			} else if(field.getName().equals("defaultValue") || field.getName().equals("min") || field.getName().equals("max")) {
				String v = (String)value;
				field.set(aParam, "".equals(v)?null:v);
				return;
			}
			field.set(aParam, value);
			
		} catch (NoSuchMethodException | SecurityException e) {
			log.warn("param:[{}] can not found match attribute:[{}] ", param.value(), field.getName());
		}
		
	}
	
	/**
	 * 接口参数适配验证
	 * @author Crap
	 * @param params 请求的参数Map
	 * @param objects String:mapping
	 * @return Errcode 错误码
	 * @throws ValidationException
	 */
	@Override
	public Errcode validate(T paramObject, Object... data) throws ValidationException {
		String mapping = String.valueOf(data[0]);
		
		BarScreenWrap wrap = vMap.get(mapping);
		
		if(wrap == null)
			return Errors.OK;
		
		if(wrap.isSecurity()){
			paramObject = decrypt0(paramObject, wrap.getSecurityParams());
		}
		log.debug("request input param:{}", paramObject);
		Errcode errcode = validateParams(paramObject, wrap.getParams());
		log.debug("request finally param:{}", paramObject);
		return errcode;
	}
	
	public T decrypt0(T paramObject, SecurityParam securityParams) throws ValidationException {
		Cipher cipher = createCipher();
		Security.setCipher(cipher);
		
		validateParams(paramObject, securityParams.getParams());
		
		return decrypt(cipher, paramObject, securityParams);
	}
	
	/**
	 * 适配类参数验证
	 * @author Crap
	 * @param request
	 * @param params 请求的参数Map
	 * @param aParams 参数验证类型集合
	 * @return Errcode 错误码
	 * @throws ValidationException
	 */
	public Errcode validateParams(T params, Param<?>[] aParams) throws ValidationException {
		StringBuffer nullParam = new StringBuffer();
		StringBuffer emptyParam = new StringBuffer("");
		if(validateRequired(nullParam, emptyParam, params, aParams)){
			nullParam.deleteCharAt(nullParam.length() - 1);
			throw new ParamRequiredException(nullParam.toString());
		}
		if(emptyParam.length() > 0){
			emptyParam.delete(emptyParam.length() - 1, emptyParam.length());
			throw new ParamEmptyException(emptyParam.toString());
		}
		return validateValue(params, aParams);
	}
	
	
	  
	/**  
	* @Title: registValidation  
	* @Description: 注册隔栅
	* @param @param serviceMethod
	* @param @param mapping    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	    
	public static void registBarScreen(String mapping, BarScreen barScreen) {
		
		Parameter[] params = barScreen.params();
		Param<?>[] aParams = new Param<?>[params.length];
		
		for (int i = 0; i < params.length; i++) {
			aParams[i] = generateParamValidation(params[i], mapping);
		}
		BarScreenWrap pumpWrap = new BarScreenWrap();
//		pumpWrap.setValue(barScreen.value());
		pumpWrap.setParams(aParams);
		pumpWrap.setSecurity(barScreen.security());
		pumpWrap.setDesc(barScreen.desc());
		if(pumpWrap.isSecurity()){
			pumpWrap.setSecurityParams(new SecurityParam());
		}
		vMap.put(mapping, pumpWrap);
	}

	  
	/**  
	* @Title: validateRequired  
	* @Description: 参数必要,为空验证
	* @param @param nullParam
	* @param @param emptyParam
	* @param @param params
	* @param @param aParams
	* @param @return
	* @param @throws ValidationException    参数  
	* @return boolean    返回类型  
	* @throws  
	*/  
	    
	public boolean validateRequired(StringBuffer nullParam, StringBuffer emptyParam, T params, Param<?>[] aParams) throws ValidationException{
		
		boolean required = false;
		
		for (Param<?> param : aParams) {
			//验证组合参数
			if(param instanceof MultiParam){
				MultiParam<?> mParam = (MultiParam<?>)param;
				if(mParam.getParams().length > 0){
					required = validateRequired(nullParam, emptyParam, params, mParam.getParams());
				}
				continue;
			}
			
			String paramName = param.getValue();
			Object value = getValue(params, paramName);

			if(value==null && param.getDefaultValue() != null && !param.getDefaultValue().equals("")) {
				value = param.getDefaultValue();
				setValue(params, param.getValue(), value);
			}
			
			if(!(param instanceof ValidateParam) && !param.checkRequired(value)) {
				required = true;
				nullParam.append(param.getDesc()).append(",");
			}
			if(!(param instanceof ValidateParam) && !param.checkEmpty(value)){
				emptyParam.append(param.getDesc()).append(",");
			}
		}
		
		return required;
	}
	
	  
	/**  
	* @Title: validateValue  
	* @Description: 参数值验证
	* @param @param params
	* @param @param aParams
	* @param @return
	* @param @throws ValidationException    参数  
	* @return Errcode    返回类型  
	* @throws  
	*/  
	
	public Errcode validateValue(T params, Param<?>[] aParams) throws ValidationException{
		for (Param<?> param : aParams) {
			Object value = getValue(params, param.getValue());
			
			//验证组合参数
			if(value == null && param instanceof MultiParam){
				MultiParam<?> mParam = (MultiParam<?>)param;
				if(mParam.getParams().length > 0){
					Errcode errcode = validateValue(params, mParam.getParams());
					if(!errcode.equals(Errors.OK))
						return errcode;
				}
			}
			
			if(value != null){
				Errcode errcode = param.validate(value);
				if(!errcode.equals(Errors.OK))
					return errcode;
				setValue(params, param.getValue(), param.cast(value));
			}
		}
		return Errors.OK;
	}
	
	  
	/**  
	* @Title: decrypt  
	* @Description: 数据解密
	* @param @param cipher
	* @param @param params
	* @param @param securityParams
	* @param @return
	* @param @throws ValidationException    参数  
	* @return T    返回类型  
	* @throws  
	*/  
	    
	public T decrypt(Cipher cipher, T params, SecurityParam securityParams) throws ValidationException{
		String encryptData = getValue(params, securityParams.getEncryptDataParam());
		String encryptSource = getValue(params, securityParams.getEncryptSourceParam());
		Number encryptFlag = getValue(params, securityParams.getEncryptFlagParam());
		
		String paramsStr;
		PrivateKey privateKey = KPCOLLECTION.get(encryptFlag.intValue()).getPrivate();
		if(encryptSource!= null && encryptSource.equalsIgnoreCase("js")){
			paramsStr = cipher.decryptJS(encryptData, privateKey);
		}else{
			paramsStr = cipher.decrypt(encryptData, privateKey);
		}
		try {
			paramsStr = URLDecoder.decode(paramsStr, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new ParamFormatException(new EncryptSourceParam(), e.getCause());
		}
		return parseParams(paramsStr);
	}

	@Override
	public Cipher createCipher() {
		return new CryptoCipher();
	}

	/**  
	* @Title: getValue  
	* @Description: 获取原始参数中转换类型后的值
	* @param @param params
	* @param @param param
	* @param @return    参数  
	* @return P    返回类型  
	* @throws  
	*/  
	protected <P> P getValue(T params, Param<P> param) {
		Object value = getValue(params, param.getValue());
		return value == null?null:param.cast(value);
	}
	  
	/**  
	* @Title: getValue  
	* @Description: 获取原始参数中的值
	* @param @param params
	* @param @param key
	* @param @return    参数  
	* @return Object    返回类型  
	* @throws  
	*/  
	    
	protected abstract Object getValue(T params, String key);	
	
	/**  
	* @Title: setValue  
	* @Description: 设置原始参数值
	* @param @param params
	* @param @param key
	* @param @param value    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	    
	protected abstract void setValue(T params, String key, Object value);
	
	  
	/**  
	* @Title: parseParams  
	* @Description: 请求数据转化为屏障处理数据类型
	* @param @param data
	* @param @return    参数  
	* @return T    返回类型  
	* @throws  
	*/  
	    
	public abstract T parseParams(String data);

	public static KeyPairCollection getKPCOLLECTION() {
		return KPCOLLECTION;
	}

	public static void setKPCOLLECTION(KeyPairCollection kPCOLLECTION) {
		KPCOLLECTION = kPCOLLECTION;
	}
	
}
