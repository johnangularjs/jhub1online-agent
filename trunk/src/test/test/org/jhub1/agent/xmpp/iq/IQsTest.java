package test.org.jhub1.agent.xmpp.iq;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jhub1.agent.xmpp.iq.IQs;
import org.junit.Test;

public class IQsTest {
	
	public static final String ATOMIZED_VALID_GET_NAMESPACE = "<query xmlns='jhub1:agent:atomized'><resource ns='jhub1:agent:resource:stats'/></query>";
	public static final String ATOMIZED_VALID_GET_RESULT_NAMESPACE = "<query xmlns='jhub1:agent:atomized'><resource ns='jhub1:agent:resource:stats'><item id='34234' format='json' hash='hash'>5cGU9InRleH</item></resource></query>";
	public static final String ATOMIZED_VALID_SET_NAMESPACE = "<query xmlns='jhub1:agent:atomized'><resource ns='jhub1:agent:resource:config'><item id='34234' format='json' hash='hash'>5cGU9InRleH</item></resource></query>";
	public static final String ATOMIZED_VALID_SET_RESULT_NAMESPACE = "<query xmlns='jhub1:agent:atomized'><resource ns='jhub1:agent:resource:config'><item id='34234' status='OK' err_code='000'/></resource></query>";
	
	public static final String SAMPLES_VALID_2_ITEMS = "<query xmlns='jhub1:agent:sample'><items type='SAMPLE'><item uid='uid00001' name='sampleNo1' timestamp='0123452342345' channel='FILE'>33</item><item uid='uid00002' name='sampleNo2' timestamp='0123452342555' channel='FILE'>34</item></items></query>";
	public static final String SAMPLES_VALID_2_ITEMS_NO_CHANNEL = "<query xmlns='jhub1:agent:sample'><items type='SAMPLE'><item uid='uid00001' name='sampleNo1' timestamp='0123452342345'>33</item><item uid='uid00002' name='sampleNo2' timestamp='0123452342555'>34</item></items></query>";
	public static final String SAMPLES_VALID_2_ITEMS_ACK = "<query xmlns='jhub1:agent:sample'><items type='SAMPLE_ACK'><item uid='uid00001'/><item uid='uid00002'/></items></query>";

	public static final String SAMPLES_VALID_2_ITEMS_IQ = "<iq type='result' to='ayak/admin' from='zyak/admin' id='1001'><query xmlns='jhub1:agent:sample'><items type='SAMPLE'><item uid='uid00001' name='sampleNo1' timestamp='0123452342345' channel='FILE'>33</item><item uid='uid00002' name='sampleNo2' timestamp='0123452342555' channel='FILE'>34</item></items></query></iq>";
	public static final String ATOMIZED_VALID_IQ = "<iq type='set' to='sayak/admin' from='zqyak/admin' id='1001'><query xmlns='jhub1:agent:atomized'><resource xmlns='jhub1:agent:resource:config' id='34234' format='xml' hash='qazwsx'>CiAgICA8c2NyaXB0IHR5cGU9InRleHQvamF2YXNjcmlwdCIgc3JjPSJkYXRhOnRleHQvamF2YXNjcml</resource></query></iq>";

	@Test
	public void checkIQRegexValidation() {
		assertTrue(SAMPLES_VALID_2_ITEMS_IQ.matches(IQs.ACCEPTED_IQ_REGEX));
	}
		
	@Test
	public void checkEnumReturningValues() {
		assertEquals("jhub1:agent:atomized", IQs.MAIN_NAMESPACE_ATOMIZED);
		assertEquals("jhub1:agent:sample", IQs.MAIN_NAMESPACE_SAMPLES);
		assertEquals("query", IQs.ELEMENT);
	}

	@Test
	public void checkXXEnumReturningValues() {
		// <query xmlns='jhub1:agent:sample'>
		String pattern = "xmlns='([0-9a-zA-Z:]*)";
		Matcher matcher = Pattern.compile(pattern).matcher(
				SAMPLES_VALID_2_ITEMS_IQ);
		if (matcher.find()) {
			assertEquals("jhub1:agent:sample", matcher.group(1));
		} else {
			fail("The name space doesn't match");
		}
	}
	
}
