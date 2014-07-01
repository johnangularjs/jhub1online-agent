/**
 * Copyright 2013 SoftCognito.org
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
package test.org.jhub1.system.stringatomizer;
import org.jhub1.system.stringatomizer.Base64Atomizer;

import junit.framework.TestCase;


public class Base64AtomizerTest extends TestCase  {
	Base64Atomizer atomizer;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		atomizer = new Base64Atomizer(5);
	}

	public void testApprovedEqualNumber() {
		assertEquals("jhub1mobile03@comm.unicat", atomizer.deAtomize(atomizer.atomize("jhub1mobile03@comm.unicat")));
	}
	
	public void testApprovedOneLessNumber() {
		assertEquals("jhub1mobile03@comm.unica", atomizer.deAtomize(atomizer.atomize("jhub1mobile03@comm.unica")));
	}
	
	public void testApprovedOneMoreNumber() {
		assertEquals("jhub1mobile03@comm.unicaXX", atomizer.deAtomize(atomizer.atomize("jhub1mobile03@comm.unicaXX")));
	}
	
	public void testApprovedOneChar() {
		assertEquals("j", atomizer.deAtomize(atomizer.atomize("j")));
	}
	
	public void testApprovedFiveChars() {
		assertEquals("qazws", atomizer.deAtomize(atomizer.atomize("qazws")));
	}
	
	public void testApprovedSixChars() {
		assertEquals("qazwse", atomizer.deAtomize(atomizer.atomize("qazwse")));
	}
	
	public void testMasiveContent() {
		assertEquals("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\"><head><meta charset=\"UTF-8\" /><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" /><meta name=\"author\" content=\"Commons Documentation Team\" /><meta name=\"Date-Revision-yyyymmdd\" content=\"20140212\" />	 <meta http-equiv=\"Content-Language\" content=\"en\" /><title>Codec - Download Apache Commons Codec</title><link rel=\"stylesheet\" href=\"./css/bootstrap.min.css\" type=\"text/css\" />		<link rel=\"stylesheet\" href=\"./css/site.css\" type=\"text/css\" /><link rel=\"stylesheet\" href=\"./css/print.css\" media=\"print\" />		<script type=\"text/javascript\" src=\"./js/jquery.min.js\"></script><script type=\"text/javascript\" src=\"./js/bootstrap.min.js\"></script>	<script type=\"text/javascript\" src=\"./js/prettify.min.js\"></script><script type=\"text/javascript\" src=\"./js/site.js\"></script>	 <link rel=\"stylesheet\" type=\"text/css\" media=\"all\" href=\"./css/prettify.css\"/><script src=\"./js/prettify.js\" type=\"text/javascript\"></script><script type=\"text/javascript\">window.onload=function() {prettyPrint(); }</script> </head>	<body class=\"composite\"> <a href=\"http://commons.apache.org/\" id=\"bannerLeft\" title=\"Apache Commons logo\"> <img class=\"logo-left\" src=\"./images/commons-logo.png\" alt=\"Apache Commons logo\"/> </a> <a href=\"index.html\" id=\"bannerRight\"> <img class=\"logo-right\" src=\"images/logo.png\" alt=\"Apache Commons Codec\"/> </a><div class=\"clear\"></div> <div class=\"navbar\"><div class=\"navbar-inner\"><div class=\"container-fluid\"><a class=\"brand\" href=\"http://commons.apache.org/proper/commons-codec/\">Apache Commons Codec &trade;</a><ul class=\"nav\">", atomizer.deAtomize(atomizer.atomize("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\"><head><meta charset=\"UTF-8\" /><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" /><meta name=\"author\" content=\"Commons Documentation Team\" /><meta name=\"Date-Revision-yyyymmdd\" content=\"20140212\" />	 <meta http-equiv=\"Content-Language\" content=\"en\" /><title>Codec - Download Apache Commons Codec</title><link rel=\"stylesheet\" href=\"./css/bootstrap.min.css\" type=\"text/css\" />		<link rel=\"stylesheet\" href=\"./css/site.css\" type=\"text/css\" /><link rel=\"stylesheet\" href=\"./css/print.css\" media=\"print\" />		<script type=\"text/javascript\" src=\"./js/jquery.min.js\"></script><script type=\"text/javascript\" src=\"./js/bootstrap.min.js\"></script>	<script type=\"text/javascript\" src=\"./js/prettify.min.js\"></script><script type=\"text/javascript\" src=\"./js/site.js\"></script>	 <link rel=\"stylesheet\" type=\"text/css\" media=\"all\" href=\"./css/prettify.css\"/><script src=\"./js/prettify.js\" type=\"text/javascript\"></script><script type=\"text/javascript\">window.onload=function() {prettyPrint(); }</script> </head>	<body class=\"composite\"> <a href=\"http://commons.apache.org/\" id=\"bannerLeft\" title=\"Apache Commons logo\"> <img class=\"logo-left\" src=\"./images/commons-logo.png\" alt=\"Apache Commons logo\"/> </a> <a href=\"index.html\" id=\"bannerRight\"> <img class=\"logo-right\" src=\"images/logo.png\" alt=\"Apache Commons Codec\"/> </a><div class=\"clear\"></div> <div class=\"navbar\"><div class=\"navbar-inner\"><div class=\"container-fluid\"><a class=\"brand\" href=\"http://commons.apache.org/proper/commons-codec/\">Apache Commons Codec &trade;</a><ul class=\"nav\">")));	
	}
}
