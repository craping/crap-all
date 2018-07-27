package org.crap.jrain.mvc.netty.render;

import org.crap.jrain.core.bean.result.Errcode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;

public class NettyJSONRender extends NettyRender {
	
	public static final String DEFAULT_CONTENT_TYPE = "application/json;charset=UTF-8";
	
	public static final String DEFAULT_FORMAT = "json";

	protected static ObjectMapper MAPPER = new ObjectMapper();
	
	static{
		MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
	}
	
	public NettyJSONRender() {
		setContentType(DEFAULT_CONTENT_TYPE);
		setFormat(DEFAULT_FORMAT);
	}
	
	@Override
	public void render(Errcode result, FullHttpRequest request, Channel channel) {
		try {
			String json = MAPPER.writeValueAsString(result);
			writeResponse(true, request, channel, json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
