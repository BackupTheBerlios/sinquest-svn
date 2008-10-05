package de.u808.simpleinquest.service.search;

import org.apache.lucene.search.IndexSearcher;

public class IndexSearchBean {
	
	private IndexSearcher indexSearcher;

	public IndexSearcher getIndexSearcher(){
		return this.indexSearcher;
	}

	public void setIndexSearcher(IndexSearcher indexSearcher) {
		this.indexSearcher = indexSearcher;
	}

}
