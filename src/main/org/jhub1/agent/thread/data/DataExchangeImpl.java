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

package org.jhub1.agent.thread.data;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.jhub1.agent.AgentSample;
import org.jhub1.agent.ChannelType;
import org.jhub1.agent.Sample;
import org.jhub1.agent.configuration.AgentProperties;
import org.jhub1.agent.configuration.ConfigDataMorpher;
import org.jhub1.agent.configuration.ConfigDataMorpherImpl;
import org.jhub1.agent.configuration.ConfigFilesIn;
import org.jhub1.agent.configuration.ConfigFilesInImpl;
import org.jhub1.agent.configuration.ConfigFilesOut;
import org.jhub1.agent.configuration.ConfigFilesOutImpl;
import org.jhub1.agent.configuration.ConfigUDPIn;
import org.jhub1.agent.configuration.ConfigUDPInImpl;
import org.jhub1.agent.configuration.ConfigUDPOut;
import org.jhub1.agent.configuration.ConfigUDPOutImpl;
import org.jhub1.agent.configuration.ConfigXMPP;
import org.jhub1.agent.configuration.ConfigXMPPImpl;
import org.jhub1.agent.configuration.Enableable;
import org.jhub1.agent.statistics.Registry;
import org.jhub1.agent.thread.ThreadDataMorpher;
import org.jhub1.agent.thread.ThreadFileIn;
import org.jhub1.agent.thread.ThreadFileOut;
import org.jhub1.agent.thread.ThreadUDPIn;
import org.jhub1.agent.thread.ThreadUDPOut;
import org.jhub1.agent.thread.ThreadXMPP;
import org.jivesoftware.smack.packet.IQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataExchangeImpl implements DataExchangeXMPP {

// Properties used only by XMPP transport: 

	private BlockingQueue<IQ> incomingIQ;
	
	private BlockingQueue<IQ> outgoingIQ;
	
	private volatile boolean online;
	
// Properties below are used by core functionality:
	
	private BlockingQueue<Sample> input;
	
	private BlockingQueue<Sample> inputACK;

	private BlockingQueue<Sample> outputFile;

	private BlockingQueue<Sample> outputUDP;

	private ConfigUDPIn configUDPIn;

	private ConfigUDPOut configUDPOut;

	private ConfigFilesOut configFilesOut;

	private ConfigFilesIn configFilesIn;

	private ConfigXMPP configXMPP;
	
	private ConfigDataMorpher configDataMorpher;

	private AgentProperties properties;
	
	private static Logger log = LoggerFactory.getLogger(DataExchangeImpl.class);

	public DataExchangeImpl(AgentProperties properties) {
		incomingIQ = new ArrayBlockingQueue<>(properties.getIncomingIQQueueSize());
		outgoingIQ = new ArrayBlockingQueue<>(properties.getOutgoingIQQueueSize());		
		this.input = new ArrayBlockingQueue<>(
				properties.getInputSamplesQueueSize());
		this.outputFile = new ArrayBlockingQueue<>(
				properties.getOutputSamplesQueueSize());
		this.outputUDP = new ArrayBlockingQueue<>(
				properties.getOutputSamplesQueueSize());
		this.configFilesOut = new ConfigFilesOutImpl(properties);
		this.configUDPIn = new ConfigUDPInImpl(properties);
		this.configUDPOut = new ConfigUDPOutImpl(properties);
		this.configFilesIn = new ConfigFilesInImpl(properties);
		this.configDataMorpher = new ConfigDataMorpherImpl(properties);
		this.configXMPP = new ConfigXMPPImpl(properties, this);
		this.properties = properties;
	}

	public AgentProperties getProp() {
		return properties;
	}

	@Override
	public void queueInputEndPoint(Sample endpoint) {
		if (endpoint.isValid()) {
			Registry.getInstance()
					.increaseEventCounter(AgentSample.class, "in");
			input.offer(endpoint);
		}
	}

	@Override
	public Sample deQueueInputEndPoint() {
		return input.poll();
	}

	@Override
	public Map<String, Sample> deQueueInputEndPoints() {
		Map<String, Sample> iqItems = new HashMap<String, Sample>();
		int samplesPStanzaLimit = 50;
		while (input.size() > 0 && samplesPStanzaLimit > 0) {
			Sample s = input.poll();
			iqItems.put(s.getUID(), s);
	//		antechamber.put(s.getUID(), s);
			samplesPStanzaLimit--;
		}
		return iqItems;
		// limit - how many samples we can get at once (will fit one IQ stanza)
		// - 50?
	}

	@Override
	public void updateSafeQueue(String id) {
		deleteSampleFromAntechamber(id);
		// update time register - not implemented
	}
	
	private void deleteSampleFromAntechamber(String key) {
//		if (antechamber.containsKey(key)) {
//			antechamber.remove(key);
//		}
	}
	
	@Override
	public void queueOutputEndPoint(Sample endpoint) {
		// log.info("QUEUEING");
		if (endpoint.getChannel().equals(ChannelType.FILE)
				&& properties.isFileOutActive()) {
			log.info("QUEUEING FILE");
			outputFile.offer(endpoint);
			log.info("QUEUED FILE" + outputFile.size());
		} else if (endpoint.getChannel().equals(ChannelType.UDP)
				&& properties.isUDPOutActive()) {
			// log.info("QUEUEING UDP");
			outputUDP.offer(endpoint);
		} else {
			log.info("QUEUEING default");
			offerToDefaultQueue(endpoint);
		}
	}

	private void offerToDefaultQueue(Sample endpoint) {
		if (properties.isDefaultOutputChannelAllowed()) {
			if (properties.isOutputChannelUDPDefault()
					&& properties.isUDPOutActive()) {
				outputUDP.offer(endpoint);
			} else if (properties.isOutputChannelFileDefault()
					&& properties.isFileOutActive()) {
				outputFile.offer(endpoint);
			}
		}
	}

	@Override
	public Sample deQueueOutputEndPoint() {
		return null;
		// output.poll();
	}

	@Override
	public Enableable getConfig(Class<?> threadClass) {
		if (threadClass.equals(ThreadUDPIn.class)) {
			return configUDPIn;
		} else if (threadClass.equals(ThreadUDPOut.class)) {
			return configUDPOut;
		} else if (threadClass.equals(ThreadFileIn.class)) {
			return configFilesIn;
		} else if (threadClass.equals(ThreadFileOut.class)) {
			return configFilesOut;
		} else if (threadClass.equals(ThreadXMPP.class)) {
			return configXMPP;
		} else if (threadClass.equals(ThreadDataMorpher.class)) {
			return configDataMorpher;
		} else {
			return null;
		}
	}

	@Override
	public void saveRemoteProperties() {
		// TODO Auto-generated method stub

	}

	@Override
	public void reload() {
		properties.reload();
	}

	@Override
	public BlockingQueue<Sample> getInputQueue() {
		return input;
	}

	@Override
	public BlockingQueue<Sample> getOutputFileQueue() {
		return outputFile;
	}

	@Override
	public BlockingQueue<Sample> getOutputUDPQueue() {
		return outputUDP;
	}

	@Override
	public IQ deQueueIncomingIQ() {
		return incomingIQ.poll();
	}

	@Override
	public IQ deQueueOutgoingIQ() {
		return outgoingIQ.poll();
	}

	@Override
	public void queueIncomingIQ(IQ iq) {
		incomingIQ.offer(iq);	
	}

	@Override
	public void queueOutgoingIQ(IQ iq) {
		outgoingIQ.offer(iq);
	}

	@Override
	public boolean isOnline() {
		return online;
	}

	@Override
	public void setOnline(boolean online) {
		this.online = online; 
	}

}
