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

import org.jhub1.agent.configuration.ConfigXMPP;
import org.jhub1.agent.statistics.Registry;
import org.jhub1.agent.thread.data.DataExchangeXMPP;
import org.jhub1.agent.xmpp.PingManager;
import org.jhub1.agent.xmpp.XMPPProxy;
import org.jhub1.agent.xmpp.XMPPProxyImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadXMPP implements Callable<Void>{

	private ConfigXMPP cfi;
	private static Logger log = LoggerFactory.getLogger(ThreadXMPP.class);
	
	public ThreadXMPP(DataExchangeXMPP dex) {
		this.cfi = (ConfigXMPP) dex.getConfig(ThreadXMPP.class);
	}
	
	@Override
	public Void call() throws Exception {
		Thread.currentThread().setName(ThreadXMPP.class.getSimpleName());
		int progressiveDelay = Registry.getInstance().getProgressiveDelay(ThreadXMPP.class, Registry.EVENT_RESTART_COUNTER);
		Thread.sleep(progressiveDelay * 1000);
		log.debug("Starting XMPP daemon.");
		XMPPProxy xmpp = null;
		try{
			xmpp = new XMPPProxyImpl(cfi);
		} catch (Exception e) {
			int nextPD = Registry.getInstance().getNextProgressiveDelay(ThreadXMPP.class, Registry.EVENT_RESTART_COUNTER);
			log.error("Problem with instantiating XMPP transport. Message: " + e.getMessage() + ". Waiting " + nextPD + "s before retry.");
			throw new Exception();
		}
		xmpp.setIncomingPackagesListener();
		PingManager pingMgr = new PingManager(xmpp.getConnection(), cfi);
		boolean justStarted = true;
		boolean loop = true;
		while (loop) {
			xmpp.dispatchOutgoingMessages();
			Thread.sleep(cfi.getLoopDelay());
			pingMgr.checkConnection();
			if(justStarted) {
				Registry.getInstance().resetProgressiveDelay(ThreadXMPP.class, Registry.EVENT_RESTART_COUNTER);
				justStarted = false;
			}
		}
		return null;
	}

}
