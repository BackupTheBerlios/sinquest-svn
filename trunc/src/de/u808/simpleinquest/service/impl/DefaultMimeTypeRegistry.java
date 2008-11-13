package de.u808.simpleinquest.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.u808.simpleinquest.service.MimeTypeRegistry;

public class DefaultMimeTypeRegistry implements MimeTypeRegistry {
	
	private MimetypesFileTypeMap mimetypesFileTypeMap;
	
	private static Log log = LogFactory.getLog(DefaultMimeTypeRegistry.class);
	
	public DefaultMimeTypeRegistry(File customMimeTypes){
		try {
			if(!customMimeTypes.exists()){
				File parent = new File("WebContent");
				File metaInfDir = new File(parent, "META-INF");
				customMimeTypes = new File(metaInfDir, customMimeTypes.getName());
			}
			mimetypesFileTypeMap = new MimetypesFileTypeMap(new FileInputStream(customMimeTypes));
		} catch (FileNotFoundException e) {
			log.error("Can´t create MimetypesFileTypeMap. Custom MimeType-File not found", e);
		}
	}

	@Override
	public String getMimeType(String fileName) {
		//TODO use magic bits
		String mimeType = mimetypesFileTypeMap.getContentType(fileName);
		return mimeType;
	}

	@Override
	public String getMimeType(File file) {
		//TODO use magic bits
		String mimeType = mimetypesFileTypeMap.getContentType(file);
		return mimeType;
	}

	@Override
	public String getFileExtension(File file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFileExtension(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

}
