package org.crap.jrain.mvc.netty.render;

import org.crap.jrain.core.bean.result.Errcode;
import org.crap.jrain.core.bean.result.criteria.DataResult;
import org.crap.jrain.core.error.support.Errors;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;

public class NettyHTMLRender extends NettyRender {
	
	public static final String DEFAULT_CONTENT_TYPE = "text/html;charset=UTF-8";
	
	public static final String DEFAULT_FORMAT = "html";
	
	public NettyHTMLRender() {
		setContentType(DEFAULT_CONTENT_TYPE);
		setFormat(DEFAULT_FORMAT);
	}
	
	@Override
	public void render(Errcode result, FullHttpRequest request, Channel channel) {
		if(!(result instanceof DataResult)) {
			writeResponse(true, request, channel, Errors.DATA_TYPE_ERROR.getMsg());
			return;
		}
		DataResult dataResult = (DataResult)result;
		writeResponse(true, request, channel, dataResult.getData().getInfo().toString());
	}
}
