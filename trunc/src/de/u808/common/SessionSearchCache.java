package de.u808.common;

import java.util.LinkedHashMap;
import java.util.Map;

public class SessionSearchCache<K,V> extends LinkedHashMap<K,V> implements Map<K,V> {
	
	private int maxEntries = 100;
	
	public SessionSearchCache() {
		super(101, .75F, true);
	}

	public int getMaxEntries() {
		return maxEntries;
	}

	public void setMaxEntries(int maxEntries) {
		this.maxEntries = maxEntries;
	}

	@Override
	protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
		return size() > maxEntries;
     }

	public void invalidate(){
		this.clear();
	}	
}
