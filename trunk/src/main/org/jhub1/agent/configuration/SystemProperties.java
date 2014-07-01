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

package org.jhub1.agent.configuration;

public interface SystemProperties {

	/**
	 * Operating system name
	 */
	String getSysOsName();
	
	/**
	 * Operating system architecture
	 */
	String getSysOsArch();
	
	/**
	 * Operating system version
	 */
	String getSysOsVersion();

	/**
	 *   File separator ("/" on UNIX)
	 */
	String getSysFileSeparator();
	
	/**
	 *  Path separator (":" on UNIX)
	 */
	String getSysPathSeparator();
	
	/**
	 *  Line separator ("\n" on UNIX)
	 */
	String getSysLineSeparator();
	
	/**
	 *  User's account name
	 */
	String getSysUserName();
	
	/**
	 * 	User's home directory
	 */
	String getSysUserHome();
	
	/**
	 * 	 User's current working directory
	 */
	String getSysUserDir();

	/**
	 * 	Java Runtime Environment version
	 */
	String getSysJavaVersion();
	
	/**
	 * 	Java Runtime Environment vendor
	 */
	String getSysJavaVendor();
	
	/**
	 * 	Java vendor URL
	 */
	String getSysJavaVendorURL();

	/**
	 * 	Java installation directory
	 */
	String getSysJavaHome();
	
	/**
	 * 	Java class format version number
	 */
	String getSysJavaClassVersion();
	
	/**
	 * 	Java class path
	 */
	String getSysJavaClassPath();
	
	/**
	 * 	List of paths to search when loading libraries
	 */
	String getSysJavaLibraryPath();
	
	/**
	 * 	Default temp file path
	 */
	String getSysJavaIOTempDir();
	
	/**
	 * 	Name of JIT compiler to use
	 */
	String getSysJavaCompiler();
	
	/**
	 * 	Path of extension directory or directories
	 */
	String getSysJavaExtDirs();
/*	
	java.vm.specification.version	Java Virtual Machine specification version
	java.vm.specification.vendor	Java Virtual Machine specification vendor
	java.vm.specification.name	Java Virtual Machine specification name
	java.vm.version	Java Virtual Machine implementation version
	java.vm.vendor	Java Virtual Machine implementation vendor
	java.vm.name	Java Virtual Machine implementation name
	java.specification.version	Java Runtime Environment specification version
	java.specification.vendor	Java Runtime Environment specification vendor
	java.specification.name	Java Runtime Environment specification name
*/
}
