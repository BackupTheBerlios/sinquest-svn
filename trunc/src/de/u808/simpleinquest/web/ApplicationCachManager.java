package de.u808.simpleinquest.web;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class ApplicationCachManager {
	
	CacheManager manager = null;
	
	public ApplicationCachManager (){
		this.manager = CacheManager.create();
	}

	public CacheManager getManager() {
		return manager;
	}

	public void setManager(CacheManager manager) {
		this.manager = manager;
	}

	public void addCache(Cache cache){
		this.manager.addCache(cache);
	}
	
	public Cache getCache(String cacheName){
		return this.manager.getCache(cacheName);
	}
}
