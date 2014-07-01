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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.jhub1.agent.Sample;
import org.jhub1.agent.channel.Outputable;
import org.jhub1.agent.configuration.ConfigFilesOut;
import org.jhub1.agent.file.FileProcessorImpl;
import org.jhub1.agent.file.OutputFileProcessor;
import org.jhub1.agent.thread.data.DataExchangeXMPP;

public class FileOutputHandler implements Outputable {

	private ConfigFilesOut config;
	private DataExchangeXMPP dex;
	private OutputFileProcessor fp;
//	private static Logger log = LoggerFactory.getLogger(FileInputHandler.class);

	public FileOutputHandler(DataExchangeXMPP dex, ConfigFilesOut config) {
		this.dex = dex;
		this.config = config;
		fp = new FileProcessorImpl();
		fp.setPath(this.config.getPath());
	}
	
	@Override
	public boolean processOutput() {
		List<Sample> samples = null;
		BlockingQueue<Sample> bq = dex.getOutputFileQueue();
		if(!bq.isEmpty()) {
			samples = new ArrayList<Sample>();
		}
		while(!bq.isEmpty()) {
			samples.add(bq.poll());
		}
		if(null != samples && !samples.isEmpty()) {
			fp.saveEndpointMessages(samples);
		}
		return false;
	}

}
