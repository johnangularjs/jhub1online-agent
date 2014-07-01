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

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.provider.IQProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlpull.v1.XmlPullParser;

public class IQJhub1SamplesProvider implements IQProvider {
	private static Logger log = LoggerFactory.getLogger(IQJhub1SamplesProvider.class);
	
	@Override
	public IQ parseIQ(XmlPullParser parser) throws Exception {
		
		IQJhub1Samples gwItems = new IQJhub1Samples();
		IQJhub1Samples.Item item;
		boolean done = false;

		String value = null;
		String timestamp = null;
		String sName = null;
		String uid = null;
		String channel = null;
		
		String name;
		String namespace = parser.getNamespace();
		
        while (!done) {
            int eventType = parser.next();
            name = parser.getName();
             
            if (eventType == XmlPullParser.START_TAG && "item".equals(name)) {
                uid = parser.getAttributeValue("", "uid");
                timestamp = parser.getAttributeValue("", "timestamp");
                sName = parser.getAttributeValue("", "name");
                channel = parser.getAttributeValue("", "channel"); 
            }
            else if (eventType == XmlPullParser.TEXT && !parser.isWhitespace()) {
            	value = parser.getText();
            }
            else if (eventType == XmlPullParser.END_TAG && "item".equals(name)) {
                // Create a new Item and add it to DiscoverItems.
                  item = new IQJhub1Samples.Item(uid);
                  item.setTimestamp(timestamp);
                  item.setName(sName);
                  item.setValue(value);
                  item.setChannel(channel);
                  gwItems.addItem(item);
            }
            else if (eventType == XmlPullParser.END_TAG && "query".equals(name)) {
                done = true;
            }
        }	
        log.debug("Proviging IQ: '" + namespace + "'.");
		return gwItems;
	}
}