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

package org.jhub1.agent.thread.data;

public interface Fridge {
	/**
	 * Stores the object in the fridge
	 * 
	 * @param key
	 * @param object
	 */
	void put(String key, Object object);
	
	/**
	 * Removes object from the fridge
	 * 
	 * @param key
	 */
	void throwAway(String key);
	
	/**
	 * Pops the old object from the fridge
	 * 
	 * @return
	 */
	Object drop();

	/**
	 * Removes all the content
	 */
	void clean();
}
