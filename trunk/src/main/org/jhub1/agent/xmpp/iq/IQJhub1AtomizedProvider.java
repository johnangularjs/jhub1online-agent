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

/**
 * Represents XMPP packets.
 *
 * @author Marek Kaszewski
 */
public class IQJhub1AtomizedProvider implements IQProvider {
	private static Logger log = LoggerFactory.getLogger(IQJhub1AtomizedProvider.class);
	
	@Override
	public IQ parseIQ(XmlPullParser parser) throws Exception {
		
		IQJhub1Atomized gwItems = new IQJhub1Atomized();
		IQJhub1Atomized.Item item;
		boolean done = false;
		String payload = null;
		String status = null;
		String format = null;
		String hash = null;
		String err_code = null;
		String id = null;

		String name;
		String ns = null;
		String namespace = parser.getNamespace();
		
        while (!done) {
            int eventType = parser.next();
            name = parser.getName();
            
    		if (eventType == XmlPullParser.START_TAG && "resource".equals(name)) {
    			ns = parser.getAttributeValue("", "ns");
                id = parser.getAttributeValue("", "id");
                err_code = parser.getAttributeValue("", "err_code");
                format = parser.getAttributeValue("", "format");
                hash = parser.getAttributeValue("", "hash");
                status = parser.getAttributeValue("", "status");                 
            }
            else if (eventType == XmlPullParser.TEXT && !parser.isWhitespace()) {
            	payload = parser.getText();
            }
            else if (eventType == XmlPullParser.END_TAG && "resource".equals(name)) {
                // Create a new Item and add it to DiscoverItems.
                  item = new IQJhub1Atomized.Item(id);
                  item.setStatus(status);
                  item.setHash(hash);
                  item.setFormat(format);
                  item.setErrorCode(err_code);
                  item.setPayload(payload);
                  item.setResource(ns);
                  gwItems.setItem(item);
            }
            else if (eventType == XmlPullParser.END_TAG && "query".equals(name)) {
                done = true;
            }
        }	
        log.debug("Providing IQ: '" + namespace + " with namespace: " + ns + "'.");
		return gwItems;
	}
}