package org.crap.jrain.core.asm.handler;


import java.util.Map;

import org.crap.jrain.core.asm.annotation.Separate;
import org.crap.jrain.core.bean.result.Errcode;

  
/**  
* @ClassName: DataPump  
* @Description: 数据泵  
* @author Crap  
* @date 2017年11月10日  
*    
*/  

@Separate("execute")
public abstract class DataPump<T extends Map<?,?>> implements ASMPump<T> {
	/**
	 *  ASM reflect to custom method
	 */
	public Errcode execute (T params) {return null;};
}