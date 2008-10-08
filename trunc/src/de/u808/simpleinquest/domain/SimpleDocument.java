package de.u808.simpleinquest.domain;

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

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

}
