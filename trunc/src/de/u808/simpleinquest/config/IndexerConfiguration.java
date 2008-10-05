package de.u808.simpleinquest.config;

import java.util.Map;

import org.simpleframework.xml.ElementMap;

public class IndexerConfiguration {
	
	@ElementMap(entry="indexerClassName", key="mimeType", attribute=true, inline=true)
	private Map<String, String> mimeTypeIndexerMap;

	public Map<String, String> getMimeTypeIndexerMap() {
		return mimeTypeIndexerMap;
	}

	public void setMimeTypeIndexerMap(Map<String, String> mimeTypeIndexerMap) {
		this.mimeTypeIndexerMap = mimeTypeIndexerMap;
	}
}
