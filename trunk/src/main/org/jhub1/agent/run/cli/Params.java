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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.configuration.ConfigurationException;
import org.jhub1.agent.AgentSample;
import org.jhub1.agent.configuration.AgentProperties;
import org.jhub1.agent.configuration.PropertiesImpl;
import org.jhub1.agent.udp.AbstractUDP;
import org.jhub1.agent.udp.UDP;

public abstract class Params {

	private static final String COMMAND_NAME = "Agent";
	private static final int DISPLAY_COLUMNS = 80;
	protected  Map<String, Option> optionsReg = new HashMap<String, Option>();
	
	protected AgentProperties config;
	
	Option getOptionsSignature() {
		Option option = new Option(getOpt(), getLongOpt(), false, getDescription());
		return option;
	}
	
	void printHelp() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(DISPLAY_COLUMNS, COMMAND_NAME, getHeader(), getOptions(), getFooter(), true);	
	}
	
	boolean process(String[] args) {
		
		try {
			config = new PropertiesImpl();
		} catch (ConfigurationException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
		
		GnuParser parser = new GnuParser();
		CommandLine cmd = null;
		try {
			cmd = parser.parse(getOptions(), args);
		} catch (Exception e) {
			System.out.println("\n************************");
			System.out.println("The parameter(s) not recognized or other parsing problem!");
			System.out.println("************************");
			return false;
		}
		
		return processCommandLine(cmd);
	}
	
	protected abstract boolean processCommandLine(CommandLine cmd);
	
	protected void addToOptionsReg(Option option) {
		optionsReg.put(option.getOpt(), option);
	}
	
	public Map<String, Option> getOptionsReg() {
		return optionsReg;
	}
	
	public Option getOption(String opt) {
		if(optionsReg.containsKey(opt)) {
			return optionsReg.get(opt);
		} 
		return null;
	}
	
	protected abstract String getHeader();
	protected abstract String getFooter();
	protected abstract Options getOptions();
	protected abstract String getOpt();
	public abstract String getLongOpt();
	protected abstract String getDescription();
	
	
	/**
	 * Sends local UDP sample
	 * 
	 * @param message
	 *            -is the sample values/IQ xml
	 * @param qty
	 *            -number of randomly generated samples if > 1 message is
	 *            ignored
	 */
	protected void sendUDPSample(String message, int qty) {
		System.out.println("Sending udp messages to port: "
				+ config.getUDPInPort() + " IP: " + config.getUDPOutIP());
		AbstractUDP udp = new UDP(config.getUDPInPort(), config.getUDPOutIP());
		if (qty > 1) {
			for (int i = 0; i < qty; i++) {
				if (null == message) {
					String genMessage = AgentSample.generateRandomSample();
					udp.sendMessage(genMessage);
					int count = i + 1;
					System.out.println("Sent UDP message (" + count + "): "
							+ genMessage);
				} else {
					udp.sendMessage(message);
					int count = i + 1;
					System.out.println("Sent UDP message (" + count + "): "
							+ message);
				}
			}
		} else {
			udp.sendMessage(message);
			System.out.println("Sent UDP message (1): " + message);
		}
	}
	
}
