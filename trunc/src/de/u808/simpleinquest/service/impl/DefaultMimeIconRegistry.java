package de.u808.simpleinquest.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.lang.StringUtils;

import de.u808.simpleinquest.service.MimeIconRegistry;
import de.u808.simpleinquest.service.MimeTypeRegistry;
import de.u808.simpleinquest.web.ConfigBeanResource;

public class DefaultMimeIconRegistry implements MimeIconRegistry{
	
	private ConfigBeanResource configBeanResource;
	
	private MimeTypeRegistry mimeTypeRegistry;

	public String getMimeIcon(String filename) {		
		String mimeType = this.mimeTypeRegistry.getMimeType(filename);
		return getIcon(mimeType);
	}

	public String getMimeIcon(File file) {
		String mimeType = this.mimeTypeRegistry.getMimeType(file);
		return getIcon(mimeType);
	}
	
	private String getSuitableType(String[] types){
		Map iconMap = configBeanResource.getSystemConfig().getConfiguration().getMimeIconMap().getMap();
		for(String type : types){
			if (iconMap.containsKey(type)){
				return type;
			}
		}
		return null;
	}
	
	private String getIcon(String mimeType){
		String singleType = null; 
		if(StringUtils.isNotEmpty(mimeType) && mimeType.contains(",")){
			String[] types = mimeType.split(",");
			singleType = this.getSuitableType(types);
		}
		else{
			singleType = mimeType;
		}
		return configBeanResource.getSystemConfig().getConfiguration().getMimeIconMap().getIconURI(singleType);
	}

	public ConfigBeanResource getConfigBeanResource() {
		return configBeanResource;
	}

	public void setConfigBeanResource(ConfigBeanResource configBeanResource) {
		this.configBeanResource = configBeanResource;
	}

	public void setMimeTypeRegistry(MimeTypeRegistry mimeTypeRegistry) {
		this.mimeTypeRegistry = mimeTypeRegistry;
	}

}
