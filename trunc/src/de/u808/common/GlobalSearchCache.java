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
