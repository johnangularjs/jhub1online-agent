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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.jivesoftware.smack.packet.IQ;

public class IQJhub1Samples extends IQ  {
	
	private final List<Item> items = new CopyOnWriteArrayList<Item>();

	/**
	 * Adds a new item to the discovered information.
	 * 
	 * @param item
	 *            the discovered entity's item
	 */
	public void addItem(Item item) {
		synchronized (items) {
			items.add(item);
		}
	}
	
	/**
	 * Returns the discovered items of the queried XMPP entity.
	 * 
	 * @return an Iterator on the discovered entity's items
	 */
	public Iterator<IQJhub1Samples.Item> getItems() {
		synchronized (items) {
			return Collections.unmodifiableList(items).iterator();
		}
	}

	public Iterator<IQJhub1Samples.Item> getWriteItems() {
		synchronized (items) {
			return items.iterator();
		}
	}

	@Override
	public String getChildElementXML() {
		StringBuilder buf = new StringBuilder();
		buf.append("<query xmlns='").append(IQs.MAIN_NAMESPACE_SAMPLES).append("'>");
		buf.append("<items>");
		synchronized (items) {
			for (Item item : items) {
				buf.append(item.toXML());
			}
			buf.append("</items>");
		}
		buf.append("</query>");
		return buf.toString();
		// return
		// "<query xmlns='jhub1:iq:gateway'><id>34</id><values>You did it</values></query>";
	}

	public static class Item {
		private String uid;
		private String name;
		private String timestamp;
		private String value;
		private String channel;
		
		/**
		 * Create a new Item associated with a given entity.
		 * 
		 * @param entityID
		 *            the id of the entity that contains the item
		 */
		public Item(String id) {
			this.uid = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getChannel() {
			return channel;
		}

		public void setChannel(String channel) {
			this.channel = channel;
		}
		
		public String getID() {
			return uid;
		}

		public String toSample() {
			StringBuilder buf = new StringBuilder();
			buf.append(name).append(":").append(value).append(":").append(timestamp);
			return buf.toString();
		}
		
		public String toXML() {
			StringBuilder buf = new StringBuilder();
			buf.append("<item uid='").append(uid).append("'");
			if (name != null) {
				buf.append(" name='").append(name).append("'");
			}
			if (timestamp != null) {
				buf.append(" timestamp='").append(timestamp).append("'");
			}
			if (channel != null) {
				buf.append(" channel='").append(channel).append("'");
			}
			if (value != null && value.length() > 0) {
				buf.append(">").append(value).append("</item>");
			} else {
				buf.append("/>");
			}
			return buf.toString();
		}

	}

}
