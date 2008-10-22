package de.u808.simpleinquest.service;

import java.io.File;

public interface MimeIconRegistry {
	
	public String getMimeIcon(String filename);
	
	public String getMimeIcon(File file);

}
