package test.org.jhub1.agent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.jhub1.agent.AgentSample;
import org.jhub1.agent.ChannelType;
import org.jhub1.agent.Sample;
import org.junit.Test;

import test.org.jhub1.agent.xmpp.iq.IQsTest;

public class SampleTest {

/*	@Test
	public void checkTest1() {
		String trimmedDate = "1111111";
		assertTrue(trimmedDate.matches("(([a-zA-Z]+|[0-9]+)){7,}"));
	}
	
	@Test
	public void checkTest24() {
		
		String testCaseValue = "this|is|my|vaxv xv xcv xcvx zcv xczue|for|app|method";
		
        String[] values = testCaseValue.split("\\|");
        if(values.length > 1) {
            List<String> listAttr = Arrays.asList(values);
            assertEquals(7,listAttr.size());
            Iterator <String> it = listAttr.iterator();
            while(it.hasNext()) {
            	System.out.println(it.next());
            }
        } else {
            String singleValue = testCaseValue.replace("|","");
            assertEquals("",singleValue);
        }
	}*/
	
/*	@Test
	public void checkTest2() {
		String trimmedDate = "11e1111|sadada";
		String[] values = trimmedDate.split("\\|");
		assertEquals(2,values.length);
		
		String trimmedDate2 = "11e1111|";
		String[] values2 = trimmedDate2.split("\\|");
		assertEquals(1,values2.length);
		String qq = trimmedDate2.replace("|","");
		assertEquals("11e1111", qq);
		
		String trimmedDate3 = "11e1111";
		String[] values3 = trimmedDate3.split("\\|");
		assertEquals(1,values3.length);
		
		String trimmedDate4 = "11e1111";
		String[] values4 = trimmedDate4.split("\\|");
		assertEquals(1,values4.length);	
	}*/
	
	/*	
	@Test
	public void checkTest3() {
		String trimmedDate = "1111111";
		assertFalse(trimmedDate.matches("^(?=[^\\s]*[a-zA-Z])(?=[^\\s]*[\\d])[^\\s]*$"));
	}
	
	@Test
	public void checkTest4() {
		String trimmedDate = "qqqqqqq";
		assertFalse(trimmedDate.matches("^(?=[^\\s]*[a-zA-Z])(?=[^\\s]*[\\d])[^\\s]*$"));
	}
	
	@Test
	public void checkTest5() {
		String trimmedDate = "qqqqq1";
		assertFalse(trimmedDate.matches("^(?=[^\\s]*[a-zA-Z])(?=[^\\s]*[\\d])[^\\s]*$"));
	}
	
	@Test
	public void checkTest6() {
		String trimmedDate = "qqqqq1e";
		assertTrue(trimmedDate.matches("^(?=[^\\s]*[a-zA-Z])(?=[^\\s]*[\\d])[^\\s]*$"));
	}
	*/
	
	@Test
	public void checkOverallSampleDefinition() {
		assertFalse(IQsTest.SAMPLES_VALID_2_ITEMS_IQ.matches(AgentSample.REGEX_SAMPLE_OVERALL));
	}
	

	@Test
	public void checkTimestampRegexForNumbersSequence13() {
		String trimmedDate = "1395220878183";
		assertTrue(trimmedDate.matches(AgentSample.REGEX_TIMESTAMP));
	}

	@Test
	public void checkTimestampRegexForNumbersSequence10() {
		String trimmedDate = "1395220878";
		assertTrue(trimmedDate.matches(AgentSample.REGEX_TIMESTAMP));
	}

	@Test
	public void checkTimestampRegexForNumbersSequence11() {
		String trimmedDate = "139220878332";
		assertTrue(trimmedDate.matches(AgentSample.REGEX_TIMESTAMP));
	}

	@Test
	public void checkTimestampRegexForNumbersSequence9() {
		String trimmedDate = "139522087";
		assertFalse(trimmedDate.matches(AgentSample.REGEX_TIMESTAMP));
	}

	@Test
	public void checkTimestampRegexForNumbersSequence14() {
		String trimmedDate = "13952208781834";
		assertFalse(trimmedDate.matches(AgentSample.REGEX_TIMESTAMP));
	}

	@Test
	public void checkNameRegexForValidName1() {
		String trimmedName = "M9dasdha_asd-sd";
		assertTrue(trimmedName.matches(AgentSample.REGEX_NAME));
	}

	@Test
	public void checkNameRegexForValidName2() {
		String trimmedName = "a956dasfgh55555dha_f-asd-sd";
		assertTrue(trimmedName.matches(AgentSample.REGEX_NAME));
	}

	@Test
	public void checkNameRegexForValidName3() {
		String trimmedName = "name1";
		assertTrue(trimmedName.matches(AgentSample.REGEX_NAME));
	}

