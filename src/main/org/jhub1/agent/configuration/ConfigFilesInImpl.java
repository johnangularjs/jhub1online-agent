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

public class ConfigFilesInImpl extends Config implements ConfigFilesIn {

	public ConfigFilesInImpl(AgentProperties properties){
		this.properties = properties;
	}

	@Override
	public boolean isEnabled() {
		return properties.isFileInActive();
	}

	@Override
	public String getPath() {
		return properties.getFileInputDirectory();
	}

	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ConfigFilesInImpl:\n");
        sb.append(" Path: ").append(getPath()).append("\n");
        sb.append(" Enabled: ").append(isEnabled());
        return sb.toString();
	}

	@Override
	public int getPullFrequency() {
		return properties.getFileInputPullFrequency();
	}
	
}
