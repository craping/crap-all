package org.crap.jrain.mvc;

  
/**  
* @ClassName: AbstractRender  
* @Description: 公共消息返回渲染
* @author Crap  
* @date 2017年11月10日  
*  
* @param <TRequest>
* @param <TResponse>  
*/  
    
public abstract class AbstractRender<TRequest, TResponse> implements Render<TRequest, TResponse> {
	
	private String contentType = "text/html;charset=ISO-8859-1";
	
	private String format = "html";
	
	@Override
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
}
