package test.org.jhub1.agent.statistics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jhub1.agent.statistics.JSONAdapter;
import org.jhub1.agent.statistics.Registry;
import org.jhub1.agent.statistics.XMLAdapter;
import org.jhub1.agent.thread.ThreadFileIn;
import org.jhub1.agent.thread.ThreadFileOut;
import org.jhub1.agent.thread.ThreadUDPIn;
import org.jhub1.agent.thread.ThreadUDPOut;
import org.jhub1.agent.thread.ThreadXMPP;
import org.jhub1.agent.thread.monitor.ThreadConfigReloadTest;
import org.jhub1.agent.thread.monitor.ThreadMonitor;
import org.jhub1.agent.thread.simulation.ThreadInQueueToOutQueueLoopback;
import org.joda.time.DateTime;
import org.junit.Test;

public class RegistryTest {
	
	@Test
	public void savingEventCounter() {
		Registry.getInstance().reset();
		Registry.getInstance().increaseEventCounter(Test.class, "testsavingEventCounter");
		Registry.getInstance().increaseEventCounter(Test.class, "testsavingEventCounter");
		Registry.getInstance().increaseEventCounter(Test.class, "testsavingEventCounter");
		Registry.getInstance().increaseEventCounter(Test.class, "testsavingEventCounter");
		assertEquals(new Long(4), Registry.getInstance().getEventCounter(Test.class, "testsavingEventCounter"));
	}
	
	@Test
	public void resetEventCounter() {
		Registry.getInstance().reset();
		Registry.getInstance().increaseEventCounter(Test.class, "testresetEventCounter");
		Registry.getInstance().increaseEventCounter(Test.class, "testresetEventCounter");
		Registry.getInstance().increaseEventCounter(Test.class, "testresetEventCounter");
		Registry.getInstance().resetEventCounter(Test.class, "testresetEventCounter");
		Registry.getInstance().increaseEventCounter(Test.class, "testresetEventCounter");
		assertEquals(new Long(1), Registry.getInstance().getEventCounter(Test.class, "testresetEventCounter"));
	}
	
	@Test
	public void savingValue() {
		Registry.getInstance().setValue(Test.class, "testing_value_name", new String("testing_value"));
		assertEquals("testing_value", Registry.getInstance().getValue(Test.class, "testing_value_name"));
	}
	
	@Test
	public void readingNonExistingValue() {
		assertEquals(null, Registry.getInstance().getValue(Test.class, "readingNonExistingValue"));
	}
	
	@Test
	public void readingNonExistingCounter() {
		assertEquals(new Long(0), Registry.getInstance().getEventCounter(Test.class, "testreadingNonExistingCounter"));
	}
	
	@Test
	public void readingNonExistingEventTimestamp() {
		assertEquals(null, Registry.getInstance().getEventTimestamp(Test.class, "testreadingNonExistingEventTimestamp"));
	}
	
	@Test
	public void savingEventTimestamp() {
		DateTime date1 = new DateTime();
		Registry.getInstance().setEventTimestamp(Test.class, "event");
		DateTime date3 = new DateTime();
		DateTime date2 = Registry.getInstance().getEventTimestamp(Test.class, "event");
		assertNotEquals(null, date2);
		assertTrue(date1.getMillis() <= date2.getMillis());
		assertTrue(date2.getMillis() <= date3.getMillis());		
	}
	
	@Test
	public void outputFormatterXML() {
		Registry.getInstance().reset();
		Registry.getInstance().increaseEventCounter(Test.class, "testsavingEventCounter");
		Registry.getInstance().increaseEventCounter(Test.class, "testsavingEventCounter");
		Registry.getInstance().increaseEventCounter(Test.class, "testsavingEventCounter");
		Registry.getInstance().increaseEventCounter(Test.class, "testsavingEventCounter");
		DateTime date = new DateTime();
		Registry.getInstance().setValue(Test.class, "testing_value_name", new String("testing_value"));
		Registry.getInstance().setValue(Test.class, "testing_datetime", date);
		String output = "<registry><statistics><items><item name='Test/testing_value_name'>testing_value</item><item name='Test/testing_datetime'>" + date.getMillis() + "</item><item name='Test/testsavingEventCounter'>4</item></items></statistics></registry>";
		assertEquals(output, Registry.getInstance().toFormatedOutput(new XMLAdapter()));
	}
	
	@Test
	public void outputFormatterJSON() {
		Registry.getInstance().reset();
		Registry.getInstance().increaseEventCounter(Test.class, "testsavingEventCounter");
		Registry.getInstance().increaseEventCounter(Test.class, "testsavingEventCounter");
		Registry.getInstance().increaseEventCounter(Test.class, "testsavingEventCounter");
		Registry.getInstance().increaseEventCounter(Test.class, "testsavingEventCounter");
		DateTime date = new DateTime();
		Registry.getInstance().setValue(Test.class, "testing_value_name", new String("testing_value"));
		Registry.getInstance().setValue(Test.class, "testing_datetime", date);
		String output = "{\"registry\":{\"statistics\":{\"items\":[{\"name\":\"Test/testsavingEventCounter\",\"value\":4},{\"name\":\"Test/testing_datetime\",\"value\":" + date.getMillis() + "},{\"name\":\"Test/testing_value_name\",\"value\":\"testing_value\"}]}}}";
		assertEquals(output, Registry.getInstance().toFormatedOutput(new JSONAdapter()));
	}
	
	@Test
	public void threadSafetynessTestCounter() throws InterruptedException {
		int numberOfThreads = 5000;
		Long expectedNumber = (long) (numberOfThreads * 5);
		
		Registry.getInstance().reset();
		
		List<Runnable> tasks = new ArrayList<>();
		
		for(int i=0; i<numberOfThreads; i++) {
			tasks.add((Runnable) new RegistryCounterIncTestThread());
		}
        
		if (!tasks.isEmpty()) {
			ExecutorService executor = Executors.newFixedThreadPool(tasks
					.size());
			for (Runnable task : tasks) {
				executor.submit(task);
			}
		}
		
		Thread.sleep(1000); //give some time to finish
		assertEquals(expectedNumber, Registry.getInstance().getEventCounter(RegistryCounterIncTestThread.class, RegistryCounterIncTestThread.NAME));
	}
	

	public void threadSafetynessTestCounterReset() throws InterruptedException {
		int numberOfThreads = 10;
		Long expectedNumber = (long) (numberOfThreads * 5);
		Registry.getInstance().reset();
		List<Runnable> tasks = new ArrayList<>();
		
		for(int i=0; i<numberOfThreads; i++) {
			tasks.add((Runnable) new RegistryCounterIncResetTestThread());
		}
        
	//	long startTime = System.nanoTime();
        
		if (!tasks.isEmpty()) {
			ExecutorService executor = Executors.newFixedThreadPool(tasks
					.size());
			for (Runnable task : tasks) {
				executor.submit(task);
			}
		}
		
		
   //     long endTime = System.nanoTime();
   //     double processingTime = (endTime - startTime)/Math.pow(10, 6);
 
//        System.out.println("Processing Time (msec): " + processingTime + " Count: " + counter.getCount());
		
		Thread.sleep(1000); //give some time to finish
		assertEquals(expectedNumber, Registry.getInstance().getEventCounter(RegistryCounterIncResetTestThread.class, "TestingCounter"));
		assertTrue(true);
	}
}
