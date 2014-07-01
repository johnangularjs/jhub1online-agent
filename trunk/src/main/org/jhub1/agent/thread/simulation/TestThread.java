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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestThread implements Callable<Boolean> {

	private static Logger log = LoggerFactory.getLogger(TestThread.class);


	public TestThread() {

	}

	@Override
	public Boolean call() throws Exception {
		Thread.currentThread().setName("TestThread");
		boolean loop = true;
		while (loop) {
			Thread.sleep(10000); // every 30 minutes update
			log.info("Throwing exception.");
			throw new Exception();
		//	loop = false;
		}
		return true;
	}

}
