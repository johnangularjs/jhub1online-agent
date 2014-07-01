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

package org.jhub1.system.stringatomizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

public class Base64Atomizer implements Atomizable {

	private int splitLength = 100;

	public Base64Atomizer() {

	}

	public Base64Atomizer(int splitLength) {
		this.splitLength = splitLength;
	}

	public static String encodeBase64(String payload) {
		byte[] encodedBytes = Base64.encodeBase64(payload.getBytes());
		return new String(encodedBytes);
	}
	
	public static String decodeBase64(String payload) {
		byte[] decodedBytes = Base64.decodeBase64(payload.getBytes());
		return new String(decodedBytes);
	}
	
	@Override
	public List<String> atomize(String payload) {
		byte[] encodedBytes = Base64.encodeBase64(payload.getBytes());
		List<String> list = splitArray(encodedBytes, splitLength);
		return list;
	}

	@Override
	public String deAtomize(List<String> atomized) {
		StringBuilder sb = new StringBuilder();
		for (String piece : atomized) {
			sb.append(piece);
		}
		byte[] decodedBytes = Base64.decodeBase64(sb.toString());
		return new String(decodedBytes);
	}

	private List<String> splitArray(byte[] array, int max) {

		int x = array.length / max;
		if (x * max < array.length)
			x++;

		int lower = 0;
		int upper = 0;

		List<String> list = new ArrayList<>();

		for (int i = 0; i < x; i++) {
			upper += max;
			list.add(new String(Arrays.copyOfRange(array, lower, upper)));
			lower = upper;
		}

		if (upper < array.length - 1) {
			lower = upper;
			upper = array.length;
			list.add(new String(Arrays.copyOfRange(array, lower, upper)));
		}
		return list;
	}
}
