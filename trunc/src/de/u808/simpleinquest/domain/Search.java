package de.u808.simpleinquest.domain;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.search.Hits;

public class Search {
	
	private static Log log = LogFactory.getLog(Search.class);
	
	private String searchString;
	
	private Hits hits;
	
	private int first = 0;
	
	private int last = 49;

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public Hits getHits() {
		return hits;
	}

	public void setHits(Hits hits) {
		this.hits = hits;
	}
	
	public long getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public int getLast() {
		return last;
	}
	
	public int getLastDisplayed() {
		if(last > this.hits.length()){
			return this.hits.length();
		}
		else{
			return this.last;
		}
	}

	public void setLast(int last) {
		this.last = last;
	}

	public int getHitsCount(){
		if(hits == null){
			return 0;
		}
		else{
			return hits.length();
		}
	}

	public Collection<SimpleDocument> getCurrentResults(){
		LinkedList<SimpleDocument> currentResults = new LinkedList<SimpleDocument>();
		long max = this.first;
		if(hits != null){
			if (hits.length() > this.last){
				max = this.last;
			}
			else{
				max = this.hits.length();
			}
		}
		for(int i=this.first; i<max; i++){
			try {
				currentResults.add(new SimpleDocument(hits.doc(i), this.hits.score(i)));
			} catch (CorruptIndexException e) {
				log.error("Error fetching result", e);
			} catch (IOException e) {
				log.error("Error fetching result", e);
			}
		}
		return currentResults;
	}
}
