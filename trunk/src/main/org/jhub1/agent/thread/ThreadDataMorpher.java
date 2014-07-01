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

package org.jhub1.agent.thread;

import java.util.concurrent.Callable;

import org.jhub1.agent.configuration.ConfigDataMorpher;
import org.jhub1.agent.thread.data.DataExchangeXMPP;
import org.jhub1.agent.thread.data.MessagesProcessor;
import org.jhub1.agent.xmpp.iq.messages.XMPPProcessorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadDataMorpher implements Callable<Void>{

	private MessagesProcessor proc;
	private ConfigDataMorpher cfi;
	private static Logger log = LoggerFactory.getLogger(ThreadDataMorpher.class);
	
	public ThreadDataMorpher(DataExchangeXMPP dex) {
		this.cfi = (ConfigDataMorpher) dex.getConfig(ThreadDataMorpher.class);
		proc = new XMPPProcessorImpl(dex);
		String dfsd = "dfsdfsd";
		String name = ThreadDataMorpher.class.getSimpleName();
		
	}
	
	@Override
	public Void call() throws Exception {
		Thread.currentThread().setName(ThreadDataMorpher.class.getSimpleName());
		log.debug("Starting data morpher with the processing window: " + cfi.getProcessingFrequency() + "ms");
		Thread.sleep(cfi.getProcessingFrequency());
		boolean loop = true;
		while (loop) {
			Thread.sleep(cfi.getProcessingFrequency());
			proc.processIncomingMessages();
			proc.processOutgoingMessages();
		}
		return null;
	}

}