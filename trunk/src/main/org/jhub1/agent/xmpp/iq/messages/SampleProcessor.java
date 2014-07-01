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

import java.util.Iterator;

import org.jhub1.agent.Sample;
import org.jhub1.agent.thread.data.DataExchangeXMPP;
import org.jhub1.agent.thread.data.ProcessingException;
import org.jhub1.agent.xmpp.iq.IQJhub1Samples;
import org.jhub1.agent.xmpp.iq.IQJhub1Samples.Item;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.IQ.Type;

public class SampleProcessor implements Processor {

	private DataExchangeXMPP dx;
	
	public SampleProcessor(DataExchangeXMPP dataExchange) {
		dx =dataExchange;
	}
	
	@Override
	public void process(IQ iq) throws ProcessingException {
		IQJhub1Samples iqSamples = (IQJhub1Samples) iq;
		if(iqSamples.getType().equals(Type.SET)) {
			Iterator<Item> items = iqSamples.getItems();
			while(items.hasNext()) {
				Item item = items.next();
				Sample entreaty = SamplesFactory.makeFromItem(item);
				dx.queueOutputEndPoint(entreaty);
			}
			//Processed by Morpher thread from now on
		} else if (iqSamples.getType().equals(Type.RESULT)) {
			// here update sent sample s list by deleting ACKs IDs
			Iterator<Item> items = iqSamples.getItems();
			while(items.hasNext()) {
				Item item = items.next();
				dx.updateSafeQueue(item.getID());
			}
		} else if (iq.getType().equals(Type.ERROR)){
			throw new ProcessingException("IQ type ERROR!?");
		}
	}
}