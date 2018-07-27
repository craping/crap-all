package org.crap.jrain.core.validate;

import org.crap.jrain.core.bean.result.Errcode;
import org.crap.jrain.core.validate.exception.ValidationException;

  
/**  
* @ClassName: Validation  
* @Description: 数据校验接口
* @author Crap  
* @date 2017年11月3日  
*  
* @param <T>  
*/  
    
public interface Validation<T> {
	public Errcode validate(T paramObject, Object... data) throws ValidationException;
}
