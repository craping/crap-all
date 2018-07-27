package org.crap.jrain.mvc.netty;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.crap.jrain.core.launch.Boot;
import org.crap.jrain.core.validate.DataType;
import org.crap.jrain.mvc.Render;
import org.crap.jrain.mvc.Treatment;
import org.crap.jrain.mvc.netty.decoder.URIDecoder;
import org.crap.jrain.mvc.netty.render.NettyHTMLRender;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.util.CharsetUtil;

  
/**  
* @ClassName: NettyTreatment  
* @Description: Netty的请求处理类
* @author Crap  
* @date 2017年11月10日  
*    
*/  
    
public class NettyTreatment extends Treatment<FullHttpRequest, Channel> {

	private Render<FullHttpRequest, Channel> defaultRender = new NettyHTMLRender();
	
	private static final HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE);
	
	public NettyTreatment(Boot boot) {
		super(boot);
	}

	@Override
	protected String getFormat(FullHttpRequest request) {
		
		URIDecoder uriDecoder = new URIDecoder(request.uri());
		
		Map<?, ?> uriAttributes = uriDecoder.parameters();
		
		return String.valueOf(uriAttributes.get("format"));
	}
	
	@Override
	protected Map<?, ?> getRawParams(FullHttpRequest request, DataType dataType) {
		
		String content;
		
		URIDecoder uriDecoder;
		switch (dataType) {
		case JSON:
			content = request.content().toString(CharsetUtil.UTF_8);
			return bsFactory.createDataBarScreen(dataType).parseParams(content);
		default:
			if(request.method().equals(HttpMethod.GET)) {
				uriDecoder = new URIDecoder(request.uri());
				return uriDecoder.parameters();
			}
			
			HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(factory, request);
			if(decoder.isMultipart()) {
			
				Map<String, Object> rawParams = new HashMap<>();
				
				while (decoder.hasNext()) {
					InterfaceHttpData data = decoder.next();
					if (data != null) {
						try {
							switch (data.getHttpDataType()) {
							case Attribute:
								Attribute attr = (Attribute)data;
								rawParams.put(attr.getName(), attr.getValue());
								break;
							case FileUpload:
								FileUpload file = (FileUpload)data;
								rawParams.put(file.getName(), file.get());
								break;
							default:
								rawParams.put(data.getName(), data);
								break;
							}
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							data.release();
						}
					}
				}
				return rawParams;
			}
			
			content = request.content().toString(CharsetUtil.UTF_8);
			uriDecoder = new URIDecoder(content, false);
			return uriDecoder.parameters();
		}
	}

	@Override
	protected DataType getDataType(FullHttpRequest request) {
		if(request.method().equals(HttpMethod.GET))
			return DataType.MAP;
		
		HttpHeaders headers = request.headers();
		String contentType = headers.get(HttpHeaderNames.CONTENT_TYPE).split(";")[0];
		
		if(contentType.contains("json"))
			return DataType.JSON;
		return DataType.MAP;
	}

	@Override
	protected Render<FullHttpRequest, Channel> getDefaultRender() {
		return defaultRender;
	}

}