	@Test
	public void checkNameRegexForInvalidName1() {
		String trimmedName = "1a956dasfgh55555dha_f-asd-sd";
		assertFalse(trimmedName.matches(AgentSample.REGEX_NAME));
	}

	@Test
	public void checkNameRegexForInvalidName2() {
		String trimmedName = "_a1a956dasfgh55555dha_f-asd-sd";
		assertFalse(trimmedName.matches(AgentSample.REGEX_NAME));
	}

	@Test
	public void checkNameRegexForInvalidName3() {
		String trimmedName = "-A_a1a956dasfgh55555dha_f-asd-sd";
		assertFalse(trimmedName.matches(AgentSample.REGEX_NAME));
	}

	@Test
	public void checkMessageValidationValid1() {
		String trimmedDate = "dasdada:adadaAas:13952208781834";
		assertTrue(trimmedDate.matches(AgentSample.REGEX_SAMPLE_OVERALL));
	}

	@Test
	public void checkMessageValidationValid2() {
		String trimmedDate = "dasdada:adadaAas:";
		assertTrue(trimmedDate.matches(AgentSample.REGEX_SAMPLE_OVERALL));
	}

	@Test
	public void checkMessageValidationValid3() {
		String trimmedDate = "dasdada:adadaAas";
		assertTrue(trimmedDate.matches(AgentSample.REGEX_SAMPLE_OVERALL));
	}

	@Test
	public void checkMessageValidationInvalid1() {
		String trimmedDate = "::";
		assertFalse(trimmedDate.matches(AgentSample.REGEX_SAMPLE_OVERALL));
	}

	@Test
	public void checkMessageValidationInvalid2() {
		String trimmedDate = ":";
		assertFalse(trimmedDate.matches(AgentSample.REGEX_SAMPLE_OVERALL));
	}

	@Test
	public void checkMessageValidationInvalid3() {
		String trimmedDate = "sfsf:sfsaf:asfsaf:ASfsa";
		assertFalse(trimmedDate.matches(AgentSample.REGEX_SAMPLE_OVERALL));
	}

	@Test
	public void checkMessageValidationInvalid4() {
		String trimmedDate = ":::";
		assertFalse(trimmedDate.matches(AgentSample.REGEX_SAMPLE_OVERALL));
	}

	@Test
	public void checkMessageValidationInvalid5() {
		String trimmedDate = ":adadaAas:";
		assertFalse(trimmedDate.matches(AgentSample.REGEX_SAMPLE_OVERALL));
	}

	@Test
	public void checkMessageValidationInvalid6() {
		String trimmedDate = "";
		assertFalse(trimmedDate.matches(AgentSample.REGEX_SAMPLE_OVERALL));
	}

	@Test
	public void checkValidSampleInitDate13CharsFILE() {
		String sampleString = "name1:value:1395220878183";
		Sample sample = new AgentSample(ChannelType.FILE, sampleString);
		assertEquals(ChannelType.FILE, sample.getChannel());
		assertTrue(sample.isValid());
		assertEquals("name1", sample.getName());
		assertEquals("value", sample.getValue());
		assertEquals(1395220878183L, sample.getDate().getTime());
	}

	@Test
	public void checkValidSampleInitDate13CharsUDP() {
		String sampleString = "name1:value:1395220878183";
		Sample sample = new AgentSample(ChannelType.UDP, sampleString);
		assertEquals(ChannelType.UDP, sample.getChannel());
		assertTrue(sample.isValid());
		assertEquals("name1", sample.getName());
		assertEquals("value", sample.getValue());
		assertEquals(1395220878183L, sample.getDate().getTime());
	}

	@Test
	public void checkValidSampleInitDate10Chars() {
		String sampleString = "name1:value:1395220878";
		Sample sample = new AgentSample(ChannelType.FILE, sampleString);
		assertEquals(ChannelType.FILE, sample.getChannel());
		assertTrue(sample.isValid());
		assertEquals("name1", sample.getName());
		assertEquals("value", sample.getValue());
		assertEquals(1395220878L, sample.getDate().getTime());
	}

	@Test
	public void checkValidSampleUID() {
		String sampleString = "name1:value:1395220878";
		Sample sample = new AgentSample(ChannelType.FILE, sampleString);
		assertEquals(ChannelType.FILE, sample.getChannel());
		assertTrue(sample.isValid());
		assertEquals(sample.getUID(), sample.getUID());
	}
	
	@Test
	public void checkInvalidSampleNoName() {
		String sampleString = ":value:1395220878000";
		Sample sample = new AgentSample(ChannelType.FILE, sampleString);
		assertFalse(sample.isValid());
	}

