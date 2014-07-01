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

package org.jhub1.agent.channel.udp;

import org.jhub1.agent.AgentSample;
import org.jhub1.agent.ChannelType;
import org.jhub1.agent.Sample;
import org.jhub1.agent.channel.Inputable;
import org.jhub1.agent.statistics.Registry;
import org.jhub1.agent.thread.data.DataExchangeXMPP;
import org.jhub1.agent.xmpp.iq.IQFactory;
import org.jhub1.agent.xmpp.iq.IQs;
import org.jivesoftware.smack.packet.IQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UDPInputHandler implements Inputable{

	private DataExchangeXMPP dex;
	
	private static Logger log = LoggerFactory.getLogger(UDPInputHandler.class);
	
	public UDPInputHandler(DataExchangeXMPP dex) {
		this.dex = dex;
	}
	//9454
	@Override
	public boolean processInput(String epi) {
		// This might be an IQ stanza or serialised sample
		if(epi.matches(IQs.ACCEPTED_IQ_REGEX)) {
			IQ iq = null;
			try {
				iq = IQFactory.getIQFromXML(epi);
			} catch (Exception e) {
				log.error("Error with parsing IQ stanza send via UDP: " + epi);
			}
			if(null != iq) {
				dex.queueIncomingIQ(iq);
				log.debug("Incoming IQ has heen queued.");
			}
			return true;
		} else if(epi.matches(AgentSample.REGEX_SAMPLE_OVERALL)) { 		// TODO improve regex sample definition
			// It is a sample
			Sample sample = new AgentSample(ChannelType.UDP, epi);
			Registry.getInstance().increaseEventCounter(UDPInputHandler.class, "in");
			dex.queueInputEndPoint(sample);
			log.debug(sample.toString());
			return true;
		} else {
			// there is no handler for string like that
		}
		return false;
	}

	@Override
	public boolean processInput() {
		return false;
	}

}
