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
		this.traversalMode = traversalMode;
		if (!directory.isDirectory()){
			throw new InvalidArgumentException("File " + directory.getPath() + " is not a directory");
		}
		else if(directory == null){
			throw new InvalidArgumentException("Directory must not be null");
		}
		this.rootDir = directory;
		this.fileProcessor = fileProcessor;
	}
	
	private void processRootDirectory(File dir){
		String[] files = dir.list();
		for(String fileName : files){
			File file = new File(dir, fileName);
			this.processFile(file);
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
