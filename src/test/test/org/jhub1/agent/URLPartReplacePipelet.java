package test.org.jhub1.agent;

import static org.junit.Assert.assertEquals;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class URLPartReplacePipelet {
	
	@Test
	public void urlStringTest1() {
		String localeID	= "en_CA";
		String siteName	= "Merck-US-Site";
		String oldURL = "http://ebiz-devtest.merckgroup.com/HK/zh/20130904_173607?Generic1=kcn-5d7cb01015man-row&Generic2=http://www.millipore.com/notification/cp2/kcn-5d7cb01015man-row";
		String expectedResult = "http://ebiz-devtest.merckgroup.com/US/en/20130904_173607?Generic1=kcn-5d7cb01015man-row&Generic2=http://www.millipore.com/notification/cp2/kcn-5d7cb01015man-row";
		assertEquals(expectedResult, createRedirectURL(localeID, siteName, oldURL));
	}
	
	@Test
	public void urlStringTest2() {
		String localeID	= "en_CA";
		String siteName	= "Merck-US-Site";
		String oldURL = "http://ebiz-devtest.merckgroup.com/CO/es/20130904_173607?Generic1=kcn-5d7cb01015man-row&Generic2=http://www.millipore.com/notification/cp2/kcn-5d7cb01015man-row";
		String expectedResult = "http://ebiz-devtest.merckgroup.com/US/en/20130904_173607?Generic1=kcn-5d7cb01015man-row&Generic2=http://www.millipore.com/notification/cp2/kcn-5d7cb01015man-row";
		assertEquals(expectedResult, createRedirectURL(localeID, siteName, oldURL));
	}

	@Test
	public void urlStringTest3() {
		String localeID	= "en_CA";
		String siteName	= "Merck-US-Site";
		String oldURL = "http://ebiz-devtest.merckgroup.com/INTL/es/20130904_173607?Generic1=kcn-5d7cb01015man-row&Generic2=http://www.millipore.com/notification/cp2/kcn-5d7cb01015man-row";
		String expectedResult = "http://ebiz-devtest.merckgroup.com/US/en/20130904_173607?Generic1=kcn-5d7cb01015man-row&Generic2=http://www.millipore.com/notification/cp2/kcn-5d7cb01015man-row";
		assertEquals(expectedResult, createRedirectURL(localeID, siteName, oldURL));
	}
	
	@Test
	public void urlStringTest4() {
		String localeID	= "en_CA";
		String siteName	= "Merck-US-Site";
		String oldURL = "http://ebiz-devtest.merckgroup.com/DISTR/es/20130904_173607?Generic1=kcn-5d7cb01015man-row&Generic2=http://www.millipore.com/notification/cp2/kcn-5d7cb01015man-row";
		String expectedResult = "http://ebiz-devtest.merckgroup.com/US/en/20130904_173607?Generic1=kcn-5d7cb01015man-row&Generic2=http://www.millipore.com/notification/cp2/kcn-5d7cb01015man-row";
		assertEquals(expectedResult, createRedirectURL(localeID, siteName, oldURL));
	}
	
	@Test
	public void urlStringTest5() {
		String localeID	= "en_CA";
		String siteName	= "Merck-DISTR-Site";
		String oldURL = "http://ebiz-devtest.merckgroup.com/CO/es/20130904_173607?Generic1=kcn-5d7cb01015man-row&Generic2=http://www.millipore.com/notification/cp2/kcn-5d7cb01015man-row";
		String expectedResult = "http://ebiz-devtest.merckgroup.com/DISTR/en/20130904_173607?Generic1=kcn-5d7cb01015man-row&Generic2=http://www.millipore.com/notification/cp2/kcn-5d7cb01015man-row";
		assertEquals(expectedResult, createRedirectURL(localeID, siteName, oldURL));
	}
	
    public static String createRedirectURL(String localeID, String siteName, String oldURL) { 
    	String[] locales = localeID.split("_");
    	String[] channels = siteName.split("-");
    	
    	StringBuilder sb = new StringBuilder();
    	sb.append("/").append(channels[1]).append("/").append(locales[0]).append("/");
    	String replace = sb.toString();

    	String newURL = null;
		if(StringUtils.isNotBlank(replace)) {
			newURL = oldURL.replaceFirst("(/(INTL|DISTR|([A-Z][A-Z]))/([a-z][a-z])/)", replace);
    	}
    	
    	return newURL;
    }
	
    public static String createRedirectURLshit(String localeID, String siteName, String oldURL) { 
    	String currLocLocalization = "(/(INTL|DISTR|([A-Z][A-Z]))/([a-z][a-z])/)"; 	
    	String[] locales = localeID.split("_");
    	String[] channels = siteName.split("-");
    	
    	StringBuilder sb = new StringBuilder();
    	sb.append("/").append(channels[1]).append("/").append(locales[0]).append("/");
    	String replace = sb.toString();
    	
    	Pattern CURRENT_LOCALIZATION = Pattern.compile(currLocLocalization);
    	Matcher m = CURRENT_LOCALIZATION.matcher(oldURL);
    	String search = "";
    	while (m.find()) {
    		search = m.group(1);
    	    System.out.println(search);
    	}
    	String newURL = null;
		if(StringUtils.isNotBlank(replace) && StringUtils.isNotBlank(search)) {
			newURL = oldURL.replaceFirst("(/(INTL|DISTR|([A-Z][A-Z]))/([a-z][a-z])/)", replace);
    	}
    	
    	return newURL;
    }
    
    
}
