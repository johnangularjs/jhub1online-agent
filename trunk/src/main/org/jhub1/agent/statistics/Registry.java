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

package org.jhub1.agent.statistics;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

/**
 * The purpose of this registry is to gather running stats from the agent like
 * start time / restart time and so on
 * 
 * This object will not grow with time.
 * 
 * @author marek
 * 
 */
public class Registry {

	public static final String EVENT_START = "start";
	public static final String EVENT_RESTART_COUNTER = "restartCounter";
	
	private volatile Map<String, Object> register;

	private static Registry instance;

	public static final String INPUT = "in";
	public static final String OUTPUT = "out";

	private static final Map<Long, Integer> P_COUNTER;
	
	private static final int P_COUNTER_MAX = 300; 
	
	static {
		P_COUNTER = new HashMap<Long, Integer>();
		P_COUNTER.put(new Long(0), new Integer(0));
		P_COUNTER.put(new Long(1), new Integer(5));
		P_COUNTER.put(new Long(2), new Integer(10));
		P_COUNTER.put(new Long(3), new Integer(15));
		P_COUNTER.put(new Long(4), new Integer(20));
		P_COUNTER.put(new Long(5), new Integer(30));
	}
	
	private Registry() {
		register = new HashMap<String, Object>();
	}

	public void reset() {
		register = new HashMap<String, Object>();
	}

	public static Registry getInstance() {
		if (null == instance) {
			synchronized (Registry.class) {
				if (null == instance) {
					instance = new Registry();
					return instance;
				}
			}
		} else {
			return instance;
		}
		return instance;
	}

	public synchronized void setEventTimestamp(Class<?> clazz, String name) {
		saveValue(assembleName(clazz, name), new DateTime());
	}

	public synchronized DateTime getEventTimestamp(Class<?> clazz, String name) {
		return (DateTime) recoverValue(assembleName(clazz, name));
	}

	public synchronized void removeEventTimestamp(Class<?> clazz, String name) {
		deleteValue(assembleName(clazz, name));
	}

	public synchronized void increaseEventCounter(Class<?> clazz, String name) {
			Long counter = (Long) recoverLongValue(assembleName(clazz, name));
			counter++;
			saveValue(assembleName(clazz, name), counter);
	}

	public synchronized void resetEventCounter(Class<?> clazz, String name) {
		deleteValue(assembleName(clazz, name));
	}

	public synchronized Long getEventCounter(Class<?> clazz, String name) {
		return (Long) recoverLongValue(assembleName(clazz, name));
	}

	public synchronized void setValue(Class<?> clazz, String name, Object obj) {
		saveValue(assembleName(clazz, name), obj);
	}

	public synchronized Object getValue(Class<?> clazz, String name) {
		return recoverValue(assembleName(clazz, name));
	}

	public synchronized int getProgressiveDelay(Class<?> clazz, String name) {
		Long c = getEventCounter(clazz, name);
		increaseEventCounter(clazz, name);
		if(P_COUNTER.containsKey(c)) {
			return P_COUNTER.get(c);
		}
		return P_COUNTER_MAX;
	}
	
	public synchronized int getNextProgressiveDelay(Class<?> clazz, String name) {
		Long c = getEventCounter(clazz, name);
		if(P_COUNTER.containsKey(c)) {
			return P_COUNTER.get(c);
		}
		return P_COUNTER_MAX;
	}
	
	public synchronized void resetProgressiveDelay(Class<?> clazz, String name) {
		resetEventCounter(clazz, name);
	}
	
	private void saveValue(String name, Object value) {
		register.put(name, value);
	}

	private Object recoverLongValue(String name) {
		if (register.containsKey(name)) {
			return register.get(name);
		}
		return new Long(0);
	}
	
	private Object recoverValue(String name) {
		if (register.containsKey(name)) {
			return register.get(name);
		}
		return null;
	}

	private void deleteValue(String name) {
		if (register.containsKey(name)) {
			register.remove(name);
		}
	}

	private String assembleName(Class<?> clazz, String name) {
		return clazz.getSimpleName() + "/" + name;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		synchronized (this) {
			for (Map.Entry<String, Object> entry : register.entrySet()) {
				if (entry.getValue() instanceof DateTime) {
					sb.append(entry.getKey())
							.append(" - ")
							.append(SmartDateDisplay
									.showHowLong((DateTime) entry.getValue()))
							.append("\n");
				} else if (entry.getValue() instanceof Long) {
					sb.append(entry.getKey()).append(" - ")
							.append((Long) entry.getValue()).append("\n");
				}
			}
		}
		return sb.toString();

	}

	public synchronized String toFormatedOutput(OutputAdapter adapter) {
		return adapter.getSerializedRegistry(register);
	}
}
