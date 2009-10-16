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
