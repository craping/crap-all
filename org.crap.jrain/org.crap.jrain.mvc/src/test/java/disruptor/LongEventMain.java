package disruptor;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

public class LongEventMain {
	
	public static void main(String[] args) throws Exception {
		
		ThreadFactory threadFactory = Executors.defaultThreadFactory();
		
		LongEventFactory factory = new LongEventFactory();
		
		int bufferSize = 1024;
		
		Disruptor<LongEvent> disruptor = new Disruptor<>(factory, bufferSize, threadFactory);
		
		disruptor.handleEventsWith((event, sequence, endOfBatch) -> {
			System.out.println("Event: " + event.getValue());
		});
		
		disruptor.start();
		
		RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
		
        for (long l = 0; true; l++) {
        	List<Long> list = new ArrayList<>();
        	list.add(l);
            ringBuffer.publishEvent((event, sequence, data) -> {
            	event.setValue(data.get(0));
            }, list);
            Thread.sleep(1000);
        }
	}
}
