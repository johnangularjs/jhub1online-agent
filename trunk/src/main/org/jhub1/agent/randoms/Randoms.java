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

package org.jhub1.agent.randoms;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.apache.commons.codec.binary.Hex;


public class Randoms {
	
	private static Randoms instance;
	private static SecureRandom random;
	private static char[] chars = "abcdefghijklmnopqrstuvwxyzQWERTYUIOPLKJHGFDSAZXCVBNM".toCharArray();
	private static Random simpleRandom = new Random();
	
	private static final int RESOURCEID_LENGTH = 10; 
	
	private Randoms() {
		random = new SecureRandom();
	}
	
	public String generateSampleName(int length) {	
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
		    char c = chars[simpleRandom.nextInt(chars.length)];
		    sb.append(c);
		}	
		return sb.toString();	
	}
	
	public String generateResourceID() {	
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < RESOURCEID_LENGTH; i++) {
		    char c = chars[simpleRandom.nextInt(chars.length)];
		    sb.append(c);
		}	
		return sb.toString();	
	}
	
	public String generateSampleValue() {
		return new BigInteger(5, random).toString();		
	}
	
	public String generateAlphanumericString(int bits) {
		return new BigInteger(bits, random).toString(32);	
	}
	
	public static Randoms getInstance() {
		if(null == instance) {
			instance = new Randoms();
		}
		return instance;
	}
	
	public String generateStrongHash(String str) {
		StringBuilder sb = new StringBuilder();
		sb.append(str).append(generateSampleName(20));
		return sha1(sb.toString());
	}
	
	public String sha1(String str) {
		MessageDigest cript = null;
		try {
			cript = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        cript.reset();
        try {
			cript.update(str.getBytes("utf8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(Hex.encodeHex(cript.digest()));
	}
}
