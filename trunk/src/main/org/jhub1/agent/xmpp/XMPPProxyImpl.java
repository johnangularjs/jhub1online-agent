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

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.jhub1.agent.configuration.ConfigXMPP;
import org.jhub1.agent.statistics.Registry;
import org.jhub1.agent.thread.data.DataExchangeXMPP;
import org.jhub1.agent.xmpp.iq.CustomIQPacketFilter;
import org.jhub1.agent.xmpp.iq.IQJhub1AtomizedProvider;
import org.jhub1.agent.xmpp.iq.IQJhub1SamplesProvider;
import org.jhub1.agent.xmpp.iq.IQs;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.Roster.SubscriptionMode;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.ping.packet.Ping;
import org.jivesoftware.smack.provider.ProviderManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMPPProxyImpl implements XMPPProxy {

	private ConnectionMgr connMgr;
	private DataExchangeXMPP dataExchange;
	
	private PacketFilter filterIQ = new CustomIQPacketFilter();
	
	private static Logger log = LoggerFactory.getLogger(XMPPProxyImpl.class);
	
	public XMPPProxyImpl(ConfigXMPP configXMPP) throws Exception {

		this.dataExchange = configXMPP.getDataExchange();
		
		Connection.DEBUG_ENABLED = true;
		
		connMgr = new ConnectionMgr(configXMPP);
		
		connMgr.getConnection().createPacketCollector(filterIQ);
		
		ProviderManager pm = ProviderManager.getInstance();
		pm.addIQProvider(IQs.ELEMENT, IQs.MAIN_NAMESPACE_SAMPLES, new IQJhub1SamplesProvider());
		pm.addIQProvider(IQs.ELEMENT, IQs.MAIN_NAMESPACE_ATOMIZED, new IQJhub1AtomizedProvider());
		
		connMgr.login();
		
		Roster.setDefaultSubscriptionMode(SubscriptionMode.manual);
		
		log.trace("INIT done, trying to set status...");
		
		setStatus("JHUB1OnlineClient - Ready!");
	}

	private void setStatus(String status) throws Exception {
		connMgr.checkConection();
		Presence presence = new Presence(Presence.Type.available);
		presence.setStatus(status);
		presence.setPriority(1);
		connMgr.getConnection().sendPacket(presence);
		log.trace("Status: " + status);
	}


	public Collection<RosterEntry> getRoster() {
		StringBuilder msg = new StringBuilder();
		Roster roster = connMgr.getConnection().getRoster();
		roster.reload();
		Collection<RosterEntry> entries = roster.getEntries();
		Iterator<RosterEntry> it = entries.iterator();
		while (it.hasNext()) {
			msg.append(it.next().toString() + "; ");
		}
		log.info("Roster content: " + msg.toString());
		return entries;
	}


	public void disconnect() throws Exception {
		connMgr.logout();
		throw new Exception();
	}

	private void sendIQ(IQ iq) throws Exception {
	//	iq.setType(IQ.Type.RESULT); do not set result as this is going to be already set;
		try {
			connMgr.getConnection().sendPacket(iq);
		} catch (Exception e) {
			log.error("The IQ could not be send - saved for later");
			throw new Exception(e.getMessage());
		} finally {
			dataExchange.queueOutgoingIQ(iq);
		}
	}

	private void parseIQPacket(IQ iq) throws XMPPException, IOException {		
		Registry.getInstance()
		.increaseEventCounter(IQ.class, Registry.INPUT);
		dataExchange.queueIncomingIQ(iq);
	}

	@Override
	public void setIncomingPackagesListener() throws Exception {
		PacketListener iqListener = new PacketListener() {

			@Override
			public void processPacket(Packet packet) {
				if(packet.getClass().equals(Ping.class)) {
					dataExchange.setOnline(true);
				} else {
					dataExchange.setOnline(true);
	 				try {
						try {
							parseIQPacket((IQ) packet);
						} catch (IOException ex) {
							log.error(ex.toString());
						}
					} catch (XMPPException ex) {
						log.error(ex.toString());
					}
				}
			}
		};
		connMgr.getConnection().addPacketListener(iqListener, filterIQ);
	}
	
	@Override
	public XMPPConnection getConnection() {
		return connMgr.getConnection();
	}

	@Override
	public void dispatchOutgoingMessages() throws Exception {
		
		connMgr.checkConection();
		
		IQ iq = dataExchange.deQueueOutgoingIQ();
		if(null != iq) {
			sendIQ(iq);
		}
	}

}