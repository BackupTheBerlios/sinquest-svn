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

import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;
@Root
public class MimeIconMap {
	
	public static final String UNKNOWN_MIME_TYPE = "application/unknown";
	
	@ElementMap(entry="property", key="mimeType", attribute=true, inline=true)
	private Map<String, String> map;

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	
	public String getIconURI(String mimeType){
		if(map != null){
			if(map.containsKey(mimeType)){
				return map.get(mimeType);
			}
			else{
				return map.get(UNKNOWN_MIME_TYPE);
			}
		}
		else{
			return null;
		}
	}

}
