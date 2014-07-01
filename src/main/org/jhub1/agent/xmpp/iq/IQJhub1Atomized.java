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

public class IQJhub1Atomized extends IQ {

	

	private Item item;
	
	/**
	 * Adds a new item to the discovered information.
	 * 
	 * @param item
	 *            the discovered entity's item
	 */
	public void setItem(Item item) {
			this.item = item;
	}

	/**
	 * Returns the discovered items of the queried XMPP entity.
	 * 
	 * @return an Iterator on the discovered entity's items
	 */
	public Item getItem() {
		return item;
	}

	public Item getResponseItem(String status, String error) {
		return null;
	}

	@Override
	public String getChildElementXML() {
		StringBuilder buf = new StringBuilder();
		buf.append("<query xmlns='").append(IQs.MAIN_NAMESPACE_ATOMIZED).append("'>");
		buf.append(item.toXML());
		buf.append("</query>");
		return buf.toString();
	}

	public static class Item {
		private String resource;
		private String payload;
		private String format;
		private String status;
		private String err_code;
		private String hash;
		private String id;

		/**
		 * Create a new Item associated with a given entity.
		 * 
		 * @param entityID
		 *            the id of the entity that contains the item
		 */
		public Item(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}

		public String getFormat() {
			return format;
		}

		public void setFormat(String format) {
			this.format = format;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getErrorCode() {
			return err_code;
		}

		public void setErrorCode(String err_code) {
			this.err_code = err_code;
		}

		public String getHash() {
			return hash;
		}

		public void setHash(String hash) {
			this.hash = hash;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getResource() {
			return resource;
		}

		public void setResource(String resource) {
			this.resource = resource;
		}
		
		/**
		 * Sets the entity's payload.
		 * 
		 * @param name
		 *            the entity's payload.
		 */
		public void setPayload(String payload) {
			this.payload = payload;
		}

		/**
		 * Returns the entity's Command.
		 * 
		 * @return the entity's Command.
		 */
		public String getPayload() {
			return payload;
		}

		public String toXML() {
			StringBuilder buf = new StringBuilder();
			buf.append("<resource ns='").append(resource).append("'");
			buf.append(" id='").append(id).append("'");
			if (format != null) {
				buf.append(" format='").append(format).append("'");
			}
			if (hash != null) {
				buf.append(" hash='").append(hash).append("'");
			}
			if (status != null) {
				buf.append(" status='").append(status).append("'");
			}
			if (err_code != null) {
				buf.append(" err_code='").append(err_code).append("'");
			}
			if (payload != null && payload.length() > 0) {
				buf.append(">").append(payload).append("</resource>");
			} else {
				buf.append("/>");
			}
			return buf.toString();
		}

	}

}
