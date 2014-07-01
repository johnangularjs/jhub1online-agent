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

package org.jhub1.agent.xmpp.iq.messages;

import org.jhub1.agent.AgentSample;
import org.jhub1.agent.ChannelType;
import org.jhub1.agent.Sample;
import org.jhub1.agent.xmpp.iq.IQJhub1Samples.Item;

public class SamplesFactory {

	public static Sample makeFromItem(Item item) {
		
		ChannelType channel = ChannelType.FILE;
		
		if(item.getChannel().equalsIgnoreCase("UDP")) {
			channel = ChannelType.UDP;
		}
		
		Sample sample = new AgentSample(channel, item.toSample(), item.getID());
		
		return sample;
		
	}
	
}
