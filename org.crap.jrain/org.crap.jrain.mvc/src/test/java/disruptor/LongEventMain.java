package disruptor;

import java.util.concurrent.atomic.AtomicLong;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

public class LongEventMain {
	
	public static AtomicLong count = new AtomicLong(0);
	
	public static void main(String[] args) throws Exception {
		
		int bufferSize = 8;
		
		Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);
//		Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new YieldingWaitStrategy());
		disruptor.handleEventsWith((event, sequence, endOfBatch) -> {
			System.out.printf("H1-T%s Event: %s\n",Thread.currentThread().getId(), event.getValue());
//			System.out.println("wait H1");
			Thread.sleep(1000);
//			System.out.println("go on H1");
//			System.out.println(disruptor.getRingBuffer().getMinimumGatingSequence() + "-" + disruptor.getRingBuffer().getCursor());
		}
		,(event, sequence, endOfBatch) -> {
			System.out.printf("H2-T%s Event: %s\n",Thread.currentThread().getId(), event.getValue());
			Thread.sleep(1000);
//			System.out.println(disruptor.getRingBuffer().getMinimumGatingSequence() + "-" + disruptor.getRingBuffer().getCursor());
		});

		
//		disruptor.handleEventsWithWorkerPool((event) -> {
//			System.out.printf("H1-T%s Event: %s\n",Thread.currentThread().getId(), event.getValue());
//			System.out.println("wait H1");
//			Thread.sleep(10000);
//			System.out.println("go on H1");
//		},(event) -> {
//			System.out.printf("H2-T%s Event: %s\n",Thread.currentThread().getId(), event.getValue());
//			System.out.println("wait H2");
//			Thread.sleep(5000);
//			System.out.println("go on H2");
//		},(event) -> {
//			System.out.printf("H3-T%s Event: %s\n",Thread.currentThread().getId(), event.getValue());
//			System.out.println("wait H3");
//			Thread.sleep(1000);
//			System.out.println("go on H3");
//		},(event) -> {
//			System.out.printf("H4-T%s Event: %s\n",Thread.currentThread().getId(), event.getValue());
//		});

		disruptor.start();
		
		
		
		RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
		
//		for (int i = 0; i < 3; i++) {
//			new Thread(() -> {
//				for (long l = 0; l < 5; l++) {
//					ringBuffer.publishEvent((event, sequence, data) -> {
//						event.setValue(count.getAndIncrement());
//					}, count);
//				}
//			}).start();
//		}
		
//		while(true){
	        for (long l = 0; l < 100; l++) {
	            ringBuffer.publishEvent((event, sequence, data) -> {
	            	event.setValue(count.getAndIncrement());
	            }, count);
	        }
	        System.out.println("生产完毕");
//	        Thread.sleep(1000);
//		}
        Thread.sleep(5000000);
	}
}
