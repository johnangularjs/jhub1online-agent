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

package org.jhub1.agent.run.cli;

import java.util.Date;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.lang.StringUtils;
import org.jhub1.agent.udp.AbstractUDP;
import org.jhub1.agent.udp.UDP;

public class ParamsProcessorSample extends Params {

	private static final String HEADER = "header";
	private static final String FOOTER = "footer";
	private static final String DESCRIPTION = "performs operation on samples";
	private static final String LONG_OPT = "sample";
	private static final String OPT = "s";
	
	private static final String SAMPLE_FILE = "FILE";
	private static final String SAMPLE_UDP = "UDP";

	public ParamsProcessorSample() {
		Option sample = new Option("s", "sample", false, "performs operations on samples");
		//sample.isRequired();
		//sample.setRequired(true);
		addToOptionsReg(sample);
		
		Option udpReceiver = new Option("r", "udpreceiver", false, "udp samples receiver server");
		addToOptionsReg(udpReceiver);
		
		Option sampleProvider = new Option("p", "provide", true, "simulate providing sample of specified type: FILE or UDP");
		sampleProvider.setArgName("type");
		addToOptionsReg(sampleProvider);
		
		Option name = new Option("n", "name", true, "sample name");
		name.setArgName("name");
		addToOptionsReg(name);
		
		Option value = new Option("v", "value", true, "sample value");
		value.setArgName("value");
		addToOptionsReg(value);
		
		Option timestamp = new Option("t", "timestamp", true, "sample date as timestamp");
		timestamp.setArgName("timestamp");
		addToOptionsReg(timestamp);
		
		Option generate = new Option("g", "generate", true, "generate random 'number' of samples; -n -t -v ignored");
		generate.setArgName("number");
		addToOptionsReg(generate);
	}
	
	@Override
	protected Options getOptions() {
		Options optionsSamples = new Options();
		Option s = getOption("s");
		s.setRequired(true);
		optionsSamples.addOption(s);
		optionsSamples.addOption(getOption("n"));
		optionsSamples.addOption(getOption("v"));
		optionsSamples.addOption(getOption("t"));
		optionsSamples.addOption(getOption("g"));
		
		OptionGroup groupSampleMain = new OptionGroup();	
		groupSampleMain.addOption(getOption("r"));
		groupSampleMain.addOption(getOption("p"));
		optionsSamples.addOptionGroup(groupSampleMain);

		return optionsSamples;
	}
	
	@Override
	protected boolean processCommandLine(CommandLine cmd) {
		if (cmd.hasOption("r")) {
			AbstractUDP udp = new UDP(config.getUDPOutPort());
			System.out.println("Waiting for UDP messages...");
			while (true) {
				String message = udp.waitForMessage();
				System.out.println(message);
			}
		} else if (cmd.hasOption("p")) {
			String type = cmd.getOptionValue("p");
			if (StringUtils.isNotBlank(type)) {
				if (type.equalsIgnoreCase(SAMPLE_FILE)) {
					return getRemainingParams(SAMPLE_FILE, cmd);
				} else if (type.equalsIgnoreCase(SAMPLE_UDP)) {
					return getRemainingParams(SAMPLE_UDP, cmd);
				} else {
					return false;
				}
			}
		} else {
			return false;  // this case will never happen as it is checked earlier
		}
		return true; // false for help // true if processed
	}
	
	@Override
	protected String getOpt() {
		return OPT;
	}
	
	@Override
	public String getLongOpt() {
		return LONG_OPT;
	}
	
	@Override
	protected String getDescription() {
		return DESCRIPTION;
	}
	
	@Override
	protected String getHeader() {
		return HEADER;
	}
	
	@Override
	protected String getFooter() {
		return FOOTER;
	}

	private boolean getRemainingParams(String sample, CommandLine cmd) {
		if (cmd.hasOption("g")) {
			String quantity = cmd.getOptionValue("g");
			if (StringUtils.isNotBlank(quantity)) {
				int qty = Integer.parseInt(quantity);
				if (sample.equalsIgnoreCase(SAMPLE_UDP)) {
					sendUDPSample(null, qty);
					return true;
				} else if (sample.equalsIgnoreCase(SAMPLE_FILE)) {
					sendFileSample(null, qty);
					return true;
				} else {
					return false;
				}
			}
		} else if (cmd.hasOption("n") && cmd.hasOption("v")) {
			String name = cmd.getOptionValue("n");
			String value = cmd.getOptionValue("v");
			String timestamp = null;
			if (cmd.hasOption("t")) {
				timestamp = cmd.getOptionValue("t");
			}
			String message = null;
			if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(value)) {
				message = StringUtils.trim(name) + ":"
						+ StringUtils.trim(value);
				if (StringUtils.isNotBlank(timestamp)) {
					message = message + ":" + StringUtils.trim(timestamp);
				} else {
					Date date = new Date();
					message = message + ":" + date.getTime();
				}
				if (sample.equalsIgnoreCase(SAMPLE_UDP)) {
					sendUDPSample(message, 1);
					return true;
				} else if (sample.equalsIgnoreCase(SAMPLE_FILE)) {
					sendFileSample(message, 1);
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	private void sendFileSample(String message, int qty) {
		@SuppressWarnings("unused")
		GenerateFiles gf = new GenerateFiles(message, qty);
	}
}
