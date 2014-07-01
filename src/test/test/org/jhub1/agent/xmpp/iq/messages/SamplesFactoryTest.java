package test.org.jhub1.agent.xmpp.iq.messages;

import static org.junit.Assert.assertEquals;

import org.jhub1.agent.Sample;
import org.jhub1.agent.xmpp.iq.IQJhub1Samples.Item;
import org.jhub1.agent.xmpp.iq.messages.SamplesFactory;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
public class SamplesFactoryTest {
	
	@Test
	public void checkUIDHandlingWhenPresent() {
		Item item = new Item("uniqueIdOfItem");
		item.setChannel("FILE");
		item.setName("sensor");
		item.setTimestamp("3353466456455");
		item.setValue("22");
		Sample sample = SamplesFactory.makeFromItem(item);
		assertEquals("uniqueIdOfItem", sample.getUID());
	}
	
	@Test
	public void checkUIDHandlingWhenNotPresent() {
		Item item = new Item(null);
		item.setChannel("FILE");
		item.setName("sensor");
		item.setTimestamp("3353466456455");
		item.setValue("22");
		Sample sample = SamplesFactory.makeFromItem(item);
		assertThat("uniqueIdOfItem", is(not(sample.getUID())));
	}
}
