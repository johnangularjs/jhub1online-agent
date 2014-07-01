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

package org.jhub1.agent.run;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.configuration.ConfigurationException;
import org.jhub1.agent.configuration.AgentProperties;
import org.jhub1.agent.configuration.PropertiesImpl;
import org.jhub1.agent.run.cli.ParamsMain;
import org.jhub1.agent.statistics.Registry;
import org.jhub1.agent.thread.ThreadDataMorpher;
import org.jhub1.agent.thread.ThreadFileIn;
import org.jhub1.agent.thread.ThreadFileOut;
import org.jhub1.agent.thread.ThreadUDPIn;
import org.jhub1.agent.thread.ThreadUDPOut;
import org.jhub1.agent.thread.ThreadXMPP;
import org.jhub1.agent.thread.data.DataExchangeXMPP;
import org.jhub1.agent.thread.data.DataExchangeImpl;
import org.jhub1.agent.thread.monitor.ThreadConfigReloadTest;
import org.jhub1.agent.thread.monitor.ThreadMonitor;
import org.jhub1.agent.thread.simulation.ThreadInQueueToOutQueueLoopback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Driver {

	private static AgentProperties config;
	
	private static DataExchangeXMPP dex;

	private static Logger log = LoggerFactory.getLogger(Driver.class);

	/**
	 * @param args
	 * @throws ExecutionException
	 * @throws ConfigurationException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException,
			ExecutionException {
		
		if(args.length > 0) {
			ParamsMain pp = new ParamsMain();
			pp.parse(args);
			System.exit(0);
		}		
		/**
		 * @TODO Make sure that config is being picked only from current folder
		 *       - not home java -Dlogback.configurationFile=/path/to/config.xml
		 *       chapters.configuration.MyApp1
		 * 
		 *       File/UDP input format: name:value:time
		 */
		systemInit();
		
		threadsInit();
		
		int counter = 0;
		while (true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				log.error("Main testing loop problem: " + e.getMessage());
			}
			counter++;
			if (counter >= 40) {
				counter = 0;
				log.trace("Waiting...");
			}
		}

	}

	private static void systemInit() {
		Thread.currentThread().setName("Jhub1OnlineAgent");
		Registry.getInstance().setEventTimestamp(Driver.class, "start");
		Path path = Paths.get(".");
		if(!Files.isWritable(path)) {
			log.error("The directory is not writable. Can't start!");
			System.exit(0);
		}
		// construct configuration object
		try {
			config = new PropertiesImpl();
		} catch (ConfigurationException e1) {
			log.error("Configuration can't be red. " + e1.getMessage());
			System.exit(0);
		}
		// construct data exchange object
		dex = new DataExchangeImpl(config);
		
		log.info("Environment initiated! OS: " + config.getSysOsName() + ", Version: " + config.getSysOsVersion() + ", Arch: " + config.getSysOsArch());
	}
	
	private static void threadsInit() {
		List<Runnable> tasks = new ArrayList<>();
		
		if(config.isThreadFileInEnabled())
		    tasks.add((Runnable) new ThreadMonitor(dex, ThreadFileIn.class));
		
		if(config.isThreadFileOutEnabled())
			tasks.add((Runnable) new ThreadMonitor(dex, ThreadFileOut.class));
		
		if(config.isThreadUDPInEnabled())
			tasks.add((Runnable) new ThreadMonitor(dex, ThreadUDPIn.class));
		
		if(config.isThreadUDPOutEnabled())
			tasks.add((Runnable) new ThreadMonitor(dex, ThreadUDPOut.class));
		
		if(config.isThreadTestQLoopbackEnabled()) {
			tasks.add((Runnable) new ThreadMonitor(dex, ThreadInQueueToOutQueueLoopback.class));
		} else {
			if(config.isThreadXMPPEnabled()) {
				tasks.add((Runnable) new ThreadMonitor(dex, ThreadXMPP.class));
			}
			if(config.isThreadDataMorpherEnabled()) {
				tasks.add((Runnable) new ThreadMonitor(dex, ThreadDataMorpher.class));
			}
		}
		
		tasks.add((Runnable) new ThreadConfigReloadTest(dex));
		
		if (!tasks.isEmpty()) {
			ExecutorService executor = Executors.newFixedThreadPool(tasks
					.size());
			for (Runnable task : tasks) {
				executor.submit(task);
			}
		}
	}
}
