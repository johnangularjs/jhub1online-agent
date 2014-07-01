package test.org.jhub1.agent.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.configuration.ConfigurationException;
import org.jhub1.agent.configuration.AgentProperties;
import org.jhub1.agent.configuration.ConfigUDPOut;
import org.jhub1.agent.configuration.ConfigUDPOutImpl;
import org.jhub1.agent.configuration.PropertiesImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConfigUDPOutTest {

	private static ConfigUDPOut config;
	private static AgentProperties prop;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		try {
			prop = new PropertiesImpl();
		} catch (ConfigurationException e1) {
			e1.printStackTrace();
		}
		config = new ConfigUDPOutImpl(prop);
	}

	@Test
	public void checkAccessToProperties() {
		assertEquals(8899, config.getPort());
		assertEquals("127.0.0.1", config.getIP());
		assertTrue(config.isEnabled());
	}

}
