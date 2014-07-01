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

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.Callable;

import org.jhub1.agent.channel.Inputable;
import org.jhub1.agent.channel.udp.UDPInputHandler;
import org.jhub1.agent.configuration.ConfigUDPIn;
import org.jhub1.agent.thread.data.DataExchangeXMPP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadUDPIn implements Callable<Void> {

	private DatagramPacket packet;
	private DatagramSocket socket;
	private static Logger log = LoggerFactory.getLogger(ThreadUDPIn.class);
	private Inputable in;
	private int failingPort = 0;
	private int runningPort = 0;
	private ConfigUDPIn configUDPIn;
	
	public ThreadUDPIn(DataExchangeXMPP dex) {
		in = new UDPInputHandler(dex);
		configUDPIn =  (ConfigUDPIn) dex.getConfig(ThreadUDPIn.class);
	}

	public Void call() throws Exception {
		Thread.currentThread().setName(ThreadUDPIn.class.getSimpleName());
		byte[] buffer = new byte[2048];
		packet = new DatagramPacket(buffer, buffer.length);
		while (true) {
			if (configUDPIn.isEnabled() && failingPort != configUDPIn.getPort()) {
				boolean result = openSocket();
				if (result) {
					log.info("Listening for samples on UDP on port " + configUDPIn.getPort());
					while (configUDPIn.isEnabled() && runningPort == configUDPIn.getPort()) {
						try {
							socket.receive(packet);
							String msg = new String(buffer, 0, packet.getLength());
							log.debug("UDP message received: " + msg);
							in.processInput(msg);
						} catch (SocketTimeoutException e) {
							// this is OK, carry on
						} catch (IOException e) {
							log.error("Problem with receiving UDP packet: "	+ e.getMessage());
							throw new Exception();	
						}
					}
					closeSocket();
					log.info("Turning OFF listening UDP on port.");
				} else {
					failingPort = configUDPIn.getPort();
				}
			} else {
				Thread.sleep(8000);
				log.debug(configUDPIn.getPort() + "  " + configUDPIn.isEnabled());
			}
		}
	}

	private boolean openSocket() {
		try {
			socket = new DatagramSocket(configUDPIn.getPort());
			socket.setSoTimeout(configUDPIn.getSocketTimeout());
		} catch (SocketException e) {
			log.error("Can't lock socket " + configUDPIn.getPort() + ". Change port or release the current. "
					+ e.getMessage());
			return false;
		}
		runningPort = configUDPIn.getPort();
		return true;
	}

	private void closeSocket() {
		socket.close();
		runningPort = 0;
	}
}