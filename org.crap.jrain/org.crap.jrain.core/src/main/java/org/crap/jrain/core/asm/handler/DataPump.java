package org.crap.jrain.core.asm.handler;


import java.util.Map;

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
public abstract class DataPump<TRequest, TResponse> implements ASMPump<Map<?,?>> {
	
	private TRequest request;
	
	private TResponse response;
	/**
	 *  ASM reflect to custom method
	 */
	public Errcode execute (Map<?,?> params) {System.out.println("DataPump"); return null;}
	
	
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