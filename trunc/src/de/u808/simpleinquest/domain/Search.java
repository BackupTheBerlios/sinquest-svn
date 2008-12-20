/*
 * Copyright 2008-2009 Andreas Friedel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.u808.simpleinquest.domain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.search.Hits;

public class Search {
	
	private static Log log = LogFactory.getLog(Search.class);
	
	private String searchString;
	
	private Hits hits;
	
	private int pageIndex = 0;
	
	private int resultsPerPage = 15;

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

	public int getResultsPerPage() {
		return resultsPerPage;
	}

	public void setResultsPerPage(int resultsPerPage) {
		this.resultsPerPage = resultsPerPage;
	}

}
