package org.crap.jrain.mvc.netty;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.crap.jrain.mvc.Treatment;
import org.crap.jrain.mvc.netty.disruptor.RequestEvent;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;

  
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
	
	private static final int BUFFER_SIZE = 8 * 1024;
	
	private static final ThreadFactory THREAD_FACTORY = Executors.defaultThreadFactory();
	
	private static final ThreadLocal<Disruptor<RequestEvent<FullHttpRequest, Channel>>> THREAD_LOCAL = new ThreadLocal<Disruptor<RequestEvent<FullHttpRequest, Channel>>>() {
        @Override
        protected Disruptor<RequestEvent<FullHttpRequest, Channel>> initialValue() {
            Disruptor<RequestEvent<FullHttpRequest, Channel>> disruptor = new Disruptor<>(RequestEvent<FullHttpRequest, Channel>::new, BUFFER_SIZE, THREAD_FACTORY, ProducerType.SINGLE, new YieldingWaitStrategy());
            disruptor.handleEventsWith((event, sequence, endOfBatch) -> {
            	try {
            		event.getTreatment().process(event.getMapping(), event.getRequest(), event.getResponse());
                } catch (Exception e) {
                	e.printStackTrace();
                	event.getResponse().close();
				} finally {
					if(event.getRequest().content().isReadable())
						ReferenceCountUtil.release(event.getRequest());
                    event.clear();
                }
    		});
            disruptor.start();
            return disruptor;
        }
    };
    
	public ServerDispatcher(Treatment<FullHttpRequest, Channel> treatment) {
		super(false);
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
		
//		treatment.process(mapping, request, ctx.channel());
		
		RingBuffer<RequestEvent<FullHttpRequest, Channel>> ringBuffer = THREAD_LOCAL.get().getRingBuffer();
		RequestEvent<FullHttpRequest, Channel> requestEvent = new RequestEvent<>();
		requestEvent.setMapping(mapping);
		requestEvent.setRequest(request);
		requestEvent.setResponse(ctx.channel());
		requestEvent.setTreatment(treatment);
		
		ringBuffer.publishEvent((event, sequence, data) -> {
			event.setMapping(data.getMapping());
			event.setRequest(data.getRequest());
			event.setResponse(data.getResponse());
			event.setTreatment(data.getTreatment());
        }, requestEvent);
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
