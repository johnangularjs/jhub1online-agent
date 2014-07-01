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
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.ManagedReloadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesImpl implements AgentProperties {

	private ManagedReloadingStrategy reloadingStrategy;
	private CompositeConfiguration config;
	private boolean reloadFlag = false;

	private static Logger log = LoggerFactory.getLogger(PropertiesImpl.class);

	public PropertiesImpl() throws ConfigurationException {
		reloadingStrategy = new ManagedReloadingStrategy();
		config = new CompositeConfiguration();
		config.addConfiguration(new SystemConfiguration());
		config.addConfiguration(new PropertiesConfiguration("agent.properties"));
		config.addConfiguration(new PropertiesConfiguration(
				"agent.internal.properties"));
		try {
			XMLConfiguration fromFile = new XMLConfiguration("settings.xml");
			fromFile.setReloadingStrategy(reloadingStrategy);
			reloadFlag = true;
			config.addConfiguration(fromFile);
		} catch (ConfigurationException ce) {
			log.error("Problem with loading custom configuration: "
					+ ce.getMessage());
		}
		config.addConfiguration(new XMLConfiguration("settings.default.xml"));
	}

	@Override
	public String getFileInputDirectory() {
		return config.getString("jhub1online.agent.file.input.folder",
				"file/input/");
	}

	@Override
	public String getFileOutputDirectory() {
		return config.getString("jhub1online.agent.file.output.folder",
				"file/output/");
	}

	@Override
	public void reload() {
		if (reloadFlag) {
			reloadingStrategy.refresh();
		}
	}

	@Override
	public int getUDPInPort() {
		return config.getInt("config.channels.inputconfig.udp.port", 8821);
	}

	@Override
	public boolean isUDPInActive() {
		return config.getBoolean("config.channels.input.udp", false);
	}

	@Override
	public boolean isFileInActive() {
		return config.getBoolean("config.channels.input.file", true);
	}

	@Override
	public boolean isFileOutActive() {
		return config.getBoolean("config.channels.output.file", true);
	}

	@Override
	public String getJID() {
		return config.getString("jhub1online.agent.jid");
	}

	@Override
	public String getJServers() {
		return config.getString("jhub1online.agent.servers");
	}

	@Override
	public int getJPort() {
		return config.getInt("jhub1online.agent.ports", 443);
	}

	@Override
	public String getJPassword() {
		return config.getString("jhub1online.agent.password");
	}

	@Override
	public String getJResourceID() {
		return config.getString("jhub1online.agent.resourceid",
				"JHUB1OnlineAgent");
	}

	@Override
	public int getUDPOutPort() {
		return config.getInt("config.channels.outputconfig.udp.port", 8898);
	}

	@Override
	public String getUDPOutIP() {
		return config.getString("config.channels.outputconfig.udp.ip");
	}

	@Override
	public boolean isUDPOutActive() {
		return config.getBoolean("config.channels.output.udp", false);
	}

	@Override
	public CompositeConfiguration getCompositeConfiguration() {
		return config;
	}

	@Override
	public String getSysOsName() {
		return config.getString("os.name");
	}

	@Override
	public String getSysOsArch() {
		return config.getString("os.arch");
	}

	@Override
	public String getSysOsVersion() {
		return config.getString("os.version");
	}

	@Override
	public String getSysFileSeparator() {
		return config.getString("file.separator");
	}

	@Override
	public String getSysPathSeparator() {
		return config.getString("path.separator");
	}

	@Override
	public String getSysLineSeparator() {
		return config.getString("line.separator");
	}

	@Override
	public String getSysUserName() {
		return config.getString("user.name");
	}

	@Override
	public String getSysUserHome() {
		return config.getString("user.home");
	}

	@Override
	public String getSysUserDir() {
		return config.getString("user.dir");
	}

	@Override
	public String getSysJavaVersion() {
		return config.getString("java.version");
	}

	@Override
	public String getSysJavaVendor() {
		return config.getString("java.vendor");
	}

	@Override
	public String getSysJavaVendorURL() {
		return config.getString("java.vendor.url");
	}

	@Override
	public String getSysJavaHome() {
		return config.getString("java.home");
	}

	@Override
	public String getSysJavaClassVersion() {
		return config.getString("java.class.version");
	}

	@Override
	public String getSysJavaClassPath() {
		return config.getString("java.class.path");
	}

	@Override
	public String getSysJavaLibraryPath() {
		return config.getString("java.library.path");
	}

	@Override
	public String getSysJavaIOTempDir() {
		return config.getString("java.io.tmpdir");
	}

	@Override
	public String getSysJavaCompiler() {
		return config.getString("java.compiler");
	}

	@Override
	public String getSysJavaExtDirs() {
		return config.getString("java.ext.dirs");
	}

	@Override
	public int getFileInputPullFrequency() {
		return config.getInt("jhub1online.agent.file.input.pull_freq_ms", 9999);
	}

	@Override
	public int getFileOutputPushFrequency() {
		return config.getInt("jhub1online.agent.file.output.push_freq_ms", 9999);
	}

	@Override
	public int getXMPPLoopDelay() {
		return config.getInt("jhub1online.agent.xmpp.update_delay_ms", 49);
	}

	@Override
	public int getXMPPSamplesBucketSize() {
		return config.getInt("jhub1online.agent.xmpp.samples_bucket_size", 24);
	}

	@Override
	public int getUDPInSocketTimeout() {
		return config.getInt("jhub1online.agent.udp.input.socket_timeout_ms", 1999);
	}

	@Override
	public int getInputSamplesQueueSize() {
		return config.getInt("jhub1online.agent.input.queue_size", 999);
	}

	@Override
	public int getOutputSamplesQueueSize() {
		return config.getInt("jhub1online.agent.output.queue_size", 999);
	}


	@Override
	public int getIncomingIQQueueSize() {
		return config.getInt("jhub1online.agent.incoming_iq.queue_size", 999);
	}

	@Override
	public int getOutgoingIQQueueSize() {
		return config.getInt("jhub1online.agent.outgoing_iq.queue_size", 999);
	}
	
	@Override
	public boolean isThreadFileInEnabled() {
		return config.getBoolean("jhub1online.agent.thread.file_in.enabled", true);
	}

	@Override
	public boolean isThreadFileOutEnabled() {
		return config.getBoolean("jhub1online.agent.thread.file_out.enabled", true);
	}

	@Override
	public boolean isThreadUDPInEnabled() {
		return config.getBoolean("jhub1online.agent.thread.udp_in.enabled", true);
	}

	@Override
	public boolean isThreadUDPOutEnabled() {
		return config.getBoolean("jhub1online.agent.thread.udp_out.enabled", true);
	}

	@Override
	public boolean isThreadXMPPEnabled() {
		return config.getBoolean("jhub1online.agent.thread.xmpp.enabled", true);
	}

	@Override
	public boolean isThreadDataMorpherEnabled() {
		return config.getBoolean("jhub1online.agent.thread.datamorpher.enabled", true);
	}

	@Override
	public boolean isOutputChannelUDPDefault() {
		return config.getBoolean("config.channels.outputconfig.udp.default", false);
	}

	@Override
	public boolean isOutputChannelFileDefault() {
		return config.getBoolean("config.channels.outputconfig.file.default", true);
	}

	@Override
	public boolean isDefaultOutputChannelAllowed() {
		return config.getBoolean("config.channels.default_output_channel", true);
	}

	@Override
	public boolean isThreadTestQLoopbackEnabled() {
		return config.getBoolean("jhub1online.agent.thread.queue_loopback.enabled", false);
	}

	@Override
	public int getDataMorpherProcessingFrequency() {
		return config.getInt("jhub1online.agent.thread.datamorpher.processing_window_ms", 500);
	}

	@Override
	public long getXMPPPingInterval() {
		return config.getLong("jhub1online.agent.xmpp.ping_interval_ms", 15000);
	}

	@Override
	public int getXMPPMissingPingsAllowed() {
		return config.getInt("jhub1online.agent.xmpp.pings_lost_before_restart", 4);
	}

}
