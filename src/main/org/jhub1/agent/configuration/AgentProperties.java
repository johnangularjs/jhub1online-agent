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

import org.apache.commons.configuration.CompositeConfiguration;



public interface AgentProperties extends SystemProperties {
   
    String getFileInputDirectory();
    
    String getFileOutputDirectory();
    
	int getUDPInPort();

	int getUDPInSocketTimeout();
	
	boolean isUDPInActive();
	
	boolean isFileInActive();
	
	boolean isFileOutActive();
	
    String getJID();
    
    String getJServers();
	
    int getJPort();
    
    String getJPassword();
    
    String getJResourceID();
    
	int getUDPOutPort();
	
	String getUDPOutIP();
    
	boolean isUDPOutActive();
	
	CompositeConfiguration getCompositeConfiguration();
	
	int getFileInputPullFrequency();
    
	int getFileOutputPushFrequency();
	
	int getXMPPLoopDelay();
	
	int getXMPPSamplesBucketSize();
	
	long getXMPPPingInterval();

	int getXMPPMissingPingsAllowed();
	
	int getInputSamplesQueueSize();
	
	int getOutputSamplesQueueSize();
	
	int getIncomingIQQueueSize();
	
	int getOutgoingIQQueueSize();		
	
	boolean isThreadFileInEnabled();
	
	boolean isThreadFileOutEnabled();
	
	boolean isThreadUDPInEnabled();
	
	boolean isThreadUDPOutEnabled();
	
	boolean isThreadXMPPEnabled();
	
	boolean isThreadDataMorpherEnabled();
	
	boolean isThreadTestQLoopbackEnabled();
	
	boolean isOutputChannelUDPDefault();
	
	boolean isOutputChannelFileDefault();
	
	boolean isDefaultOutputChannelAllowed();
	
	int getDataMorpherProcessingFrequency();
	
    void reload();
/*
	CompositeConfiguration getCCI();
    
    String getInitialStatus();

    boolean isLogEnabled();
    
    boolean isJavaDebugEnabled();
    
    boolean isPresenceLogEnabled();
    
    String getSessionPassword();
    
    boolean isSetPortListener();
    
    boolean isSMSListener();
    
    boolean isIMListener();

    String getPIDFile();
    
    String getPName(String name);
    
    boolean isTrusted(String from);
    
    boolean isApproved(String from);
*/
}
