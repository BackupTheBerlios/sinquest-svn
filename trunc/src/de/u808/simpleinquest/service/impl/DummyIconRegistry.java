package de.u808.simpleinquest.service.impl;

import java.io.File;

import de.u808.simpleinquest.service.MimeIconRegistry;
import de.u808.simpleinquest.web.ConfigBeanResource;

public class DummyIconRegistry implements MimeIconRegistry {
	
	private ConfigBeanResource configBeanResource;
	
	private final static String dummyFile = "/SimpleInquest/img/mime/22/text-generic.gif"; 

	@Override
	public String getMimeIcon(String filename) {
		return dummyFile;
	}

	@Override
	public String getMimeIcon(File file) {
		return dummyFile;
	}

	public void setConfigBeanResource(ConfigBeanResource configBeanResource) {
		this.configBeanResource = configBeanResource;
	}

	
}
