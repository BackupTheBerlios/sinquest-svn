package de.u808.simpleinquest.indexer.impl;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;

import de.u808.simpleinquest.indexer.Indexer;


public abstract class AbstractIndexer implements Indexer{
	
	protected Document document;
	
	protected File file;
	
	private static Log log = LogFactory.getLog(AbstractIndexer.class);
	
	public Document indexFile(File file) throws FileNotFoundException{
		this.file = file;
		this.document = FileDocument.Document(file);
		try{
			this.setContentsFild(file);
		} catch (Exception e) {
			log.error("Exception while fetch content of file: " + file.toString(), e);
			return null;
		}
		return document;
	}
	
	public abstract void setContentsFild(File file) throws Exception;

}
