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

import org.jhub1.agent.channel.Outputable;
import org.jhub1.agent.channel.udp.UDPOutputHandler;
import org.jhub1.agent.configuration.ConfigUDPOut;
import org.jhub1.agent.thread.data.DataExchangeXMPP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadUDPOut implements Callable<Void> {
	private Outputable out;
	private ConfigUDPOut cfo;
	private static Logger log = LoggerFactory.getLogger(ThreadUDPOut.class);
	
	public ThreadUDPOut(DataExchangeXMPP dex) {
		this.cfo = (ConfigUDPOut) dex.getConfig(ThreadUDPOut.class);
		out = new UDPOutputHandler(dex, cfo);
	}
	
	@Override
	public Void call() throws Exception {
		Thread.currentThread().setName(ThreadUDPOut.class.getSimpleName());
		log.debug("UDP OUT is ready to send to IP: " + cfo.getIP() + " PORT: " + cfo.getPort());
		boolean loop = true;
		while (loop) {
			Thread.sleep(500);
			out.processOutput();
		}
		return null;
	}

}