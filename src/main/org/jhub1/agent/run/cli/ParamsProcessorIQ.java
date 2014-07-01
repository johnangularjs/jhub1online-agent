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

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.lang.StringUtils;

public class ParamsProcessorIQ extends Params {
	private static final String HEADER = "header";
	private static final String FOOTER = "footer";
	private static final String DESCRIPTION = "performs operations on IQ (XMPP) input and output queues";
	private static final String LONG_OPT = "iq";
	private static final String OPT = "q";

	public ParamsProcessorIQ() {
		Option mainiq = new Option("q", "iq", false,
				"performs operations on IQ (XMPP)");
		//mainiq.setRequired(true);
		addToOptionsReg(mainiq);

		Option offer = new Option("of", "offer", true,
				"operation type - adding IQ to the queue");
		offer.setArgName("XML");
		addToOptionsReg(offer);

		Option pool = new Option("po", "pool", false,
				"operation type - removing IQ from the queue - content output to log");
		addToOptionsReg(pool);

		Option in = new Option("in", false,
				"target, operation performed on incoming queue");
		addToOptionsReg(in);

		Option out = new Option("out", false,
				"target, operation performed on outgoing queue");
		addToOptionsReg(out);
	}

	@Override
	protected Options getOptions() {
		Options optionsIQ = new Options();
		Option q = getOption("q");
		q.setRequired(true);
		optionsIQ.addOption(q);
		OptionGroup groupIQoperation = new OptionGroup();
		groupIQoperation.addOption(getOption("of"));
		groupIQoperation.addOption(getOption("po"));
		optionsIQ.addOptionGroup(groupIQoperation);
		OptionGroup groupIQtarget = new OptionGroup();
		groupIQtarget.addOption(getOption("in"));
		groupIQtarget.addOption(getOption("out"));
		optionsIQ.addOptionGroup(groupIQtarget);
		return optionsIQ;
	}

	@Override
	protected boolean processCommandLine(CommandLine cmd) {
		 if (cmd.hasOption("of")) {
			String provide = cmd.getOptionValue("of");
			if (StringUtils.isNotBlank(provide)) {
				sendUDPSample(provide, 1);
				return true;
				// java -jar JHUB1OnlineAgentDev.jar -piq
				// "<iq type='result' to='ayak/admin' from='zyak/admin' id='1001'><query xmlns='jhub1:agent:sample'><items type='SAMPLE'><item uid='uid00001' name='sampleNo1' timestamp='0123452342345' channel='FILE'>33</item><item uid='uid00002' name='sampleNo2' timestamp='0123452342555' channel='FILE'>34</item></items></query></iq>"
			}
		}
		return false;
	}

//java -jar JHUB1OnlineAgentDev.jar -q -in -of "<iq type='result' to='ayak/admin' from='zyak/admin' id='1001'></iq>"
//<queue direction='in' operation='offer'><iq type='result' to='ayak/admin' from='zyak/admin' id='1001'></iq></queue>
//<queue direction='out' operation='pool'/>	
	
	
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

}
