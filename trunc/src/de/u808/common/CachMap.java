package de.u808.common;

import java.util.LinkedHashMap;
import java.util.Map;

public class CachMap<K,V> extends LinkedHashMap<K,V> implements Map<K,V> {
	
	private int maxEntries = -1;

	public CachMap(){
		
	}
	
	public CachMap(int maxEntries) {
		this.maxEntries = maxEntries;
	}

	public int getMaxEntries() {
		return maxEntries;
	}

	public void setMaxEntries(int maxEntries) {
		this.maxEntries = maxEntries;
	}

	@Override
	protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
		if(maxEntries != -1){
			return size() > maxEntries;
		}
		else return false;
     }

	
}
