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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.jhub1.agent.configuration.Enableable;
import org.jhub1.agent.statistics.Registry;
import org.jhub1.agent.thread.data.DataExchangeXMPP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadMonitor implements Runnable {

	private Future<Void> es;
	private ExecutorService service;
	private Enableable config;
	private DataExchangeXMPP dex;
	private Class<?> threadClass;

	private static Logger log = LoggerFactory.getLogger(ThreadMonitor.class);

	public ThreadMonitor(DataExchangeXMPP dex, Class<?> class1) {
		this.dex = dex;
		this.threadClass = class1;
		this.config = dex.getConfig(class1);
	}

	@Override
	public void run() {
		Thread.currentThread().setName(threadClass.toString());
		if (null != config) {
			while (true) {
				if (config.isEnabled()) {
					runTask();
					while (config.isEnabled()) {
						try {
							es.get(1000, TimeUnit.MILLISECONDS); //without timeout we would get stuck here
						} catch (ExecutionException ex) {
							log.error("Exception (1) in task: " + threadClass.getSimpleName() + " - " + ex.getMessage());
							catchExceptionEvent(ex.toString());
						} catch (InterruptedException ex) {
							log.error("Exception (2) in task: " + threadClass.getSimpleName() + " - " + ex.getMessage());
							catchExceptionEvent(ex.toString());
						} catch (TimeoutException ex) {
							//this exception is happening whenever previously set timeout occurs
							//do not record - just let it go
						}
					}
					log.info("Stopping the task: " + threadClass.getSimpleName());
					shutdownTask();
				} else if (!config.isEnabled()) {
					while (!config.isEnabled()) {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException ex) {
							log.error("Exception (3) in task: " + threadClass.getSimpleName() + " - " + ex.getMessage());
						}
					}
				}
			}
		} else {
			log.warn("Config not provided for: " + threadClass.getSimpleName() + " Just running the thread.");
			runTask();
		}

	}

	@SuppressWarnings("unchecked")
	private void runTask() {
		Class<?> clazz = null;
		try {
			clazz = Class.forName(threadClass.getCanonicalName());
		} catch (ClassNotFoundException ex) {
			log.error("Exception (4) in task: " + threadClass.getSimpleName() + " - " + ex.getMessage());
		}
		Constructor<?> c = null;
		try {
			c = clazz.getConstructor(DataExchangeXMPP.class);
		} catch (NoSuchMethodException | SecurityException ex) {
			log.error("Exception (5) in task: " + threadClass.getSimpleName() + " - " + ex.getMessage());
		}
		Callable<Void> thread = null;
		try {
			thread = (Callable<Void>) c.newInstance(dex);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException ex) {
			log.error("Exception (6) in task: " + threadClass.getSimpleName() + " - " + ex.getMessage());
		}
		service = Executors.newSingleThreadExecutor();
		Registry.getInstance().setEventTimestamp(threadClass, Registry.EVENT_START);
		es = service.submit(thread);
	}

	private void shutdownTask() {
		Registry.getInstance().removeEventTimestamp(threadClass, Registry.EVENT_START);
		service.shutdownNow();
		while (!service.isTerminated()) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException ex) {
				log.error("Exception (7) in task: " + threadClass.getSimpleName() + " - " + ex.getMessage());
			}
		}
	}

	private void catchExceptionEvent(String message) {
		log.warn("The exception has been catch: " + message);
		Registry.getInstance().removeEventTimestamp(threadClass, Registry.EVENT_START);
		service.shutdownNow();
		shutdownTask();
		log.info("Restarting Task.");
		runTask();
	}
}
