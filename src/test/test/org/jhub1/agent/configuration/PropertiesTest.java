package test.org.jhub1.agent.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.configuration.ConfigurationException;
import org.jhub1.agent.configuration.AgentProperties;
import org.jhub1.agent.configuration.PropertiesImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class PropertiesTest  {
	/**
	 * The purpose of the tests is to make sure that
	 * all property values are actually being read properly
	 * and are accessible via relevant method.
	 * 
	 * Update the test when the default properties in file has changed.
	 * Change of any of the properties should make the test fail.
	 */
	private static AgentProperties config;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		try {
			config = new PropertiesImpl();
		} catch (ConfigurationException e1) {
			System.exit(0);
		}
	}
	
	@Test
	public void checkIsOutputChannelUDPDefaultFalse() {
		assertFalse(config.isOutputChannelUDPDefault());
	}
	
	@Test
	public void checkIsDefaultOutputChannelAllowedFalse() {
		assertFalse(config.isDefaultOutputChannelAllowed());
	}
	
	@Test
	public void checkIsOutputChannelFileDefaultFalse() {
		assertFalse(config.isOutputChannelFileDefault());
	}
	
	@Test
	public void checkFileInputDirectory() {
		assertEquals("file/input/", config.getFileInputDirectory());
	}

	@Test
	public void checkFileOutputDirectory() {
		assertEquals("file/output/", config.getFileOutputDirectory());
	}
	
	@Test
	public void checkUDPInPort() {
		assertEquals(8822, config.getUDPInPort());
	}

	@Test
	public void checkUDPInSocketTimeout() {
		assertEquals(2000, config.getUDPInSocketTimeout());
	}

	@Test
	public void checkUDPInActive() {
		assertTrue(config.isUDPInActive());
	}
	
	@Test
	public void checkFileInActive() {
		assertTrue(config.isFileInActive());
	}

	@Test
	public void checkFileOutActive() {
		assertTrue(config.isFileOutActive());
	}

	@Test
	public void checkJID() {
		assertEquals("GeneratedUsername@jhub1online.org", config.getJID());
	}

	@Test
	public void checkJServers() {
		assertEquals("server.comm.unicate.me|10.10.10.11|11.10.10.11", config.getJServers());
	}

	@Test
	public void checkJPort() {
		assertEquals(443, config.getJPort());
	}
	
	@Test
	public void checkJPassword() {
		assertEquals("GeneratedPassword", config.getJPassword());
	}

	@Test
	public void checkJResourceID() {
		assertEquals("JHUB1Agent", config.getJResourceID());
	}
	
	@Test
	public void checkUDPOutPort() {
		assertEquals(8899, config.getUDPOutPort());
	}
    
	@Test
	public void checkUDPOutIP() {
		assertEquals("127.0.0.1", config.getUDPOutIP());
	}
	
	@Test
	public void checkUDPOutActive() {
		assertTrue(config.isUDPOutActive());
	}

	@Test
	public void checkFileInputPullFrequency() {
		assertEquals(600, config.getFileInputPullFrequency());
	}
	
	@Test
	public void checkFileOutputPushFrequency() {
		assertEquals(600, config.getFileOutputPushFrequency());
	}

	@Test
	public void checkXMPPLoopDelay() {
		assertEquals(50, config.getXMPPLoopDelay());
	}

	@Test
	public void checkXMPPSamplesBucketSize() {
		assertEquals(25, config.getXMPPSamplesBucketSize());
	}

	@Test
	public void checkInputSamplesQueueSize() {
		assertEquals(1024, config.getInputSamplesQueueSize());
	}
	
	@Test
	public void checkOutputSamplesQueueSize() {
		assertEquals(1024, config.getOutputSamplesQueueSize());
	}

	@Test
	public void checkThreadFileInEnabled() {
		assertTrue(config.isThreadFileInEnabled());
	}
	
	@Test
	public void checkThreadFileOutEnabled() {
		assertTrue(config.isThreadFileOutEnabled());
	}

	@Test
	public void checkThreadUDPInEnabled() {
		assertTrue(config.isThreadUDPInEnabled());
	}
	
	@Test
	public void checkThreadUDPOutEnabled() {
		assertTrue(config.isThreadUDPOutEnabled());
	}
	
	@Test
	public void checkThreadXMPPEnabled() {
		assertTrue(config.isThreadXMPPEnabled());
	}
	
	@Test
	public void checkTestQueueLoopbackEnabled() {
		assertFalse(config.isThreadTestQLoopbackEnabled());
	}
	
	@Test
	public void checkOutputChannelUDPDefault() {
		assertFalse(config.isOutputChannelUDPDefault());
	}
	
	@Test
	public void checkOutputChannelFileDefault() {
		assertFalse(config.isOutputChannelFileDefault());
	}
	
	@Test
	public void checkDefaultOutputChannelAllowed() {
		assertFalse(config.isDefaultOutputChannelAllowed());
	}
		
}
