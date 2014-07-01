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

package org.jhub1.agent.thread.monitor;

import org.jhub1.agent.statistics.Registry;
import org.jhub1.agent.thread.data.DataExchangeXMPP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ThreadConfigReloadTest implements Runnable {

	private DataExchangeXMPP dex;

	private static Logger log = LoggerFactory
			.getLogger(ThreadConfigReloadTest.class);
	
	public ThreadConfigReloadTest(DataExchangeXMPP dex) {
		this.dex = dex;
	}

	@Override
	public void run() {
		Thread.currentThread().setName("ThreadConfigReloadTest");
		log.trace("Starting config reloader...");
		while (true) {
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				log.error("Problem with reloading: " + e.getMessage());
			}
			dex.reload();
			System.out.println(Registry.getInstance().toString());
		}
	}
}
