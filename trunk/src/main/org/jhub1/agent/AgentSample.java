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

package org.jhub1.agent;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.jhub1.agent.randoms.Randoms;

public class AgentSample implements Sample {

	private String uid;
	private ChannelType type;
	private String name;
	private String value;
	private Date timestamp;
	private boolean isValid = true;

	public final static String REGEX_SAMPLE_DETAIL = "[^:]+:[^:]+(:[^:]*)?";
	public final static String REGEX_SAMPLE_OVERALL = "[^:]+:[^:]+(:[^:]*)?";
	public final static String REGEX_NAME = "[a-zA-Z][a-zA-Z0-9_-]*";
	public final static String REGEX_TIMESTAMP = "[0-9]{10,13}";
	
	private static final int SAMPLE_NAME_LENGTH = 10;
	
	public AgentSample(ChannelType type, String message, String uid) {
		this(type, message);
		this.uid = uid;
	}
	
	public AgentSample(ChannelType type, String message) {
		this.type = type;
		String messageTrimed = StringUtils.trim(message);
		if (isMessageValid(message)) {
			String[] parts = messageTrimed.split("\\:");
			if (parts.length == 3) {
				this.timestamp = parseDate(parts[2]);
			} else {
				this.timestamp = parseDate("");
			}
			this.name = parseName(parts[0]);
			this.value = parseValue(parts[1]);
		} else {
			this.name = "";
			this.value = "";
			this.timestamp = null;
		}
	}

/*	Matcher matcher = Pattern.compile(IQs.NAMESPACE_REGEX).matcher(epi);
	if (matcher.find()) {
		assertEquals("jhub1:agent:sample", matcher.group(1));
	} else {
		fail("The name space doesn't match");
	}*/
	
	private boolean isMessageValid(String message) {
		if (message.matches(REGEX_SAMPLE_OVERALL)) {
			return true;
		}
		isValid = false;
		return false;
	}

	private String parseName(String name) {
		if (StringUtils.isNotBlank(name)) {
			String trimmedName = StringUtils.trim(name);
			if (trimmedName.matches(REGEX_NAME)) {
				return name;
			}
		}
		return "unknown";
	}

	private String parseValue(String value) {
		if (StringUtils.isNotBlank(value)) {
			return StringUtils.trim(value);
		} else {
			return "null";
		}
	}

	private Date parseDate(String date) {
		if (StringUtils.isNotBlank(date)) {
			String trimmedDate = StringUtils.trim(date);
			if (trimmedDate.matches(REGEX_TIMESTAMP)) {
				return new Date(Long.parseLong(trimmedDate));
			}
		}
		return new Date();
	}

	public static String generateRandomSample() {
		//sampleName:134:32423333325
		StringBuilder sb = new StringBuilder();
		sb.append(Randoms.getInstance().generateSampleName(SAMPLE_NAME_LENGTH)).append(":");
		sb.append(Randoms.getInstance().generateSampleValue()).append(":");
		sb.append(new Date().getTime());
		return sb.toString();		
	}
	
	@Override
	public boolean isValid() {
		return isValid;
	}

	@Override
	public ChannelType getChannel() {
		return type;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public Date getDate() {
		return timestamp;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(AgentSample.class.getSimpleName()).append(": ");
		sb.append("Type: ").append(type).append(" ");
		sb.append("Name: ").append(name).append(" ");
		sb.append("Value: ").append(value).append(" ");
		sb.append("Date: ").append(timestamp);
		return sb.toString();
	}

	private String weakHashCode() {
		StringBuilder sb = new StringBuilder();
		Date date = new Date();
		sb.append(toString()).append(timestamp.getTime()).append(date.getTime());
		return sb.toString();
	}

	@Override
	public String getUID() {
		if (null == uid) {
			if (isValid) {
				uid = Randoms.getInstance().generateStrongHash(weakHashCode());
			} else {
				uid = "0";
			}
		}
		return uid;
	}
}
