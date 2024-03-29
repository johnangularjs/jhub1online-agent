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

package org.jhub1.agent.xmpp.iq;

import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.ping.packet.Ping;

public class CustomIQPacketFilter implements PacketFilter  {

	@Override
	public boolean accept(Packet paramPacket) {
		if(null != paramPacket) {
			if(null != paramPacket.getClass()) {
		if(paramPacket.getClass().equals(IQJhub1Samples.class)) {
			return true;
		} else if(paramPacket.getClass().equals(IQJhub1Atomized.class)) {
			return true;
		} else if(paramPacket.getClass().equals(Ping.class)) {
			return true;
		} 
			}
		}
		return false;
	}

}
