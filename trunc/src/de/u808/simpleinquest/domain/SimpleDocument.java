package de.u808.simpleinquest.domain;

import java.io.File;

import org.apache.lucene.document.Document;

import de.u808.simpleinquest.indexer.Indexer;

public class SimpleDocument {
	
	private Document document;
	
	private float score;
	
	public SimpleDocument(Document document, float score){
		this.document = document;
		this.score = score;
	}
	
	public String getPath(){
		if(document != null){
			return document.get(Indexer.PATH_FIELD_NAME);
		}
		else {
			return null;
		}
	}
	
	public String getId(){
		if(document != null){
			return document.get(Indexer.ID_FIELD_NAME);
		}
		else {
			return null;
		}
	}
	
	public String getFileName(){
		if(document != null){
			String path = document.get(Indexer.PATH_FIELD_NAME);
			int pos = path.lastIndexOf(File.separator);
			if(pos != -1 && pos < path.length()-1){
				return path.substring(pos+1);
			}
		}
		return null;
	}
	
	public String getLastModified(){
		if(document != null){
			return document.get(Indexer.MODIFIED_FIELD_NAME);
		}
		else {
			return null;
		}
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

}
