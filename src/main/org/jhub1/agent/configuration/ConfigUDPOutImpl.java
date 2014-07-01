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

public class ConfigUDPOutImpl extends Config implements ConfigUDPOut {
	
	private final int ON_DELAY = 5;
	
	private int currentPort = 0;
	private String currentIP;
	
	private int delay = 0;
	
	/**
	 * @param properties
	 */
	public ConfigUDPOutImpl(AgentProperties properties){
		this.properties = properties;
	}
	
	@Override
	public boolean isEnabled() {
		if(delay == 0) {
			currentPort = getPort();
			currentIP = getIP();
			return properties.isUDPOutActive();
		} else {
			delay--;
			return false;
		}
	}

	@Override
	public int getPort() {
		if(currentPort == 0) {
			currentPort = properties.getUDPOutPort();
		} else if(currentPort != properties.getUDPOutPort()) {
			delay = ON_DELAY;
		}
		return properties.getUDPOutPort();
	}
	
	@Override
	public String getIP() {
		if(null == currentIP) {
			currentIP = properties.getUDPOutIP();
		} else if(!currentIP.equals(properties.getUDPOutIP())) {
			delay = ON_DELAY;
		}
		return properties.getUDPOutIP();	
	}
	
	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ConfigUDPImpl:\n");
        sb.append(" Port: ").append(getPort()).append("\n");
        sb.append(" IP: ").append(getIP()).append("\n");
        sb.append(" Enabled: ").append(isEnabled());
        return sb.toString();
	}

}
