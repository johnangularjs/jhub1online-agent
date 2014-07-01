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

import java.util.concurrent.Callable;

import org.jhub1.agent.channel.Inputable;
import org.jhub1.agent.channel.file.FileInputHandler;
import org.jhub1.agent.configuration.ConfigFilesIn;
import org.jhub1.agent.thread.data.DataExchangeXMPP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadFileIn implements Callable<Void>{

	private Inputable in;
	private ConfigFilesIn cfi;
	private static Logger log = LoggerFactory.getLogger(ThreadFileIn.class);
	
	public ThreadFileIn(DataExchangeXMPP dex) {
		this.cfi = (ConfigFilesIn) dex.getConfig(ThreadFileIn.class);
		in = new FileInputHandler(dex, cfi);
	}
	
	@Override
	public Void call() throws Exception {
		Thread.currentThread().setName(ThreadFileIn.class.getSimpleName());
		log.debug("Looking for samples in folder: " + cfi.getPath());
		boolean loop = true;
		while (loop) {
			Thread.sleep(cfi.getPullFrequency());
			in.processInput();
		}
		return null;
	}

}
