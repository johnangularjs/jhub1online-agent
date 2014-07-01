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

package org.jhub1.agent.configuration;

import org.jhub1.agent.thread.data.DataExchangeXMPP;

public interface ConfigXMPP extends Enableable {

	int getPort();

	String getResourcePrefix();

	String getResource();
	
	String getPassword();

	String getJID();

	String getServer();

	String getAltServer();
	
	String getNick();

	String getDomain();

	int getLoopDelay();
	
	int getBucketSize();
	
	long getPingInterval();
	
	int getAllowedMissingPings();
	
	DataExchangeXMPP getDataExchange();
}
