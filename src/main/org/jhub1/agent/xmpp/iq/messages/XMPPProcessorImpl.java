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
import org.jhub1.agent.thread.data.MessagesProcessor;
import org.jhub1.agent.thread.data.ProcessingException;
import org.jhub1.agent.xmpp.iq.IQJhub1Atomized;
import org.jhub1.agent.xmpp.iq.IQJhub1Samples;
import org.jivesoftware.smack.packet.IQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMPPProcessorImpl implements MessagesProcessor {

	private DataExchangeXMPP dx;
	private AtomizedProcessor ap;
	private SampleProcessor sp;
	
	private static Logger log = LoggerFactory
			.getLogger(XMPPProcessorImpl.class);

	public XMPPProcessorImpl(DataExchangeXMPP dataExchange) {
		dx = dataExchange;
		ap = new AtomizedProcessor(dataExchange);
		sp = new SampleProcessor(dataExchange);
	}

	@Override
	public void processIncomingMessages() {
		IQ iq = dx.deQueueIncomingIQ();
		while (null != iq) {
			log.trace("Incoming IQ: " + iq.toXML());
			if (iq instanceof IQJhub1Atomized) {
				try {
					ap.process(iq);
				} catch (ProcessingException e) {
					log.error("Processing IQJhub1Atomized: "
							+ iq.getChildElementXML() + " -> " + e.getMessage());
				}
			} else if (iq instanceof IQJhub1Samples) {
				try {
					sp.process(iq);
				} catch (ProcessingException e) {
					log.error("Processing IQJhub1Samples: "
							+ iq.getChildElementXML() + " -> " + e.getMessage());
				}
			}
			iq = dx.deQueueIncomingIQ();
		}
	}

	@Override
	public void processOutgoingMessages() {
		// TODO Auto-generated method stub

	}

}
