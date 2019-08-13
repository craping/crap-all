package org.crap.jrain.core.asm.handler;


import java.io.Serializable;

import org.crap.jrain.core.asm.annotation.Separate;
import org.crap.jrain.core.bean.result.Errcode;

  
/**  
* @ClassName: DataPump  
* @Description: 数据泵  
* @author Crap  
 * @param <TRequest>
 * @param <TResponse>
* @date 2017年11月10日  
*    
*/  

@Separate("execute")
public abstract class ObjectPump<TRequest, TResponse> implements ASMPump<Serializable> {
	
	private TRequest request;
	
	private TResponse response;
	/**
	 *  ASM reflect to custom method
	 */
	public Errcode execute (Serializable params) {System.out.println("DataPump"); return null;}
	
	
	public TRequest getRequest() {
		return request;
	}
	public void setRequest(TRequest request) {
		this.request = request;
	}
	public TResponse getResponse() {
		return response;
	}
	public void setResponse(TResponse response) {
		this.response = response;
	};
	
	
}