	@Test
	public void checkValidSampleNoDate1() {
		String sampleString = "name1:value";
		Sample sample = new AgentSample(ChannelType.FILE, sampleString);
		Date date = new Date();
		assertEquals(ChannelType.FILE, sample.getChannel());
		assertTrue(sample.isValid());
		assertEquals("name1", sample.getName());
		assertEquals("value", sample.getValue());
		assertEquals(trimm4(date.getTime()), trimm4(sample.getDate().getTime()));
	}

	@Test
	public void checkValidSampleNoDate2() {
		String sampleString = "name1:value:";
		Sample sample = new AgentSample(ChannelType.FILE, sampleString);
		Date date = new Date();
		assertEquals(ChannelType.FILE, sample.getChannel());
		assertTrue(sample.isValid());
		assertEquals("name1", sample.getName());
		assertEquals("value", sample.getValue());
		assertEquals(trimm4(date.getTime()), trimm4(sample.getDate().getTime()));
	}

	@Test
	public void checkInalidSampleInitDate14Chars() {
		String sampleString = "name1:value:13952208781836";
		Sample sample = new AgentSample(ChannelType.FILE, sampleString);
		Date date = new Date();
		assertEquals(ChannelType.FILE, sample.getChannel());
		assertTrue(sample.isValid());
		assertEquals("name1", sample.getName());
		assertEquals("value", sample.getValue());
		assertEquals(trimm4(date.getTime()), trimm4(sample.getDate().getTime()));
	}

	@Test
	public void checkInalidSampleInitDate9Chars() {
		String sampleString = "name1:value:139522089";
		Sample sample = new AgentSample(ChannelType.UDP, sampleString);
		Date date = new Date();
		assertEquals(ChannelType.UDP, sample.getChannel());
		assertTrue(sample.isValid());
		assertEquals("name1", sample.getName());
		assertEquals("value", sample.getValue());
		assertEquals(trimm4(date.getTime()), trimm4(sample.getDate().getTime()));
	}

	@Test
	public void checkInalidSampleInitDateWithNotAllowedChars() {
		String sampleString = "name1:value:1_395220878W$";
		Sample sample = new AgentSample(ChannelType.FILE, sampleString);
		Date date = new Date();
		assertEquals(ChannelType.FILE, sample.getChannel());
		assertTrue(sample.isValid());
		assertEquals("name1", sample.getName());
		assertEquals("value", sample.getValue());
		assertEquals(trimm4(date.getTime()), trimm4(sample.getDate().getTime()));
	}

	@Test
	public void checkInalidSampleInitNameWithNotAllowedChars01() {
		String sampleString = "1name_:value:1395220878183";
		Sample sample = new AgentSample(ChannelType.FILE, sampleString);
		assertEquals(ChannelType.FILE, sample.getChannel());
		assertTrue(sample.isValid());
		assertEquals("unknown", sample.getName());
		assertEquals("value", sample.getValue());
		assertEquals(1395220878183L, sample.getDate().getTime());
	}

	@Test
	public void checkInalidSampleInitNameWithNotAllowedChars02() {
		String sampleString = "123455:value:1395220878183";
		Sample sample = new AgentSample(ChannelType.FILE, sampleString);
		assertEquals(ChannelType.FILE, sample.getChannel());
		assertTrue(sample.isValid());
		assertEquals("unknown", sample.getName());
		assertEquals("value", sample.getValue());
		assertEquals(1395220878183L, sample.getDate().getTime());
	}

	@Test
	public void checkInalidSampleInitNameWithNotAllowedChars03() {
		String sampleString = "df_^%*cx:value:1395220878183";
		Sample sample = new AgentSample(ChannelType.FILE, sampleString);
		assertEquals(ChannelType.FILE, sample.getChannel());
		assertTrue(sample.isValid());
		assertEquals("unknown", sample.getName());
		assertEquals("value", sample.getValue());
		assertEquals(1395220878183L, sample.getDate().getTime());
	}

	@Test
	public void checkValidSample01() {
		String sampleString = "value:1395220878";
		Sample sample = new AgentSample(ChannelType.FILE, sampleString);
		Date date = new Date();
		assertEquals(ChannelType.FILE, sample.getChannel());
		assertTrue(sample.isValid());
		assertEquals("value", sample.getName());
		assertEquals("1395220878", sample.getValue());
		assertEquals(trimm4(date.getTime()), trimm4(sample.getDate().getTime()));
	}

	@Test
	public void checkInalidSample02() {
		String sampleString = "::1395220878";
		Sample sample = new AgentSample(ChannelType.FILE, sampleString);
		assertFalse(sample.isValid());
	}

	@Test
	public void checkInalidSample03() {
		String sampleString = ":::";
		Sample sample = new AgentSample(ChannelType.FILE, sampleString);
		assertFalse(sample.isValid());
	}

	private String trimm4(long time) {
		String timeString = "" + time;
		return (String) timeString.subSequence(0, 9);
	}

}