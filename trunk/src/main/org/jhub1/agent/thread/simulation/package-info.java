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

// set request from server - should result with creating output files
// java -jar JHUB1OnlineAgentDev.jar -piq "<iq type='set' to='ayak/admin' from='zyak/admin' id='1001'><query xmlns='jhub1:agent:sample'><items><item uid='uid00001' name='sampleNo1' timestamp='0123452342345' channel='FILE'>33</item><item uid='uid00002' name='sampleNo2' timestamp='0123452342555' channel='FILE'>34</item></items></query></iq>"

// result should be send to server
// java -jar JHUB1OnlineAgentDev.jar -piq "<iq type='result' to='ayak/admin' from='zyak/admin' id='1001'><query xmlns='jhub1:agent:sample'><items><item uid='uid00001' /><item uid='uid00002' /></items></query></iq>"

// request for statistics result should be send to server
//java -jar JHUB1OnlineAgentDev.jar -piq "<iq type='get' to='ayak/admin' from='zyak/admin' id='1001'><query xmlns='jhub1:agent:atomized'><resource xmlns="jhub1:agent:resource:stats"/></query></iq>"

// set new configuration - result ACK should be send back
//java -jar JHUB1OnlineAgentDev.jar -piq "<iq type='set' to='ayak/admin' from='zyak/admin' id='1001'><query xmlns='jhub1:agent:atomized'><resource xmlns='jhub1:agent:resource:config' id='34234' format='xml' hash='dsfsdfsd'> CiAgICA8c2NyaXB0IHR5cGU9InRleHQvamF2YXNjcmlwdCIgc3JjPSJkYXRhOnRleHQvamF2YXNjcml</resource></query></iq>"