package de.u808.simpleinquest.web.search;

import de.u808.simpleinquest.domain.Search;

public class SearchViewModel {
	
	private Search search;
	
	public SearchViewModel(){
		
	}
	
	public SearchViewModel(Search search){
		this.search = search;
	}

	public Search getSearch() {
		return search;
	}

	public void setSearch(Search search) {
		this.search = search;
	}

}
