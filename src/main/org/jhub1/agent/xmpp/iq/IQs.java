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

package org.jhub1.agent.xmpp.iq;

public class IQs {
	public static final String MAIN_NAMESPACE_SAMPLES = "jhub1:agent:sample";
	public static final String MAIN_NAMESPACE_ATOMIZED = "jhub1:agent:atomized";
	public static final String ATOMIZED_NAMESPACE_CONFIG = "jhub1:agent:resource:config";
	public static final String ATOMIZED_NAMESPACE_STATS  = "jhub1:agent:resource:stats";
	public static final String SAMPLE  = "SAMPLE";
	public static final String SAMPLE_ACK = "SAMPLE_ACK";
	public static final String ENTREATY = "ENTREATY";
	public static final String ENTREATY_ACK = "ENTREATY_ACK";
	public static final String ELEMENT = "query";
	
	public static final String RESPONSE_STATUS_OK = "OK";
	public static final String RESPONSE_STATUS_ERROR = "ERROR";
	
	public static final String NAMESPACE_REGEX = "xmlns='([0-9a-zA-Z:]*)";
	public static final String ACCEPTED_IQ_REGEX = "<(iq|IQ) (.*)</(iq|IQ)>";
}
