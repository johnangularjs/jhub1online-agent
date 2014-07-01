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

import java.util.Map;
import java.util.concurrent.BlockingQueue;

import org.jhub1.agent.Sample;
import org.jhub1.agent.configuration.Enableable;

public interface DataExchange {
	
	void queueInputEndPoint(Sample endpoint);
	
	Sample deQueueInputEndPoint();
	
	Map<String, Sample> deQueueInputEndPoints();

	void queueOutputEndPoint(Sample endpoint);
	
	Sample deQueueOutputEndPoint();
	
	Enableable getConfig(Class<?> class1);
	
	BlockingQueue<Sample> getInputQueue();
	
	BlockingQueue<Sample> getOutputFileQueue();
	
	BlockingQueue<Sample> getOutputUDPQueue();
	
	/**
	 * Deletes the samples immediately after receiving ACK from server
	 * In case the sample stays not updated for longer it is going to be resent
	 * @param id
	 */
	void updateSafeQueue(String id);
	
	void reload();
	
	void saveRemoteProperties();
	
}
