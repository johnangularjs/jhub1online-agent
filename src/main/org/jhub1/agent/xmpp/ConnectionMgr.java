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

import org.jhub1.agent.configuration.ConfigXMPP;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionMgr {

	private ConfigXMPP ca;
	private XMPPConnection xmppConn;
	private static Logger log = LoggerFactory.getLogger(ConnectionMgr.class);

	public ConnectionMgr(ConfigXMPP ca) {
		this.ca = ca;
		ConnectionConfiguration connConfig = new ConnectionConfiguration(ca.getServer(), ca.getPort(),
				ca.getDomain());
		xmppConn = new XMPPConnection(connConfig);
		Connection.DEBUG_ENABLED = false; // ca.isJavaDebugEnabled();
	}

	public XMPPConnection getConnection() {
		return xmppConn;
	}

	public void login() throws Exception {
		try {
			SASLAuthentication.supportSASLMechanism("PLAIN", 0);
			xmppConn.connect();
			xmppConn.login(ca.getNick(), ca.getPassword(), ca.getResourcePrefix());
		} catch (XMPPException ex) {
			log.error("Unable to connect to the server: " + ex.toString());
			throw new Exception();
		}
	}

	private boolean isConnected() {
		boolean connected = true;
		String result = null;
		if (xmppConn.isConnected() == true
				&& xmppConn.isAuthenticated() == true) {
/*			result = "Connect to " + xmppConn.getHost() + "; " + "JID - "
					+ xmppConn.getUser();
			log.trace(result);*/
		}
		if (xmppConn.isConnected() == true
				&& xmppConn.isAuthenticated() == false) {
			result = "Connection is not authenticated " + xmppConn.getHost()
					+ "!";
			log.error(result);
			connected = false;
		}
		if (xmppConn.isConnected() == false) {
			log.error("Connection to the XMPP server is lost!");
			connected = false;
		}
		return connected;
	}

	public void checkConection() throws Exception {
		if (!isConnected()) {
			log.error("Connection failed.");
			throw new Exception();
		} 
	}

	public void logout() {
		log.info("Shutting down!!!");
		xmppConn.disconnect();
		if (xmppConn.isConnected() == false) {
			log.debug("Offline");
		}
	}
}
