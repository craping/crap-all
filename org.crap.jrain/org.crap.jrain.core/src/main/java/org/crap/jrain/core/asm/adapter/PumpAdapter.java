package org.crap.jrain.core.asm.adapter;

import java.util.Map;

import org.crap.jrain.core.asm.MethodEnhancer;
import org.crap.jrain.core.asm.handler.ASMPump;

  
/**  
* @ClassName: ClassAdapter  
* @Description: {@DataPump} 解析适配抽象类 
* @author Crap  
* @date 2017年11月2日  
*    
*/  
    
public abstract class PumpAdapter {
	
	protected final MethodEnhancer mete = new MethodEnhancer();
	
	public abstract Map<String, ASMPump<Map<?,?>>> resolve(Class<?> clazz);
	
}
