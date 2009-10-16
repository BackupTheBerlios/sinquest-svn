/*
 * Copyright 2008-2009 Andreas Friedel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.u808.common;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The SessionSerachCache holds up to 10 search results per session.
 * 
 * @author Andreas Friedel
 * 
 * @param <K>
 * @param <V>
 */
public class SessionSearchCache<K, V> extends LinkedHashMap<K, V> implements
		Map<K, V> {

	private int maxEntries = 10;

	/**
	 * Calls the super constructor with the arguments (11, .75F, true).
	 */
	public SessionSearchCache() {
		super(11, .75F, true);
	}

	/**
	 * Get the maximum number of entries.
	 * 
	 * @return 10
	 */
	public int getMaxEntries() {
		return maxEntries;
	}

	/**
	 * Setter for the maximum number of entries.
	 * 
	 * @param maxEntries
	 */
	public void setMaxEntries(int maxEntries) {
		this.maxEntries = maxEntries;
	}

	@Override
	/*
	 * Override the super method to allow the map to grow up to 10 entries and
	 * then delete the eldest entry each time a new entry is added.
	 */
	protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		return size() > maxEntries;
	}

	/**
	 * Clears the cache.
	 */
	public void invalidate() {
		this.clear();
	}
}
