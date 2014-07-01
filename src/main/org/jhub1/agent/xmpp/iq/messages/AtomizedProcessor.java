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

package org.jhub1.agent.xmpp.iq.messages;

import org.jhub1.agent.thread.data.DataExchangeXMPP;
import org.jhub1.agent.thread.data.ProcessingException;
import org.jhub1.agent.xmpp.iq.IQJhub1Atomized;
import org.jhub1.agent.xmpp.iq.IQJhub1Atomized.Item;
import org.jhub1.agent.xmpp.iq.IQs;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.IQ.Type;

public class AtomizedProcessor implements Processor {
	
	private DataExchangeXMPP dx;
	
	public AtomizedProcessor(DataExchangeXMPP dataExchange) {
		dx = dataExchange;
	}
	
	@Override
	public void process(IQ iq) throws ProcessingException {
		IQJhub1Atomized iqAtomized = (IQJhub1Atomized) iq;
		if (iqAtomized.getItem().getResource().equalsIgnoreCase(
				IQs.ATOMIZED_NAMESPACE_CONFIG)) {
			if(iqAtomized.getType().equals(Type.SET) || iqAtomized.getType().equals(Type.RESULT)) {
				IQJhub1Atomized iqAtomizedResp = (IQJhub1Atomized) IQ.createResultIQ(iqAtomized);
				if(putConfig(iqAtomized.getItem())) {
					dx.reload();
					Item respItem = iqAtomized.getResponseItem(IQs.RESPONSE_STATUS_OK, ""); 
					iqAtomizedResp.setItem(respItem);
				} else {
					Item respItem = iqAtomized.getResponseItem(IQs.RESPONSE_STATUS_ERROR, "Could not save file");
					iqAtomizedResp.setItem(respItem);
				}
				dx.queueOutgoingIQ(iqAtomizedResp);
			} else if (iqAtomized.getType().equals(Type.GET)) {
				IQJhub1Atomized iqAtomizedResp = (IQJhub1Atomized) IQ.createResultIQ(iqAtomized);
				iqAtomizedResp.setItem(getConfig());
				dx.queueOutgoingIQ(iqAtomizedResp);
			} else {
				//INVALID REQUEST
				throw new ProcessingException("Unrecognized IQ type");
			}
		} else if (iqAtomized.getItem().getResource().equalsIgnoreCase(
				IQs.ATOMIZED_NAMESPACE_STATS)) {
			if(iqAtomized.getType().equals(Type.GET)) {
				//provide read only stats do not expect result
				IQJhub1Atomized iqAtomizedResp = (IQJhub1Atomized) IQ.createResultIQ(iqAtomized);
				iqAtomizedResp.setItem(getStats());
				dx.queueOutgoingIQ(iqAtomizedResp);
			} else {
				//NO SET end RESULT for stats!!
				//INVALID REQUEST
				throw new ProcessingException("Unrecognized or not supported IQ type");
			}
		} else {
			throw new ProcessingException("Undefined IQ namespace");
		}
	}

	
	private boolean putConfig(Item item) {
		return true;
	}
	
	private Item getConfig() {
		return null;
	}
	
	private Item getStats() {
		return null;
	}
}
