package de.u808.simpleinquest.service.impl;

import java.io.File;

import de.u808.simpleinquest.service.MimeIconRegistry;
import de.u808.simpleinquest.web.ConfigBeanResource;
import eu.medsea.util.MimeUtil;

public class DefaultMimeIconRegistry implements MimeIconRegistry{
	
	private ConfigBeanResource configBeanResource;

	public String getMimeIcon(String filename) {
		String mimeType = MimeUtil.getMimeType(filename);
		return getIcon(mimeType);
	}

	public String getMimeIcon(File file) {
		String mimeType = MimeUtil.getMimeType(file);
		return getIcon(mimeType);
	}
	
	private String getIcon(String mimeType){
		return configBeanResource.getSystemConfig().getConfiguration().getMimeIconMap().getIconURI(mimeType);
	}

	public ConfigBeanResource getConfigBeanResource() {
		return configBeanResource;
	}

	public void setConfigBeanResource(ConfigBeanResource configBeanResource) {
		this.configBeanResource = configBeanResource;
	}

}
