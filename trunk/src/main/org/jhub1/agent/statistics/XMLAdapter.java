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

package org.jhub1.agent.statistics;

import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.DateTime;

public class XMLAdapter extends OutputAdapter {

	@Override
	public String formatRegistry(Map<String, Object> register) {
		StringBuilder sb = new StringBuilder();
		if (!register.isEmpty()) {
			sb.append("<registry>").append("<statistics>").append("<items>");
			for (Entry<String, Object> entry : register.entrySet()) {
				sb.append("<item name='").append(entry.getKey()).append("'>");
				Object value = entry.getValue();
				if (value instanceof DateTime) {
					sb.append(((DateTime) value).getMillis());
				} else if (value instanceof Long) {
					sb.append(((Long) value).longValue());
				} else if (value instanceof String) {
					sb.append(((String) value).toString());
				} else {
					sb.append(value.toString());
				}
				sb.append("</item>");
			}
			sb.append("</items>").append("</statistics>").append("</registry>");
		}
		return sb.toString();
	}

}
