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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.jhub1.agent.AgentSample;
import org.jhub1.agent.configuration.AgentProperties;
import org.jhub1.agent.configuration.PropertiesImpl;

public class GenerateFiles {

	public GenerateFiles(String message, int quantity) {
		AgentProperties config = null;
		// construct configuration object
		try {
			config = new PropertiesImpl();
		} catch (ConfigurationException e1) {
			e1.printStackTrace();
			System.exit(0);
		}

		while (quantity != 0) {

			String fileName = config.getFileInputDirectory() + "/generated"
					+ quantity + ".jhub1";

			try {
				// Assume default encoding.
				FileWriter fileWriter = new FileWriter(fileName);

				// Always wrap FileWriter in BufferedWriter.
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

				// Note that write() does not automatically
				// append a newline character.
				if(null == message) {
					bufferedWriter.write(AgentSample.generateRandomSample());	
				} else {
					bufferedWriter.write(message);	
				}
				// Always close files.
				bufferedWriter.close();
			} catch (IOException ex) {
				System.out.println("Error writing to file '" + fileName + "'");
			}
			quantity--;
		}
	}

}
