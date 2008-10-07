package de.u808.simpleinquest.domain;

import org.apache.lucene.document.Document;

import de.u808.simpleinquest.indexer.Indexer;

public class SimpleDocument {
	
	private Document document;
	
	public SimpleDocument(Document document){
		this.document = document;
	}
	
	public String getPath(){
		if(document != null){
			return document.get(Indexer.PATH_FIELD_NAME);
		}
		else {
			return null;
		}
	}

}
