package de.u808.simpleinquest.domain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.search.Hits;

public class Search {
	
	private static Log log = LogFactory.getLog(Search.class);
	
	private String searchString;
	
	private Hits hits;
	
	private int pageIndex = 0;
	
	private int resultsPerPage = 10;

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

	public int getHitsCount(){
		if(hits == null){
			return 0;
		}
		else{
			return hits.length();
		}
	}
	
	public int getPageCount(){
		int pages = this.getHitsCount() / this.resultsPerPage;
		if(this.getHitsCount() % this.resultsPerPage != 0){
			pages = pages + 1;
		}
		return pages;
	}
	
	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public PageModel getCurrentPage(){
		int first = pageIndex * resultsPerPage;
		if(first < this.hits.length()){
			return new PageModel(this.hits, first, this.resultsPerPage);
		}
		else return null;
	}

}
