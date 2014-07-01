package test.org.jhub1.agent.thread.data;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.BlockingQueue;

import org.jhub1.agent.AgentSample;
import org.jhub1.agent.ChannelType;
import org.jhub1.agent.Sample;
import org.jhub1.agent.thread.data.ConcurrentFridge;
import org.jhub1.agent.thread.data.DataExchangeImpl;
import org.jhub1.agent.thread.data.Fridge;
import org.junit.Test;
import org.mockito.Mockito;

public class ConcurrentFridgeTest {
	
	@Test
	public void checkFileOutActiveFileInput() throws InterruptedException {	
		
		Fridge fridge = new ConcurrentFridge(500);
		
		fridge.put("AAA", "AAA");
		fridge.put("BBB", "BBB");
		fridge.put("CCC", "CCC");
		fridge.put("DDD", "DDD");
		
		Thread.sleep(1000);
		
		fridge.put("EEE", "EEE");
		fridge.put("FFF", "FFF");
		
		String drop = (String) fridge.drop();
/* */
/*
		DataExchangeImpl dex = new DataExchangeImpl(prop);
		
		Sample s1 = new AgentSample(ChannelType.FILE, "qazqaz:123123");
		dex.queueOutputEndPoint(s1);
		
		//The sample should end up in the file queue 
		BlockingQueue<Sample> f = dex.getOutputFileQueue();
		assertEquals(1, f.size());
		Sample s11 = f.poll();
		assertEquals("123123", s11.getValue());
		assertEquals("qazqaz", s11.getName());	
		BlockingQueue<Sample> u = dex.getOutputUDPQueue();
		assertEquals(0, u.size());*/
	}
}
