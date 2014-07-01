package test.org.jhub1.agent.xmpp.iq;

import static org.junit.Assert.assertEquals;

import org.jhub1.agent.xmpp.iq.IQFactory;
import org.jhub1.agent.xmpp.iq.IQJhub1Atomized;
import org.jhub1.agent.xmpp.iq.IQJhub1AtomizedProvider;
import org.junit.Test;
import org.xmlpull.v1.XmlPullParser;

public class IQJhub1AtomizedProviderTest {

	
	@Test
	public void stanzaProviderGetValidNamespace() throws Exception {
		XmlPullParser parser;
		parser = IQFactory.getParser(IQsTest.ATOMIZED_VALID_GET_NAMESPACE, "query");
		IQJhub1AtomizedProvider prov = new IQJhub1AtomizedProvider();
		IQJhub1Atomized atomized = (IQJhub1Atomized) prov.parseIQ(parser);
		assertEquals(IQsTest.ATOMIZED_VALID_GET_NAMESPACE, atomized.getChildElementXML());
	}

	@Test
	public void stanzaProviderGetValidNamespaceResult() throws Exception {
		XmlPullParser parser;
		parser = IQFactory.getParser(IQsTest.ATOMIZED_VALID_GET_RESULT_NAMESPACE, "query");
		IQJhub1AtomizedProvider prov = new IQJhub1AtomizedProvider();
		IQJhub1Atomized atomized = (IQJhub1Atomized) prov.parseIQ(parser);
		assertEquals(IQsTest.ATOMIZED_VALID_GET_RESULT_NAMESPACE, atomized.getChildElementXML());
	}
	
	@Test
	public void stanzaProviderSetValidNamespace() throws Exception {
		XmlPullParser parser;
		parser = IQFactory.getParser(IQsTest.ATOMIZED_VALID_SET_NAMESPACE, "query");
		IQJhub1AtomizedProvider prov = new IQJhub1AtomizedProvider();
		IQJhub1Atomized atomized = (IQJhub1Atomized) prov.parseIQ(parser);
		assertEquals(IQsTest.ATOMIZED_VALID_SET_NAMESPACE, atomized.getChildElementXML());
	}
	
	@Test
	public void stanzaProviderSetValidNamespaceResult() throws Exception {
		XmlPullParser parser;
		parser = IQFactory.getParser(IQsTest.ATOMIZED_VALID_SET_RESULT_NAMESPACE, "query");
		IQJhub1AtomizedProvider prov = new IQJhub1AtomizedProvider();
		IQJhub1Atomized atomized = (IQJhub1Atomized) prov.parseIQ(parser);
		assertEquals(IQsTest.ATOMIZED_VALID_SET_RESULT_NAMESPACE, atomized.getChildElementXML());
	}
	
}
