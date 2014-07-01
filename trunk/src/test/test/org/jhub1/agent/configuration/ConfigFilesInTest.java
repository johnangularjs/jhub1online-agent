package test.org.jhub1.agent.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.configuration.ConfigurationException;
import org.jhub1.agent.configuration.ConfigFilesIn;
import org.jhub1.agent.configuration.ConfigFilesInImpl;
import org.jhub1.agent.configuration.AgentProperties;
import org.jhub1.agent.configuration.PropertiesImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConfigFilesInTest {
	
	private static ConfigFilesIn config;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		AgentProperties prop = null;
		try {
			prop = new PropertiesImpl();
		} catch (ConfigurationException e1) {
			e1.printStackTrace();
		}
		config = new ConfigFilesInImpl(prop);
	}
	
	@Test
	public void checkAccessToProperties() {
		assertEquals("file/input/", config.getPath());
		assertEquals(600, config.getPullFrequency());
		assertTrue(config.isEnabled());
	}
	
}
