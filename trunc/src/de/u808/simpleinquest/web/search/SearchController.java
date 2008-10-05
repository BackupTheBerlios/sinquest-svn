package de.u808.simpleinquest.web.search;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import de.u808.simpleinquest.domain.Search;
import de.u808.simpleinquest.service.search.SearchManager;

public class SearchController extends SimpleFormController{
	
	private SearchManager searchManager;

	public SearchController(){
		setCommandClass(Search.class);
		setCommandName("appointment");
	}	

	@Override
	protected Object formBackingObject(HttpServletRequest request)
		throws Exception {
		return searchManager.search(request.getParameter("searchString"));
	}


	protected ModelAndView onSubmit(Object command){
		Search search = (Search) command;
		return new ModelAndView(getSuccessView(), "searchResults", search.getHits());
	}

	public SearchManager getSearchManager() {
		return searchManager;
	}

	public void setSearchManager(SearchManager searchManager) {
		this.searchManager = searchManager;
	}
	
}
