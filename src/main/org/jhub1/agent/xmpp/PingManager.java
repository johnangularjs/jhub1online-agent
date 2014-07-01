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

package org.jhub1.agent.xmpp;

import java.util.Date;

import org.jhub1.agent.configuration.ConfigXMPP;
import org.jhub1.agent.thread.data.DataExchangeXMPP;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.keepalive.KeepAliveManager;
import org.jivesoftware.smack.ping.PingFailedListener;

public class PingManager implements PingFailedListener {

	private final static long PING_INTERVAL = 15000L;
	private KeepAliveManager keepAlive;
	private Date firstLostPingTime;
	private DataExchangeXMPP dex;
	private ConfigXMPP config;
	
	@Override
	public void pingFailed() {
		System.out.println("PING FAILED");
		if (null == firstLostPingTime) {
			firstLostPingTime = new Date(); // record missing ping time
		}
		keepAlive.setPingInterval(PING_INTERVAL); // reset for another ping
		dex.setOnline(false); // share information with data exchange
	}

	public PingManager(XMPPConnection connection, ConfigXMPP cfi) {
		dex = cfi.getDataExchange();
		config = cfi;
		keepAlive = KeepAliveManager.getInstanceFor(connection);
		keepAlive.setPingInterval(config.getPingInterval()); // 15 s.
		keepAlive.addPingFailedListener((PingFailedListener) this);
		dex.setOnline(true);
	}

	public void checkConnection() throws Exception {
		if (null != firstLostPingTime && !dex.isOnline()) {
			Date dateNow = new Date();
			if ((dateNow.getTime() - firstLostPingTime.getTime()) >= (config.getPingInterval() * config.getAllowedMissingPings())) {
				throw new Exception(
						"Connection is lost - pings are not going through..."); // this will do restart
			}
		}
		if(dex.isOnline()) {
			firstLostPingTime = null;
		}
	}
}
