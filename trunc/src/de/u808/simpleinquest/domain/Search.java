package de.u808.simpleinquest.domain;

import org.apache.lucene.search.Hits;

public class Search {
	
	private String searchString;
	
	private Hits hits;

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

}
