package test.org.jhub1.agent.xmpp.iq;

import static org.junit.Assert.assertEquals;

import org.jhub1.agent.xmpp.iq.IQJhub1Samples;
import org.junit.Test;

public class IQJhub1SamplesTest {

	@Test
	public void stanzaChildElementXMLWith2Items() {
		IQJhub1Samples gwItems = new IQJhub1Samples();
		gwItems.setItemsType("SAMPLE");
		IQJhub1Samples.Item item;
		
        item = new IQJhub1Samples.Item("uid00001");
        item.setTimestamp("0123452342345");
        item.setName("sampleNo1");
        item.setValue("33");
        item.setChannel("FILE");
        assertEquals("sampleNo1:33:0123452342345", item.toSample());
        gwItems.addItem(item);
        
        item = new IQJhub1Samples.Item("uid00002");
        item.setTimestamp("0123452342555");
        item.setName("sampleNo2");
        item.setValue("34");
        item.setChannel("FILE");
        assertEquals("sampleNo2:34:0123452342555", item.toSample());
        gwItems.addItem(item);
        
      //  System.out.print(gwItems.getChildElementXML());
		assertEquals(IQsTest.SAMPLES_VALID_2_ITEMS, gwItems.getChildElementXML());
	}
	
	@Test
	public void stanzaChildElementXMLWith2ItemsACK() {
		IQJhub1Samples gwItems = new IQJhub1Samples();
		gwItems.setItemsType("SAMPLE_ACK");
		IQJhub1Samples.Item item;
		
        item = new IQJhub1Samples.Item("uid00001");
        gwItems.addItem(item);
        
        item = new IQJhub1Samples.Item("uid00002");
        gwItems.addItem(item);
        
		assertEquals(IQsTest.SAMPLES_VALID_2_ITEMS_ACK, gwItems.getChildElementXML());
	}
}
