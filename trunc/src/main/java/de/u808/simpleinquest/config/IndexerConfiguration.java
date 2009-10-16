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
