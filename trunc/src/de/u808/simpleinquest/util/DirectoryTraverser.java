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

package de.u808.simpleinquest.util;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DirectoryTraverser {
	
	private File rootDir;
	
	private FileProcessor fileProcessor;
	
	public enum TraversalMode {JustFiles, FilesAndDirectories, JustDirectories};
	
	private TraversalMode traversalMode;
	
	private static Log log = LogFactory.getLog(DirectoryTraverser.class);
	
	public DirectoryTraverser(File directory, FileProcessor fileProcessor, TraversalMode traversalMode) throws InvalidArgumentException{
		if (!directory.isDirectory()){
			throw new InvalidArgumentException("File " + directory.getPath() + " is not a directory");
		}
		else if(directory == null){
			throw new InvalidArgumentException("Directory must not be null");
		}
		this.rootDir = directory;
		this.fileProcessor = fileProcessor;
		this.traversalMode = traversalMode;
	}
	
	private void processRootDirectory(File dir){
		String[] files = dir.list();
		if(files != null){
			for(String fileName : files){
				File file = new File(dir, fileName);
				this.processFile(file);
			}
		}
		else {
			String rootDDir = (dir == null ? "Null" : dir.getAbsolutePath());
			log.info("No files fount to process in directory: " + rootDDir);
		}
	}
	
	private void processFile(File file){
		if(!file.isHidden()){
			if(file.isDirectory()){
				if(traversalMode.equals(TraversalMode.FilesAndDirectories) || traversalMode.equals(TraversalMode.JustDirectories)){
					this.fileProcessor.processFile(file);
				}
				if(!file.equals(this.rootDir)){
					processRootDirectory(file);
				}
			}
			else if(traversalMode.equals(TraversalMode.JustFiles) || traversalMode.equals(TraversalMode.FilesAndDirectories)){
				this.fileProcessor.processFile(file);
			}
		}
		else{
			log.info("Ignoring hidden file: " + file.getPath());
		}
	}
	
	public void startTraversal(){
		this.processFile(rootDir);
		this.processRootDirectory(rootDir);
		this.fileProcessor.dispose();
	}

}
