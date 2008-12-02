package de.u808.simpleinquest.config;

import java.util.Map;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;

public class DirectoryConfiguration {
	
	public DirectoryConfiguration(){
		
	}
	
	public DirectoryConfiguration(String path){
		this.path = path;
	}

	@Element
	private String path;
	
	@ElementMap(entry="systemFetchPrefixUrl", key="system", attribute=true, inline=true, required=false)
	private Map<String, String> systemFetchPrefixUrlMap;
	
	@Element(required=false)
	private boolean blockDirectDownload;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Map<String, String> getSystemFetchPrefixUrlMap() {
		return systemFetchPrefixUrlMap;
	}

	public void setSystemFetchPrefixUrlMap(Map<String, String> systemFetchPrefixUrlMap) {
		this.systemFetchPrefixUrlMap = systemFetchPrefixUrlMap;
	}

	public boolean isBlockDirectDownload() {
		return blockDirectDownload;
	}

	public void setBlockDirectDownload(boolean blockDirectDownload) {
		this.blockDirectDownload = blockDirectDownload;
	}
	
}
