package test.org.jhub1.agent;


import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.junit.Test;

public class QueryStringOrderTest {
	
	@Test
	public void queryStringTest1() {
		String test = "Generic1=kcn-5d7cb01015man-row&Generic2=http%3A%2F%2Fwww.millipore.com%2Fnotification%2Fcp2%2Fkcn-5d7cb01015man-row&PageletEntryPointID=20130904_173607";
		String expectedResult = "PageletEntryPointID=20130904_173607&Generic1=kcn-5d7cb01015man-row&Generic2=http%3A%2F%2Fwww.millipore.com%2Fnotification%2Fcp2%2Fkcn-5d7cb01015man-row";
		assertEquals(expectedResult, QueryStringOrderTest.orderQueryString(test));
	}
	
	@Test
	public void queryStringTest2() {
		String test = "Generic1=kcn-5d7cb01015man-row&PageletEntryPointID=20130904_173607&Generic2=http%3A%2F%2Fwww.millipore.com%2Fnotification%2Fcp2%2Fkcn-5d7cb01015man-row";
		String expectedResult = "PageletEntryPointID=20130904_173607&Generic1=kcn-5d7cb01015man-row&Generic2=http%3A%2F%2Fwww.millipore.com%2Fnotification%2Fcp2%2Fkcn-5d7cb01015man-row";
		assertEquals(expectedResult, QueryStringOrderTest.orderQueryString(test));
	}

	@Test
	public void queryStringTest3() {
		String test = "Generic1=kcn-5d7cb01015man-row";
		String expectedResult = "Generic1=kcn-5d7cb01015man-row";
		assertEquals(expectedResult, QueryStringOrderTest.orderQueryString(test));
	}
	
	@Test
	public void queryStringTest4() {
		String test = "PageletEntryPointID=20130904_173607";
		String expectedResult = "PageletEntryPointID=20130904_173607";
		assertEquals(expectedResult, QueryStringOrderTest.orderQueryString(test));
	}
	
    public static String orderQueryStringOLD(String query) { 
        StringBuilder sb = new StringBuilder();
        String[] params = query.split("&"); 
        String epSep = params.length > 1 ? "&" : "";
        String sep = "";
        for (String param : params) {
            if(param.contains("PageletEntryPointID=")) {
                sb.insert(0, param + epSep);
            } else {
                sb.append(sep);
                sep = "&";
                sb.append(param);
            }     
        }   
        return sb.toString();  
    }
    
    public static String orderQueryString(String query)
    {
    	String param = "PageletEntryPointID";
        StringBuilder orderedQuery = new StringBuilder();
        Map<String, String> map = extractParameters(query);
        String pepid = map.remove(param);
        
        orderedQuery.append(param);
        if(pepid != null){
            orderedQuery.append("=");
            orderedQuery.append(pepid);
        }
        for (Map.Entry<String, String> e : map.entrySet())
        {
            orderedQuery.append("&");
            orderedQuery.append(e.getKey());
            if (e.getValue() != null)
            {
                orderedQuery.append("=");
                orderedQuery.append(e.getValue());
            }
        }
        return orderedQuery.toString();
    }
    
    
    public static Map<String, String> extractParameters(String query)
    {
        if ((query == null) || (query.isEmpty()))
        {
            return Collections.emptyMap();
        }

        Map extractedParameters = new HashMap();
        StringTokenizer st = new StringTokenizer(query, "&");
        while(st.hasMoreTokens())
        {
            String token = st.nextToken();

            String[] splitted = token.split("=");
            if (splitted.length == 1)
            {
                extractedParameters.put(splitted[0], null);
            }
            else
            {
                extractedParameters.put(splitted[0], splitted[1]);
            }
        }
        return extractedParameters;
    }
}
