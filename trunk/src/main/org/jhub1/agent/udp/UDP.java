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

package org.jhub1.agent.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UDP extends AbstractUDP {

//	public static final String EXAMPLE_SAMPLE = "sampleName:134:32423333325";
	public static final int BUFFER_SIZE = 2048;

	private InetAddress inetAddress;
	private byte[] buffer;

	private DatagramSocket socket;
	
	private static Logger log = LoggerFactory.getLogger(UDP.class);
	
	public UDP(int port) {
		this.port = port;
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			log.error("Can't create DatagramSocket and bind it to the port: " + port + ". - " + e.getMessage());
			e.printStackTrace();
		}
		buffer = new byte[BUFFER_SIZE];
	}
	
	public UDP(int port, String address) {
		this.port = port;
		setAddress(address);	
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			log.error("Can't create DatagramSocket. - " + e.getMessage());
		}
		buffer = new byte[BUFFER_SIZE];
	}
	
	public void setAddress(String address) {
		try {
			inetAddress = InetAddress.getByName(address);
		} catch (UnknownHostException e1) {
			log.error("Can't determine host name. - " + e1.getMessage());
		}
	}

	private DatagramPacket getUDPPacket(String message) {	
		int msg_length = message.length();
		byte[] messageBytes = message.getBytes();
		DatagramPacket p = new DatagramPacket(messageBytes, msg_length, inetAddress, port);
		return p;
		
	}
	
	private DatagramPacket getUDPPacket() {
		DatagramPacket p = new DatagramPacket(buffer, buffer.length);
		return p;		
	}

	@Override
	public void sendMessage(String message) {
		DatagramPacket p = getUDPPacket(message);
		try {
			socket.send(p);
		} catch (IOException e) {
			log.error("Can't send DatagramPacket. - " + e.getMessage());
		}
	}

	@Override
	public String waitForMessage() {
		DatagramPacket p = getUDPPacket();
		try {
			socket.receive(p);
		} catch (IOException e) {
			log.error("Can't receive DatagramPacket. - " + e.getMessage());
		}
		String msg = new String(buffer, 0, p.getLength());
		return msg;
	}
}
