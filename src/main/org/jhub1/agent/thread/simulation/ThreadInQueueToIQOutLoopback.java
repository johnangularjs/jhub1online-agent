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

package org.jhub1.agent.thread.simulation;

import java.util.concurrent.Callable;

import org.jhub1.agent.thread.data.DataExchangeXMPP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadInQueueToIQOutLoopback  implements Callable<Void> {

	private DataExchangeXMPP dex;
	private static Logger log = LoggerFactory
			.getLogger(ThreadInQueueToOutQueueLoopback.class);

	public ThreadInQueueToIQOutLoopback(DataExchangeXMPP dex) {
		this.dex = dex;
	}

	@Override
	public Void call() throws Exception {
		Thread.currentThread().setName(
				ThreadInQueueToOutQueueLoopback.class.getSimpleName());
		log.info("Test Queue Loopback is waiting for Samples.");
		boolean loop = true;
		while (loop) {
			Thread.sleep(200);
			if (dex.getInputQueue().size() > 0) {
				log.trace("LOOPBACK: Transfering IN queue to OUT queue!");
				synchronized (this) { // why? just because the counters might be giving inaccurate results otherwise
					int cCounter = dex.getInputQueue().size();
					int counter = 0;
					while (dex.getInputQueue().size() > 0) {
						counter++;
						dex.queueOutputEndPoint(dex.deQueueInputEndPoint());
					}
					log.trace("LOOPBACK: Transfered: " + counter + " out of "
							+ cCounter + ".");
				}
			}
		}
		return null;
	}

}