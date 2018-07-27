package org.crap.jrain.mvc.netty.render;

import org.crap.jrain.core.bean.result.Errcode;
import org.crap.jrain.core.bean.result.Result;
import com.thoughtworks.xstream.XStream;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;

public class NettyImageRender extends NettyRender {
	
	public static final String DEFAULT_CONTENT_TYPE = "application/xml;charset=UTF-8";
	
	public static final String DEFAULT_FORMAT = "xml";
	
	protected static XStream XSTREAM = new XStream();
	
	static {
		XSTREAM.alias("result", Result.class);
	}
	
	public NettyImageRender() {
		setContentType(DEFAULT_CONTENT_TYPE);
		setFormat(DEFAULT_FORMAT);
	}
	
	@Override
	public void render(Errcode result, FullHttpRequest request, Channel channel) {
		String xml = XSTREAM.toXML(result);
		writeResponse(true, request, channel, xml);
	}
}
