package test.org.jhub1.agent.thread.data;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.BlockingQueue;

import org.jhub1.agent.ChannelType;
import org.jhub1.agent.Sample;
import org.jhub1.agent.AgentSample;
import org.jhub1.agent.configuration.PropertiesImpl;
import org.jhub1.agent.thread.data.DataExchangeImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class DataExchangeTest {

	@Mock
	private static PropertiesImpl prop;

	@BeforeClass
	public static void setUpBeforeClass() {
		MockitoAnnotations.initMocks(DataExchangeTest.class);
		DataExchangeTest.prop = Mockito.mock(PropertiesImpl.class);
		// Instantiation essentials:
		Mockito.when(DataExchangeTest.prop.getInputSamplesQueueSize()).thenReturn(200);
		Mockito.when(DataExchangeTest.prop.getOutputSamplesQueueSize()).thenReturn(200);
	}

	@Test
	public void checkFileOutActiveFileInput() {	
		// Test essentials:
		Mockito.when(DataExchangeTest.prop.isFileOutActive()).thenReturn(true);
		Mockito.when(DataExchangeTest.prop.isUDPOutActive()).thenReturn(true);
		Mockito.when(DataExchangeTest.prop.isOutputChannelFileDefault()).thenReturn(true);
		Mockito.when(DataExchangeTest.prop.isOutputChannelUDPDefault()).thenReturn(true);

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
		assertEquals(0, u.size());
	}
	
	@Test
	public void checkDefaultOutput_FileInput() {	
		// Test essentials:
		Mockito.when(DataExchangeTest.prop.isFileOutActive()).thenReturn(false);  //
		Mockito.when(DataExchangeTest.prop.isUDPOutActive()).thenReturn(true);
		Mockito.when(DataExchangeTest.prop.isOutputChannelFileDefault()).thenReturn(false);  //
		Mockito.when(DataExchangeTest.prop.isOutputChannelUDPDefault()).thenReturn(true);
		Mockito.when(DataExchangeTest.prop.isDefaultOutputChannelAllowed()).thenReturn(true);
		
		DataExchangeImpl dex = new DataExchangeImpl(prop);
		
		Sample s1 = new AgentSample(ChannelType.FILE, "qazqaz:123123");
		dex.queueOutputEndPoint(s1);
		
		//The sample should end up in the default queue which is UDP 
		BlockingQueue<Sample> f = dex.getOutputFileQueue();
		assertEquals(0, f.size());
		
		BlockingQueue<Sample> u = dex.getOutputUDPQueue();
		assertEquals(1, u.size());
		Sample s11 = u.poll();
		assertEquals("123123", s11.getValue());
		assertEquals("qazqaz", s11.getName());
	}

	
	@Test
	public void checkDefaultOutputDisabled_FileInput() {	
		// Test essentials:
		Mockito.when(DataExchangeTest.prop.isFileOutActive()).thenReturn(false);  //
		Mockito.when(DataExchangeTest.prop.isUDPOutActive()).thenReturn(true);
		Mockito.when(DataExchangeTest.prop.isOutputChannelFileDefault()).thenReturn(true);
		Mockito.when(DataExchangeTest.prop.isOutputChannelUDPDefault()).thenReturn(true);
		Mockito.when(DataExchangeTest.prop.isDefaultOutputChannelAllowed()).thenReturn(true);
		
		DataExchangeImpl dex = new DataExchangeImpl(prop);
		
		Sample s1 = new AgentSample(ChannelType.FILE, "qazqaz:123123");
		dex.queueOutputEndPoint(s1);
		
		//The sample be redirected to default queue FILE however file is disabled so it goes to next enabled default UDP
		BlockingQueue<Sample> f = dex.getOutputFileQueue();
		assertEquals(0, f.size());
		
		BlockingQueue<Sample> u = dex.getOutputUDPQueue();
		assertEquals(1, u.size());
		Sample s11 = u.poll();
		assertEquals("123123", s11.getValue());
		assertEquals("qazqaz", s11.getName());
	}
	
	@Test
	public void checkDefaultOutputDisabled_UDPInput() {	
		// Test essentials:
		Mockito.when(DataExchangeTest.prop.isFileOutActive()).thenReturn(true);
		Mockito.when(DataExchangeTest.prop.isUDPOutActive()).thenReturn(false);  //
		Mockito.when(DataExchangeTest.prop.isOutputChannelFileDefault()).thenReturn(true);
		Mockito.when(DataExchangeTest.prop.isOutputChannelUDPDefault()).thenReturn(true);
		Mockito.when(DataExchangeTest.prop.isDefaultOutputChannelAllowed()).thenReturn(true);
		
		DataExchangeImpl dex = new DataExchangeImpl(prop);
		
		Sample s1 = new AgentSample(ChannelType.UDP, "qazqaz:123123");
		dex.queueOutputEndPoint(s1);
		
		//The sample be redirected to default queue UDP however UDP is disabled so it goes to next enabled default > FILE
		BlockingQueue<Sample> f = dex.getOutputFileQueue();
		assertEquals(1, f.size());
		Sample s11 = f.poll();
		assertEquals("123123", s11.getValue());
		assertEquals("qazqaz", s11.getName());

		
		BlockingQueue<Sample> u = dex.getOutputUDPQueue();
		assertEquals(0, u.size());
	}
	
	@Test
	public void checkDefaultOutputDisabledcc_UDPInput() {	
		// Test essentials:
		Mockito.when(DataExchangeTest.prop.isFileOutActive()).thenReturn(true);
		Mockito.when(DataExchangeTest.prop.isUDPOutActive()).thenReturn(true);
		Mockito.when(DataExchangeTest.prop.isOutputChannelFileDefault()).thenReturn(true);
		Mockito.when(DataExchangeTest.prop.isOutputChannelUDPDefault()).thenReturn(true);
		Mockito.when(DataExchangeTest.prop.isDefaultOutputChannelAllowed()).thenReturn(true);
		
		DataExchangeImpl dex = new DataExchangeImpl(prop);
		
		Sample s1 = new AgentSample(ChannelType.UDP, "qazqaz:123123");
		dex.queueOutputEndPoint(s1);
		
		//The sample be redirected to default queue UDP with UDP out enabled
		BlockingQueue<Sample> f = dex.getOutputFileQueue();
		assertEquals(0, f.size());

		
		BlockingQueue<Sample> u = dex.getOutputUDPQueue();
		assertEquals(1, u.size());
		Sample s11 = u.poll();
		assertEquals("123123", s11.getValue());
		assertEquals("qazqaz", s11.getName());
	}
	
	@Test
	public void checkDefaultOutputNotAllowed_UDPInput() {
		//Sample is UDP but no Default output is allowed and only FILE is active
		// Test essentials:
		Mockito.when(DataExchangeTest.prop.isFileOutActive()).thenReturn(true);
		Mockito.when(DataExchangeTest.prop.isUDPOutActive()).thenReturn(false);  //
		Mockito.when(DataExchangeTest.prop.isOutputChannelFileDefault()).thenReturn(true);
		Mockito.when(DataExchangeTest.prop.isOutputChannelUDPDefault()).thenReturn(true);
		Mockito.when(DataExchangeTest.prop.isDefaultOutputChannelAllowed()).thenReturn(false);  //
		
		DataExchangeImpl dex = new DataExchangeImpl(prop);
		
		Sample s1 = new AgentSample(ChannelType.UDP, "qazqaz:123123");
		dex.queueOutputEndPoint(s1);
		
		BlockingQueue<Sample> f = dex.getOutputFileQueue();
		assertEquals(0, f.size());
		
		BlockingQueue<Sample> u = dex.getOutputUDPQueue();
		assertEquals(0, u.size());
	}
	
	@Test
	public void checkDefaultOutputNotAllowed_FILEInput() {
		//Sample is FILE but no Default output is allowed and only UDP is active
		
		// Test essentials:
		Mockito.when(DataExchangeTest.prop.isFileOutActive()).thenReturn(false);  //
		Mockito.when(DataExchangeTest.prop.isUDPOutActive()).thenReturn(true);
		Mockito.when(DataExchangeTest.prop.isOutputChannelFileDefault()).thenReturn(true);
		Mockito.when(DataExchangeTest.prop.isOutputChannelUDPDefault()).thenReturn(true);
		Mockito.when(DataExchangeTest.prop.isDefaultOutputChannelAllowed()).thenReturn(false);  //
		
		DataExchangeImpl dex = new DataExchangeImpl(prop);
		
		Sample s1 = new AgentSample(ChannelType.FILE, "qazqaz:123123");
		dex.queueOutputEndPoint(s1);

		BlockingQueue<Sample> f = dex.getOutputFileQueue();
		assertEquals(0, f.size());

		
		BlockingQueue<Sample> u = dex.getOutputUDPQueue();
		assertEquals(0, u.size());
	}
	
	@Test
	public void checkDefaultOutputNotAllowedqqqq_FILEInput() {
		//Sample is FILE but no Default output is allowed and only UDP is active
		
		// Test essentials:
		Mockito.when(DataExchangeTest.prop.isFileOutActive()).thenReturn(false);  //
		Mockito.when(DataExchangeTest.prop.isUDPOutActive()).thenReturn(true);
		Mockito.when(DataExchangeTest.prop.isOutputChannelFileDefault()).thenReturn(true);
		Mockito.when(DataExchangeTest.prop.isOutputChannelUDPDefault()).thenReturn(true);
		Mockito.when(DataExchangeTest.prop.isDefaultOutputChannelAllowed()).thenReturn(false);  //
		
		DataExchangeImpl dex = new DataExchangeImpl(prop);
		
		Sample s1 = new AgentSample(ChannelType.FILE, "qazqaz:123123");
		dex.queueOutputEndPoint(s1);

		BlockingQueue<Sample> f = dex.getOutputFileQueue();
		assertEquals(0, f.size());

		BlockingQueue<Sample> u = dex.getOutputUDPQueue();
		assertEquals(0, u.size());
	}
}