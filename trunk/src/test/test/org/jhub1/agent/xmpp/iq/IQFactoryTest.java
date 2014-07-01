package test.org.jhub1.agent.xmpp.iq;

import static org.junit.Assert.assertEquals;

import org.jhub1.agent.xmpp.iq.IQFactory;
import org.jhub1.agent.xmpp.iq.IQJhub1Atomized;
import org.jhub1.agent.xmpp.iq.IQJhub1Samples;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.IQ.Type;
import org.junit.Test;

public class IQFactoryTest {
	
	@Test
	public void fromXMLStanzaTo_IQJhub1Samples_Element() throws Exception {
		
		IQ iq = IQFactory.getIQFromXML(IQsTest.SAMPLES_VALID_2_ITEMS_IQ);
		
		assertEquals("zyak/admin", iq.getFrom());
		assertEquals("ayak/admin", iq.getTo());
		assertEquals(Type.RESULT, iq.getType());
		IQJhub1Samples samples = (IQJhub1Samples) iq;
		assertEquals("SAMPLE", samples.getItemsType());
		// TODO add iterator through items - to make test better
	}
	
	@Test
	public void fromXMLStanzaTo_IQJhub1Atomized_Element() throws Exception {
		
		IQ iq = IQFactory.getIQFromXML(IQsTest.ATOMIZED_VALID_IQ);
		
		assertEquals("zqyak/admin", iq.getFrom());
		assertEquals("sayak/admin", iq.getTo());
		assertEquals(Type.SET, iq.getType());
		IQJhub1Atomized atom = (IQJhub1Atomized) iq;
		assertEquals("qazwsx", atom.getItem().getHash());
		// TODO add the rest of the properties - to make test better
	}
}
