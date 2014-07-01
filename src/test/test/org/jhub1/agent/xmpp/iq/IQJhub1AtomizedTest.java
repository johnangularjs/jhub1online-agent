package test.org.jhub1.agent.xmpp.iq;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.jhub1.agent.randoms.Randoms;
import org.jhub1.agent.statistics.Registry;
import org.jhub1.agent.statistics.XMLAdapter;
import org.jhub1.agent.xmpp.iq.IQFactory;
import org.jhub1.agent.xmpp.iq.IQJhub1Atomized;
import org.jhub1.agent.xmpp.iq.IQJhub1AtomizedProvider;
import org.jhub1.agent.xmpp.iq.IQs;
import org.jhub1.system.stringatomizer.Base64Atomizer;
import org.joda.time.DateTime;
import org.junit.Test;
import org.xmlpull.v1.XmlPullParser;

public class IQJhub1AtomizedTest {

	/*
	 * @Test public void stanzaChildElementXMLGETForNamespace() {
	 * IQJhub1Atomized gwItems = new IQJhub1Atomized();
	 * gwItems.setResource(IQs.ATOMIZED_NAMESPACE_STATS);
	 * assertEquals(IQsTest.ATOMIZED_VALID_GET_NAMESPACE,
	 * gwItems.getChildElementXML()); }
	 */

	@Test
	public void stanzaChildElementXMLRESULT() {
		IQJhub1Atomized gwItems = new IQJhub1Atomized();
		IQJhub1Atomized.Item item;

		item = new IQJhub1Atomized.Item("34234");
		item.setFormat("json");
		item.setHash("hash");
		item.setPayload("5cGU9InRleH");
		item.setResource(IQs.ATOMIZED_NAMESPACE_STATS);
		gwItems.setItem(item);

		assertEquals(IQsTest.ATOMIZED_VALID_GET_RESULT_NAMESPACE,
				gwItems.getChildElementXML());
	}

	@Test
	public void stanzaResponseWithErrorAndStatus() {
		IQJhub1Atomized gwItems = new IQJhub1Atomized();
		IQJhub1Atomized.Item item;

		item = new IQJhub1Atomized.Item("34234");
		item.setStatus("OK");
		item.setErrorCode("000");
		item.setResource(IQs.ATOMIZED_NAMESPACE_CONFIG);
		gwItems.setItem(item);

		assertEquals(IQsTest.ATOMIZED_VALID_SET_RESULT_NAMESPACE,
				gwItems.getChildElementXML());
	}

	@Test
	public void stanzaFoldingStatisticsXML() throws Exception {
		// Populating stats:
		Registry.getInstance().reset();
		Registry.getInstance().increaseEventCounter(Test.class,
				"testsavingEventCounter");
		Registry.getInstance().increaseEventCounter(Test.class,
				"testsavingEventCounter");
		Registry.getInstance().increaseEventCounter(Test.class,
				"testsavingEventCounter");
		Registry.getInstance().increaseEventCounter(Test.class,
				"testsavingEventCounter");
		DateTime date = new DateTime();
		Registry.getInstance().setValue(Test.class, "testing_value_name",
				new String("testing_value"));
		Registry.getInstance().setValue(Test.class, "testing_datetime", date);

		// Serialising stats to XML:
		String statsXML = Registry.getInstance().toFormatedOutput(
				new XMLAdapter());
		String hash = Randoms.getInstance().sha1(statsXML);
		String payload = Base64Atomizer.encodeBase64(statsXML);

		String expectedStatsXML = "<registry><statistics><items><item name='Test/testsavingEventCounter'>4</item><item name='Test/testing_datetime'>"
				+ date.getMillis()
				+ "</item><item name='Test/testing_value_name'>testing_value</item></items></statistics></registry>";
		assertEquals(expectedStatsXML, statsXML);

		// Building IQ
		IQJhub1Atomized gwItems = new IQJhub1Atomized();

		IQJhub1Atomized.Item item;

		item = new IQJhub1Atomized.Item("34234");
		item.setFormat("XML");
		item.setHash(hash);
		item.setPayload(payload);
		item.setResource(IQs.ATOMIZED_NAMESPACE_STATS);
		gwItems.setItem(item);

		System.out.println("BB");
		System.out.println(gwItems.getChildElementXML());
		System.out.println("QQ");

		// Passing IQ to provider
		XmlPullParser parser;
		parser = IQFactory.getParser(gwItems.getChildElementXML(), "query");
		System.out.println(parser.getColumnNumber());
		IQJhub1AtomizedProvider prov = new IQJhub1AtomizedProvider();
		System.out.println( prov);
		IQJhub1Atomized atomized = (IQJhub1Atomized) prov.parseIQ(parser);
		System.out.println(atomized);
		// Retrieving data from IQ
		assertEquals(IQs.ATOMIZED_NAMESPACE_STATS, atomized.getItem()
				.getResource());
		IQJhub1Atomized.Item inItem = atomized.getItem();
		assertEquals(hash, inItem.getHash());
		assertEquals(payload, inItem.getPayload());
		assertEquals("XML", inItem.getFormat());
		assertEquals("34234", inItem.getId());
		// assertEquals(IQsTest.ATOMIZED_VALID_SET_RESULT_NAMESPACE,
		// gwItems.getChildElementXML());
	}

}
