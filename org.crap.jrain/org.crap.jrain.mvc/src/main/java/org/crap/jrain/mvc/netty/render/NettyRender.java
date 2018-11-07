package org.crap.jrain.mvc.netty.render;

import org.crap.jrain.core.validate.security.Cipher;
import org.crap.jrain.core.validate.security.Security;
import org.crap.jrain.mvc.AbstractRender;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

  
/**  
* @ClassName: NettyRender  
* @Description: Netty框架主消息返回渲染模型
* @author Crap  
* @date 2017年11月10日  
*    
*/  
    
public abstract class NettyRender extends AbstractRender<FullHttpRequest, Channel> {
	
	  
	/**  
	* @Title: writeResponse  
	* @Description: 消息返回
	* @param @param forceClose
	* @param @param request
	* @param @param channel
	* @param @param json    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	    
	protected void writeResponse(boolean forceClose, FullHttpRequest request, Channel channel, String json){
		FullHttpResponse response = renderResponse(json, getContentType());
		boolean close = isClose(request);
		if(!close && !forceClose){
			response.headers().add(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(response.content().readableBytes()));
		}
		ChannelFuture future = channel.writeAndFlush(response);
		if(close || forceClose){
			future.addListener(ChannelFutureListener.CLOSE);
		}
	}
	
	  
	/**  
	* @Title: renderResponse  
	* @Description: 渲染
	* @param @param content
	* @param @param contentType
	* @param @return    参数  
	* @return FullHttpResponse    返回类型  
	* @throws  
	*/  
	    
	protected FullHttpResponse renderResponse(String content, String contentType){
		
		if(content == null){
			content = "";
		}
		
		Cipher cipher = Security.getCipher();
		if(cipher != null && cipher.getClientAESKey() != null)
			content = cipher.encrypt(content);
			
		ByteBuf byteBuf = Unpooled.copiedBuffer(content, CharsetUtil.UTF_8);
//		ByteBuf byteBuf = Unpooled.wrappedBuffer(content.getBytes(CharsetUtil.UTF_8));
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
		response.headers().add(HttpHeaderNames.CONTENT_TYPE, contentType);
		response.headers().add(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(byteBuf.readableBytes()));
		return response;
	}
	
	protected boolean isClose(FullHttpRequest request){
		return (request.headers().contains(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE, true)
				|| request.protocolVersion().equals(HttpVersion.HTTP_1_0)
				&& !request.headers().contains(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE, true));
	}
}
