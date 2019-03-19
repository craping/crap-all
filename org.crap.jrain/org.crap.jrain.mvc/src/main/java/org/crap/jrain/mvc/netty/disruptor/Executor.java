package org.crap.jrain.mvc.netty.disruptor;

import com.lmax.disruptor.WorkHandler;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;

public class Executor implements WorkHandler<RequestEvent<FullHttpRequest, Channel>> {

	@Override
	public void onEvent(RequestEvent<FullHttpRequest, Channel> event) throws Exception {
		try {
    		event.getTreatment().process(event.getMapping(), event.getRequest(), event.getResponse());
        } catch (Exception e) {
        	e.printStackTrace();
        	event.getResponse().close();
		} finally {
//			if(event.getRequest().content().isReadable())
//				ReferenceCountUtil.release(event.getRequest());
            event.clear();
        }
	}

}
