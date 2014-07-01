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

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.joda.time.Weeks;

public class SmartDateDisplay {

	public static String showHowLong(DateTime rDate) {
		StringBuilder sb = new StringBuilder();
		DateTime dt = new DateTime();
		Weeks w = Weeks.weeksBetween(rDate, dt);
		Days d = Days.daysBetween(rDate, dt);
		Hours h = Hours.hoursBetween(rDate, dt);
		Minutes m = Minutes.minutesBetween(rDate, dt);
		Seconds s = Seconds.secondsBetween(rDate, dt);
		if(s.getSeconds() != 0) {
			sb.append(s.getSeconds() % 60).append("s");
		} 
		if (m.getMinutes() != 0) {
			sb.insert(0, (m.getMinutes() % 60) + "m ");
		}
		if (h.getHours() != 0) {
			sb.insert(0, (h.getHours() % 24)+ "h ");
		}
		if (d.getDays() != 0) {
			sb.insert(0, (d.getDays() % 7) + "d ");
		}
		if (w.getWeeks() != 0) {
			sb.insert(0, w.getWeeks() + "w ");
		}
		return sb.toString();
	}

}
