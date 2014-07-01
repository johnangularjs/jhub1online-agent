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

package org.jhub1.agent.thread.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class ConcurrentFridge implements Fridge {
	
	private long expireTime;
	
	private Map<String, Object> samples;
	
	private Map<String, Long> time;
	
	public ConcurrentFridge(long expireTime) {
		this.expireTime = expireTime;
		this.samples = new HashMap<String, Object>();
		this.time = new HashMap<String, Long>();
	}

	@Override
	public synchronized void put(String key, Object object) {
		Date date = new Date();
		samples.put(key, object);
		time.put(key, date.getTime());
	}

	@Override
	public synchronized void throwAway(String key) {
		samples.remove(key);
		time.remove(key);
	}

	@Override
	public synchronized void clean() {
		this.samples = new HashMap<String, Object>();
		this.time = new HashMap<String, Long>();
	}

	@Override
	public synchronized Object drop() {
		Date date = new Date();
		long msec = date.getTime() - expireTime;
        Object obj = null;
        String key = null;
	    Iterator<Entry<String, Long>> it = time.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, Long> pairs = (Map.Entry<String, Long>)it.next();
	        if(pairs.getValue() < msec) {
	        	obj = samples.get(pairs.getKey());
	        	key = pairs.getKey();
	        }
	        it.remove();
	        if(null != obj) {
	        	break;
	        }
	    }
	    samples.remove(key);
	    time.remove(key);
		return obj;
	}

}
