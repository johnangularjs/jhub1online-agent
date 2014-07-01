package test.org.jhub1.agent.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.configuration.ConfigurationException;
import org.jhub1.agent.configuration.AgentProperties;
import org.jhub1.agent.configuration.ConfigUDPIn;
import org.jhub1.agent.configuration.ConfigUDPInImpl;
import org.jhub1.agent.configuration.PropertiesImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConfigUDPInTest {
	
	private static ConfigUDPIn config;
	private static AgentProperties prop;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		try {
			prop = new PropertiesImpl();
		} catch (ConfigurationException e1) {
			e1.printStackTrace();
		}
		config = new ConfigUDPInImpl(prop);
	}

	@Test
	public void checkAccessToProperties() {
		assertEquals(8822, config.getPort());
		assertTrue(config.isEnabled());
	}
}
