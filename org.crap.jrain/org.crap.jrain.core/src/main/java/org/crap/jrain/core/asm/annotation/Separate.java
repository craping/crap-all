package org.crap.jrain.core.asm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


  
/**  
* @ClassName: Separate  
* @Description: {@MethodEnhancer} 在动态生成消息分发类的时候会寻找 该注解的类
* @author Crap  
* @date 2017年11月3日  
*    
*/  
    
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Separate {
	String value();
}
