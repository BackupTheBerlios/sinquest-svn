package de.u808.simpleinquest.config;

import java.util.Map;

import org.simpleframework.xml.ElementMap;

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
