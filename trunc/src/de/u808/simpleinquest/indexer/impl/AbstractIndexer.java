package de.u808.simpleinquest.indexer.impl;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.lucene.document.Document;

import de.u808.simpleinquest.indexer.Indexer;


public abstract class AbstractIndexer implements Indexer{
	
	protected Document document;
	
	protected File file;
	
	public Document indexFile(File file) throws FileNotFoundException{
		this.file = file;
		this.document = FileDocument.Document(file);
		this.setContentsFild(file);
		return document;
	}
	
	public abstract void setContentsFild(File file);

}
