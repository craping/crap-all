package org.crap.jrain.core.asm.handler;


import org.crap.jrain.core.bean.result.Errcode;


  
/**  
* @ClassName: ASMPump  
* @Description: 
* @author Crap  
* @date 2017年11月3日  
*  
* @param <T>  
*/  
    
public interface ASMPump<T> {
	/**
	 *  ASM reflect to custom method
	 */
	public Errcode execute (T params);
}