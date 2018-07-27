package org.crap.jrain.mvc.netty;

import org.crap.jrain.mvc.Treatment;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

  
/**  
* @ClassName: ServerDispatcher  
* @Description: Netty消息分发入口
* @author Crap  
* @date 2017年11月10日  
*    
*/  
    
public class ServerDispatcher extends SimpleChannelInboundHandler<FullHttpRequest> {
	
	private FullHttpRequest request;
	
	private Treatment<FullHttpRequest, Channel> treatment;
	
	public ServerDispatcher(Treatment<FullHttpRequest, Channel> treatment) {
		this.treatment = treatment;
	}
	
	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
		messageReceived(ctx, request);
	}
	
	public void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception{
		this.request = request;
		
		if (request.uri().equals("/favicon.ico")) {
        	return;
		}
		String mapping = getRequestMapping();
		
		treatment.process(mapping, request, ctx.channel());
		
	}
	
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
	
	  
	/**  
	* @Title: getRequestMapping  
	* @Description: 从uri中解析出消息执行方法的映射mapping
	* @param @return
	* @param @throws Exception    参数  
	* @return String    返回类型  
	* @throws  
	*/  
	    
	private String getRequestMapping() throws Exception{
		String uri = request.uri();
		int endIndex = uri.indexOf("?");
		endIndex = endIndex==-1?uri.length():endIndex;
		return uri.substring(1, endIndex).replace("/", "$");
	}
}
