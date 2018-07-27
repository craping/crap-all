package org.crap.jrain.mvc;


import org.crap.jrain.core.bean.result.Errcode;

  
/**  
* @ClassName: Render  
* @Description: 返回消息渲染接口
* @author Crap  
* @date 2017年11月10日  
*  
* @param <TRequest>
* @param <TResponse>  
*/  
    
public interface Render<TRequest, TResponse> {
	
	String getContentType();
	
	String getFormat();
	
	void render(Errcode result, TRequest request, TResponse response);
}
