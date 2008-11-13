package de.u808.simpleinquest.service;

import java.io.File;

public interface MimeTypeRegistry {
	
	public String getMimeType(String fileName);
	
	public String getMimeType(File file);
	
	public String getFileExtension(File file);
	
	public String getFileExtension(String fileName);

}
