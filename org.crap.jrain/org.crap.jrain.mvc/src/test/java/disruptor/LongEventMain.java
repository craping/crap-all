package disruptor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

public class LongEventMain {
	
	public static AtomicLong count = new AtomicLong(0);
	
	public static void main(String[] args) throws Exception {
		
		int bufferSize = 8;
		
		Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);
		
		disruptor.handleEventsWith((event, sequence, endOfBatch) -> {
			Thread.sleep(2000);
			System.out.println("H1 Event: " + event.getValue());
		}
//		,(event, sequence, endOfBatch) -> {
//			System.out.println("H2 Event: " + event.getValue());
//		}
		);
//		disruptor.handleEventsWithWorkerPool((event) -> {
//			Thread.sleep(3000);
//			System.out.println("Sleep H1 Event: " + event.getValue());
//		},(event) -> {
//			System.out.println("H2 Event: " + event.getValue());
//		},(event) -> {
//			System.out.println("H3 Event: " + event.getValue());
//		},(event) -> {
//			System.out.println("H4 Event: " + event.getValue());
//		},(event) -> {
//			System.out.println("H5 Event: " + event.getValue());
//		});
		disruptor.start();
		
		
		
		for (int i = 0; i < 3; i++) {
			new Thread(() -> {
				RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
				for (long l = 0; l < 5; l++) {
					List<Long> list = new ArrayList<>();
					list.add(count.getAndIncrement());
					ringBuffer.publishEvent((event, sequence, data) -> {
						event.setValue(data.get(0));
					}, list);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		
		RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
		
        for (long l = 0; l < 5; l++) {
        	List<Long> list = new ArrayList<>();
        	list.add(count.getAndIncrement());
            ringBuffer.publishEvent((event, sequence, data) -> {
            	event.setValue(data.get(0));
            }, list);
            Thread.sleep(500);
        }
        
        Thread.sleep(5000000);
	}
}
