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
			log.error("Can�t create MimetypesFileTypeMap. Custom MimeType-File not found", e);
		}
	}

	public String getMimeType(String fileName) {
		//TODO use magic bits
		String mimeType = mimetypesFileTypeMap.getContentType(fileName);
		return mimeType;
	}

	public String getMimeType(File file) {
		//TODO use magic bits
		String mimeType = mimetypesFileTypeMap.getContentType(file);
		return mimeType;
	}

	public String getFileExtension(File file) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getFileExtension(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

}
