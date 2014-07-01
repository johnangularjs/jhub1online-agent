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

package org.jhub1.agent.configuration;

import org.jhub1.agent.randoms.Randoms;
import org.jhub1.agent.thread.data.DataExchangeXMPP;



public class ConfigXMPPImpl extends Config implements ConfigXMPP {

	private int usedServer = 0;
	
	private DataExchangeXMPP dataExchange;

	public ConfigXMPPImpl(AgentProperties properties, DataExchangeXMPP dataExchange){
		this.properties = properties;
		this.dataExchange = dataExchange;
	}
	
	@Override
	public int getPort() {
		return properties.getJPort();
	}

	@Override
	public String getResourcePrefix() {	
		return properties.getJResourceID();
	}

	@Override
	public String getPassword() {
		return properties.getJPassword();
	}

	@Override
	public String getJID() {
		return properties.getJID();
	}

	@Override
	public String getServer() {
		String[] servers = getServersArray();
		return servers[usedServer];
	}

	@Override
	public String getAltServer() {
		String[] servers = getServersArray();
		if (servers.length > 1) {
			if(usedServer == servers.length - 1) {
				usedServer = 0;
				return servers[usedServer];
			} else {
				usedServer++;
				return servers[usedServer];
			}
		} else {
			return getServer();
		}
	}

	@Override
	public String getNick() {
		String[] parts = properties.getJID().split("\\@");
		return parts[0];
	}

	@Override
	public String getDomain() {
		String[] parts = properties.getJID().split("\\@");
		return parts[1];
	}

	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ConfigXMPPImpl.class.getSimpleName()).append(": ");
        sb.append(" Port: ").append(getPort()).append("\n");
        sb.append(" Resource: ").append(getResourcePrefix()).append("\n");
        sb.append(" Password: ").append(getPassword()).append("\n");
        sb.append(" Jid: ").append(getJID()).append("\n");    
        sb.append(" Servers: ").append(properties.getJServers()).append("\n");
        sb.append(" Used Server: ").append(getServer()).append("\n");
        sb.append(" Enabled: ").append(isEnabled());
        return sb.toString();
	}

	@Override
	public boolean isEnabled() {
	   /**
		* @todo add logic that would return false in case of misconfiguratio
		* 
		*/
		return true;
	}

	private String[] getServersArray() {
		String[] servers = properties.getJServers().split("\\|");
		return servers;	
	}

	@Override
	public int getLoopDelay() {
		return properties.getXMPPLoopDelay();
	}

	@Override
	public int getBucketSize() {
		return properties.getXMPPSamplesBucketSize();
	}

	@Override
	public DataExchangeXMPP getDataExchange() {
		return dataExchange;
	}

	@Override
	public String getResource() {
		// TODO Auto-generated method stub
		//Randoms.getInstance().generateResourceID();
		return getResourcePrefix() + Randoms.getInstance().generateResourceID();
	}

	@Override
	public long getPingInterval() {
		return properties.getXMPPPingInterval();
	}

	@Override
	public int getAllowedMissingPings() {
		return properties.getXMPPMissingPingsAllowed();
	}
	
}
