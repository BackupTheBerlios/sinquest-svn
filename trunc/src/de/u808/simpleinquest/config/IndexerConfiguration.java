package de.u808.simpleinquest.config;

import java.util.Map;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;

public class IndexerConfiguration {
	
	@Element
	private int indexSearchRefreshCount;
	
	@ElementMap(entry="indexerClassName", key="mimeType", attribute=true, inline=true)
	private Map<String, String> mimeTypeIndexerMap;

	public Map<String, String> getMimeTypeIndexerMap() {
		return mimeTypeIndexerMap;
	}

	public void setMimeTypeIndexerMap(Map<String, String> mimeTypeIndexerMap) {
		this.mimeTypeIndexerMap = mimeTypeIndexerMap;
	}

	public int getIndexSearchRefreshCount() {
		return indexSearchRefreshCount;
	}

	public void setIndexSearchRefreshCount(int indexSearchRefreshCount) {
		this.indexSearchRefreshCount = indexSearchRefreshCount;
	}
	
}
