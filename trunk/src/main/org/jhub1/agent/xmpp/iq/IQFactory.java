/**
 * $Revision$
 * $Date$
 * 
 * Copyright 2013 SoftCognito.org.
 *
 * All rights reserved. Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jhub1.agent.xmpp.iq;

import java.io.IOException;
import java.io.StringReader;

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.IQ.Type;
import org.xmlpull.mxp1.MXParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class IQFactory {
	public static IQ getIQFromXML(String xml) throws Exception {
		IQ iq = null;
		XmlPullParser parserIQ = null;
		parserIQ = getParser(xml, "iq");

		XmlPullParser parser = null;
		parser = getParser(xml, "query");

		if (parser.getNamespace().equalsIgnoreCase(IQs.MAIN_NAMESPACE_SAMPLES)) {
			IQJhub1SamplesProvider prov = new IQJhub1SamplesProvider();
			iq = prov.parseIQ(parser);

		} else if (parser.getNamespace().equalsIgnoreCase(
				IQs.MAIN_NAMESPACE_ATOMIZED)) {
			IQJhub1AtomizedProvider prov = new IQJhub1AtomizedProvider();
			iq = prov.parseIQ(parser);
		}

		iq.setFrom(parserIQ.getAttributeValue("", "from"));
		iq.setTo(parserIQ.getAttributeValue("", "to"));
		String type = parserIQ.getAttributeValue("", "type");
		if (type.equalsIgnoreCase("get")) {
			iq.setType(Type.GET);
		} else if (type.equalsIgnoreCase("set")) {
			iq.setType(Type.SET);
		} else if (type.equalsIgnoreCase("result")) {
			iq.setType(Type.RESULT);
		}

		return iq;
	}

	public static XmlPullParser getParser(String control, String startTag)
			throws XmlPullParserException, IOException {

		XmlPullParser parser = new MXParser();
		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
		parser.setInput(new StringReader(control));

		while (true) {
			if (parser.next() == XmlPullParser.START_TAG
					&& parser.getName().equals(startTag)) {
				break;
			}
		}
		return parser;
	}
}
