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

import net.sf.ehcache.Cache;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
import de.u808.simpleinquest.web.ApplicationCachManager;

public class GlobalSearchCache {
	
	private ApplicationCachManager cachManager;
	
	private Cache cache = null;
	
	public GlobalSearchCache(){

	}
	
	private void setupCach(){
		Cache memoryOnlyCache = new Cache("globalSearchCache", 100, MemoryStoreEvictionPolicy.LFU, false, null, true, 900, 900, false, 900, null);
		cachManager.addCache(memoryOnlyCache);
		cache = cachManager.getCache("globalSearchCache");
	}

	public ApplicationCachManager getCachManager() {
		return cachManager;
	}

	public void setCachManager(ApplicationCachManager cachManager) {
		this.cachManager = cachManager;
	}

	public Cache getCache() {
		if(this.cache == null){
			this.setupCach();
		}
		return this.cache;
	}
	
	public void invalidate(){
		if(this.cache != null){
			this.cache.removeAll();
		}
	}

}
