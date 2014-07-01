package test.org.jhub1.agent.xmpp.iq;

import static org.junit.Assert.assertEquals;

import org.jhub1.agent.xmpp.iq.IQFactory;
import org.jhub1.agent.xmpp.iq.IQJhub1Samples;
import org.jhub1.agent.xmpp.iq.IQJhub1SamplesProvider;
import org.junit.Test;
import org.xmlpull.v1.XmlPullParser;

public class IQJhub1SamplesProviderTest {

	@Test
	public void stanzaProviderSampleACK() throws Exception {
		XmlPullParser parser;
		parser = IQFactory.getParser(IQsTest.SAMPLES_VALID_2_ITEMS_ACK, "query");
		IQJhub1SamplesProvider prov = new IQJhub1SamplesProvider();
		IQJhub1Samples samples = (IQJhub1Samples) prov.parseIQ(parser);
		assertEquals(IQsTest.SAMPLES_VALID_2_ITEMS_ACK, samples.getChildElementXML());
	}

	@Test
	public void stanzaProviderSample() throws Exception {
		XmlPullParser parser;
		parser = IQFactory.getParser(IQsTest.SAMPLES_VALID_2_ITEMS, "query");
		IQJhub1SamplesProvider prov = new IQJhub1SamplesProvider();
		IQJhub1Samples samples = (IQJhub1Samples) prov.parseIQ(parser);
		assertEquals(IQsTest.SAMPLES_VALID_2_ITEMS, samples.getChildElementXML());
	}
	
	@Test
	public void stanzaProviderSampleNoChannel() throws Exception {
		XmlPullParser parser;
		parser = IQFactory.getParser(IQsTest.SAMPLES_VALID_2_ITEMS_NO_CHANNEL, "query");
		IQJhub1SamplesProvider prov = new IQJhub1SamplesProvider();
		IQJhub1Samples samples = (IQJhub1Samples) prov.parseIQ(parser);
		assertEquals(IQsTest.SAMPLES_VALID_2_ITEMS_NO_CHANNEL, samples.getChildElementXML());
	}

	@Test
	public void stanzaProviderSampleFullIQ() throws Exception {
		XmlPullParser parser;
		parser = IQFactory.getParser(IQsTest.SAMPLES_VALID_2_ITEMS_IQ, "iq");
		IQJhub1SamplesProvider prov = new IQJhub1SamplesProvider();
		IQJhub1Samples samples = (IQJhub1Samples) prov.parseIQ(parser);
		System.out.println(samples.toXML());
		assertEquals(IQsTest.SAMPLES_VALID_2_ITEMS, samples.getChildElementXML());
	}
	
}
