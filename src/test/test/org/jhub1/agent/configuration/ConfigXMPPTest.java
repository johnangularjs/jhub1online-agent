package test.org.jhub1.agent.configuration;
import static org.junit.Assert.assertEquals;

import org.apache.commons.configuration.ConfigurationException;
import org.jhub1.agent.configuration.AgentProperties;
import org.jhub1.agent.configuration.ConfigXMPP;
import org.jhub1.agent.configuration.ConfigXMPPImpl;
import org.jhub1.agent.configuration.PropertiesImpl;
import org.jhub1.agent.thread.data.DataExchangeImpl;
import org.junit.BeforeClass;
import org.junit.Test;


public class ConfigXMPPTest {
	
		private static ConfigXMPP config;
		
		@BeforeClass
		public static void setUpBeforeClass() {
			AgentProperties prop = null;
			try {
				prop = new PropertiesImpl();
			} catch (ConfigurationException e1) {
				e1.printStackTrace();
			}
			config = new ConfigXMPPImpl(prop, new DataExchangeImpl(prop));
		}

	    @Test
	    public void checkAccessToProperties()
	    {
	        assertEquals("GeneratedPassword", config.getPassword());
	        assertEquals(443, config.getPort());
	        assertEquals("JHUB1Agent", config.getResourcePrefix());
	        assertEquals("jhub1online.org", config.getDomain());
	        assertEquals("GeneratedUsername", config.getNick());
	    }
	    

	    @Test
	    public void checkSequentialAccessToServers()
	    {
	        assertEquals("server.comm.unicate.me", config.getServer());
	        assertEquals("server.comm.unicate.me", config.getServer());
	        assertEquals("10.10.10.11", config.getAltServer());
	        assertEquals("10.10.10.11", config.getServer());
	        assertEquals("10.10.10.11", config.getServer());
	        assertEquals("10.10.10.11", config.getServer());
	        assertEquals("11.10.10.11", config.getAltServer());
	        assertEquals("11.10.10.11", config.getServer());
	        assertEquals("server.comm.unicate.me", config.getAltServer());
	        assertEquals("server.comm.unicate.me", config.getServer());
	    }
}
