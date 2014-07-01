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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ParamsMain {

	private Options options;
	private CommandLineParser parser;
	private CommandLine cmdMain;

	private List<Params> paramsProcessors;
	
	// private static Logger log =
	// LoggerFactory.getLogger(ParamsProcessor.class);

	public ParamsMain() {
		paramsProcessors = new ArrayList<Params>();
		paramsProcessors.add(new ParamsProcessorIQ());
		paramsProcessors.add(new ParamsProcessorSample());
	}

	private Options getMainOptions() {
		OptionGroup groupMain = new OptionGroup();

		for (Params paramsProcessor : paramsProcessors) {
			groupMain.addOption(paramsProcessor.getOptionsSignature());
		}
		groupMain.addOption(new Option("h", "help", false, "print this message"));
		options = new Options();
		options.addOptionGroup(groupMain);
		return options;
	}

	private Options getGlobalOptions() {
		Options options = getMainOptions();
		for (Params paramsProcessor : paramsProcessors) {
			Iterator<Entry<String, Option>> it = paramsProcessor.getOptionsReg().entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Option> pairs = (Map.Entry<String, Option>) it.next();
				options.addOption((Option) pairs.getValue());
			}
		}
		return options;
	}

	private void initParser(String[] args) throws ParseException {
		parser = new GnuParser();
		cmdMain = parser.parse(getGlobalOptions(), args);
		if (cmdMain.hasOption("help")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter
					.printHelp(
							80,
							"agent",
							"This is command line tool that helps simulating / provoking usual agent operations. Here are the params:",
							getMainOptions(),
							"Using one of those you will get more detailed help relevant to each subsection.",
							true);
			System.exit(0);
		} else {
			for (Params paramsProcessor : paramsProcessors) {
				if (cmdMain.hasOption(paramsProcessor.getLongOpt())) {
					boolean result = paramsProcessor.process(args);
					if (false == result) {
						paramsProcessor.printHelp();
					}
					System.exit(0);
				}
			}
		}
	}

	public void parse(String[] args) {
		try {
			initParser(args);
		} catch (ParseException e) {
			System.out.println("\n************************");
			System.out.println("The parameter(s) not recognized or other parsing problem!");
			System.out.println("************************");
			e.printStackTrace();
		}
/*		if (cmdMain.hasOption("p")) {
			String provide = cmdMain.getOptionValue("p");
			if (StringUtils.isNotBlank(provide)) {
				if (provide.equalsIgnoreCase(SAMPLE_FILE)) {
					getRemainingParams(SAMPLE_FILE, args);
				} else if (provide.equalsIgnoreCase(SAMPLE_UDP)) {
					getRemainingParams(SAMPLE_UDP, args);
				}
			}
		}*/
	}

}
