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

/**
 * Implements following namespaces for XMPP IQ elements:
 * 
 * IQJhub1Atomized namespace: jhub1:agent:atomized
 *
 * This is a universal element for transporting base64 payload 
 * what in this case is going to be a set of serialised objects.  
 *  
 * Retrieving agent's statistics:
 * 
 *   SEND: <iq type='get' to='ayak/admin' from='zyak/admin' id='1001'>
 *          <query xmlns='jhub1:agent:atomized'>
 *          	<resource xmlns="jhub1:agent:resource:stats"/>
 *          </query>
 *         </iq>
 *
 *   RECV: <iq type="result" id="1001">
 *          <query xmlns='jhub1:agent:atomized'>
 *          	<resource xmlns="jhub1:agent:resource:stats" >
 *          		<item id='34234' format='json|xml' hash=''>
 *          CiAgICA8c2NyaXB0IHR5cGU9InRleHQvamF2YXNjcmlwdCIgc3JjPSJkYXRhOnRleHQvamF2YXNjcml
 *          wdDtiYXNlNjQsLzlqLzRBQVFTa1pKUmdBQkFRRUFXZ0JhQUFELzRneFlTVU5EWDFCU1QwWkpURVVBQV
 *          FFQUFBeElUR2x1YndJUUFBQi4uLiI+PC9zY3JpcHQ+DQogICAgSFRNTCBDU1MgZW1iZWRkaW5nOg0KI
 *          CAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgdHlwZT0idGV4dC9jc3MiIGhyZWY9ImRhdGE6dGV4dC9j
 *          c3M7YmFzZTY0LC85ai80QUFRU2taSlJnQUJBUUVBV2dCYUFBRC80Z3hZU1VORFgxQlNUMFpKVEVVQUF
 *          RRUFBQXhJVEdsdWJ3SVFBQUIuLi4iIC8+DQogICAgSFRNTCBpbWFnZSBlbWJlZGRpbmc6
 *          		</item>
 *          	</resource>
 *          </query>
 *         </iq>
 *id='34234' format='json|xml' hash='' status='OK' [err_code='']
 * Retrieving agent's configuration:
 * 
 *   SEND: <iq type='get' to='yak/admin' id="1005">
 *          <query xmlns='jhub1:agent:atomized'>
 *          	<resource xmlns="jhub1:agent:resource:config"/>
 *          </query>
 *         </iq>
 *
 *OLD DESIGN:
 *   RECV: <iq type="result" id="1005">
 *          <query xmlns='jhub1:agent:atomized'>
 *          	<resource xmlns="jhub1:agent:resource:config" >
 *          		<item id='34234' format='json|xml' hash=''>
 *          CiAgICA8c2NyaXB0IHR5cGU9InRleHQvamF2YXNjcmlwdCIgc3JjPSJkYXRhOnRleHQvamF2YXNjcml
 *          wdDtiYXNlNjQsLzlqLzRBQVFTa1pKUmdBQkFRRUFXZ0JhQUFELzRneFlTVU5EWDFCU1QwWkpURVVBQV
 *          FFQUFBeElUR2x1YndJUUFBQi4uLiI+PC9zY3JpcHQ+DQogICAgSFRNTCBDU1MgZW1iZWRkaW5nOg0KI
 *          CAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgdHlwZT0idGV4dC9jc3MiIGhyZWY9ImRhdGE6dGV4dC9j
 *          c3M7YmFzZTY0LC85ai80QUFRU2taSlJnQUJBUUVBV2dCYUFBRC80Z3hZU1VORFgxQlNUMFpKVEVVQUF
 *          RRUFBQXhJVEdsdWJ3SVFBQUIuLi4iIC8+DQogICAgSFRNTCBpbWFnZSBlbWJlZGRpbmc6
 *          		</item>
 *          	</resource>
 *          </query>
 *         </iq>
 *
 *NEW DESIGN:
 *   RECV: <iq type="result" id="1005">
 *          <query xmlns='jhub1:agent:atomized'>
 *          	<resource xmlns='jhub1:agent:resource:config' id='34234' format='json|xml' hash=''>
 *          CiAgICA8c2NyaXB0IHR5cGU9InRleHQvamF2YXNjcmlwdCIgc3JjPSJkYXRhOnRleHQvamF2YXNjcml
 *          wdDtiYXNlNjQsLzlqLzRBQVFTa1pKUmdBQkFRRUFXZ0JhQUFELzRneFlTVU5EWDFCU1QwWkpURVVBQV
 *          FFQUFBeElUR2x1YndJUUFBQi4uLiI+PC9zY3JpcHQ+DQogICAgSFRNTCBDU1MgZW1iZWRkaW5nOg0KI
 *          CAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgdHlwZT0idGV4dC9jc3MiIGhyZWY9ImRhdGE6dGV4dC9j
 *          c3M7YmFzZTY0LC85ai80QUFRU2taSlJnQUJBUUVBV2dCYUFBRC80Z3hZU1VORFgxQlNUMFpKVEVVQUF
 *          RRUFBQXhJVEdsdWJ3SVFBQUIuLi4iIC8+DQogICAgSFRNTCBpbWFnZSBlbWJlZGRpbmc6
 *          	</resource>
 *          </query>
 *         </iq>
 *
 *
 * Submitting agent's configuration:
 *OLD DESIGN:  
 *   SEND: <iq type='set' to='yak/admin' id="1007">
 *          <query xmlns='jhub1:agent:atomized'>
 *          	<resource xmlns="jhub1:agent:resource:config"/>
 *          		<item id='34234' format='json|xml' hash='' >
 *          CiAgICA8c2NyaXB0IHR5cGU9InRleHQvamF2YXNjcmlwdCIgc3JjPSJkYXRhOnRleHQvamF2YXNjcml
 *          wdDtiYXNlNjQsLzlqLzRBQVFTa1pKUmdBQkFRRUFXZ0JhQUFELzRneFlTVU5EWDFCU1QwWkpURVVBQV
 *          FFQUFBeElUR2x1YndJUUFBQi4uLiI+PC9zY3JpcHQ+DQogICAgSFRNTCBDU1MgZW1iZWRkaW5nOg0KI
 *          CAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgdHlwZT0idGV4dC9jc3MiIGhyZWY9ImRhdGE6dGV4dC9j
 *          c3M7YmFzZTY0LC85ai80QUFRU2taSlJnQUJBUUVBV2dCYUFBRC80Z3hZU1VORFgxQlNUMFpKVEVVQUF
 *          RRUFBQXhJVEdsdWJ3SVFBQUIuLi4iIC8+DQogICAgSFRNTCBpbWFnZSBlbWJlZGRpbmc6
 *          		</item>
 *          </query>
 *         </iq>
 * 
 *NEW DESIGN:  
 *   SEND: <iq type='set' to='yak/admin' id="1007">
 *          <query xmlns='jhub1:agent:atomized'>
 *          	<resource xmlns='jhub1:agent:resource:config' item id='34234' format='json|xml' hash='' >
 *          CiAgICA8c2NyaXB0IHR5cGU9InRleHQvamF2YXNjcmlwdCIgc3JjPSJkYXRhOnRleHQvamF2YXNjcml
 *          wdDtiYXNlNjQsLzlqLzRBQVFTa1pKUmdBQkFRRUFXZ0JhQUFELzRneFlTVU5EWDFCU1QwWkpURVVBQV
 *          FFQUFBeElUR2x1YndJUUFBQi4uLiI+PC9zY3JpcHQ+DQogICAgSFRNTCBDU1MgZW1iZWRkaW5nOg0KI
 *          CAgIDxsaW5rIHJlbD0ic3R5bGVzaGVldCIgdHlwZT0idGV4dC9jc3MiIGhyZWY9ImRhdGE6dGV4dC9j
 *          c3M7YmFzZTY0LC85ai80QUFRU2taSlJnQUJBUUVBV2dCYUFBRC80Z3hZU1VORFgxQlNUMFpKVEVVQUF
 *          RRUFBQXhJVEdsdWJ3SVFBQUIuLi4iIC8+DQogICAgSFRNTCBpbWFnZSBlbWJlZGRpbmc6
 *          	</resource>
 *          </query>
 *         </iq>
 * 
 *   RECV: <iq type="result" id="1007">
 *          <query xmlns='jhub1:agent:atomized'>
 *          	<resource xmlns="jhub1:agent:resource:config" id='34234' status='OK' [err_code=''] />
 *          </query>
 *         </iq>
 *   
 *   
 * IQJhub1Samples namespace: jhub1:agent:sample
 *
 * The elements purpose is to transport samples and orders between 
 * server and agent.  
 *  
 * Sending sample from agent to server:
 * OUTBOUND
 *   SEND: <iq type='set' to='yak/admin' id="1001">
 *          <query xmlns='jhub1:agent:sample'>
 *          	<items>
 *          		<item uid='CiAgICA8c2NyaXamgc3J' name='temperature' timestamp='1234334334332'>33</item>
 *          		<item uid='CiAgICA8fgfdytNjcm3J' name='temp2' timestamp='1234334334666'>79</item>
 *          	</items>
 *          </query>
 *         </iq>
 *         
 * AGENT INBOUND          
 *   RECV: <iq type="result" id="1001">
 *          <query xmlns='jhub1:agent:sample'>
 *          	<items>
 *          		<item uid='CiAgICA8c2NyaXamgc3J' />
 *          		<item uid='CiAgICA8fgfdytNjcm3J' />
 *          	</items>
 *          </query>
 *         </iq> 
 *         
 * Sending entreaty from server to agent:
 * AGENT INBOUND
 *   SEND: <iq type='set' to='yak/admin' id="1001">
 *          <query xmlns='jhub1:agent:sample'>
 *          	<items>
 *          		<item uid='uid00001' name='sampleNo1' timestamp='0123452342345' channel='FILE'>33</item>
 *          		<item uid='uid00002' name='sampleNo2' timestamp='0123452342555' channel='FILE'>34</item>
 *          	</items>
 *          </query>
 *         </iq>
 * OUTBOUND
 *   RECV: <iq type="result" id="1001">
 *          <query xmlns='jhub1:agent:sample'>
 *          	<items>
 *          		<item uid='CiAgICA8c2NyaXamgc3J' />
 *          		<item uid='CiAgICA8fgfdytNjcm3J' />
 *          	</items>
 *          </query>
 *         </iq> 
 *         
 *         
 *         
 *         
 *         
 *         
 *         
 * @author marek
 */
/*Manual Examples

<iq type='set' to='jhub1onlineagent01@xmpp.org.uk/JHUB1Agent' id="1001">
           <query xmlns='jhub1:agent:sample'>
           	<items>
           		<item uid='uid00001' name='sampleNo1' timestamp='0123452342345' channel='FILE'>33</item>
           		<item uid='uid00002' name='sampleNo2' timestamp='0123452342555' channel='FILE'>34</item>
           	</items>
           </query>
        </iq>

*/
package org.jhub1.agent.xmpp.iq;