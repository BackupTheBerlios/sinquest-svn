package de.u808.simpleinquest.util;

import java.io.File;

import de.u808.simpleinquest.config.DirectoryConfiguration;

public interface FileProcessor {
	
	public void processFile(File file);
	
	public void setDirectoryConfiguration(DirectoryConfiguration directoryConfiguration);
	
	public void dispose();

}
