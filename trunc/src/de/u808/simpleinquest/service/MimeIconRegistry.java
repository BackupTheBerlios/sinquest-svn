package de.u808.simpleinquest.service;

import java.io.File;

import de.u808.simpleinquest.web.ConfigBeanResource;

public interface MimeIconRegistry {
	
	public String getMimeIcon(String filename);
	
	public String getMimeIcon(File file);
	
	public void setConfigBeanResource(ConfigBeanResource configBeanResource);

}
