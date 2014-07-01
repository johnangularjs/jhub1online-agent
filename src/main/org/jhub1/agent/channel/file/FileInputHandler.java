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

package org.jhub1.agent.channel.file;

import java.util.List;

import org.jhub1.agent.Sample;
import org.jhub1.agent.AgentSample;
import org.jhub1.agent.ChannelType;
import org.jhub1.agent.channel.Inputable;
import org.jhub1.agent.configuration.ConfigFilesIn;
import org.jhub1.agent.file.FileProcessorImpl;
import org.jhub1.agent.file.InputFileProcessor;
import org.jhub1.agent.statistics.Registry;
import org.jhub1.agent.thread.data.DataExchangeXMPP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileInputHandler implements Inputable {

	private ConfigFilesIn config;
	private DataExchangeXMPP dex;
	private InputFileProcessor fp;
	private static Logger log = LoggerFactory.getLogger(FileInputHandler.class);

	public FileInputHandler(DataExchangeXMPP dex, ConfigFilesIn config) {
		this.dex = dex;
		this.config = config;
		fp = new FileProcessorImpl();
		fp.setPath(this.config.getPath());
	}
	
	@Override
	public boolean processInput(String epi) {
		List<String> eps = fp.getEndpointMessages();
		if (eps.size() > 0) {
			log.trace("Captured elements: " + eps.size());
			for (String ep : eps) {
				log.trace(ep);
				Sample ePoint = new AgentSample(ChannelType.FILE, ep);
				Registry.getInstance().increaseEventCounter(FileInputHandler.class, "in");
				dex.queueInputEndPoint(ePoint);
				log.debug(ePoint.toString());
			}
		}
		return false;
	}

	@Override
	public boolean processInput() {
		return processInput(null);
	}
}
