package de.u808.simpleinquest.web.search;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.queryParser.ParseException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import de.u808.simpleinquest.domain.Search;
import de.u808.simpleinquest.service.search.SearchManager;

public class SearchController extends SimpleFormController {

	private SearchManager searchManager;
	
	private static Log log = LogFactory.getLog(SearchController.class);

	public SearchController() {
		setCommandClass(Search.class);
		setCommandName("search");
	}

	protected boolean isFormSubmission(HttpServletRequest request) {
		if (request.getParameter("searchString") != null || request.getParameter("pageIndex") != null ){
			return true;
		}
		return false;
	}

	protected ModelAndView onSubmit(Object command) {
		Search search = (Search) command;
		try {
			search.setHits(searchManager.search(search.getSearchString()));
		} catch (ParseException e) {
			log.error("Can´t execute search", e);
		} catch (IOException e) {
			log.error("Can´t execute search", e);
		}
		return new ModelAndView("searchForm", "search", search);
	}

	public SearchManager getSearchManager() {
		return searchManager;
	}

	public void setSearchManager(SearchManager searchManager) {
		this.searchManager = searchManager;
	}

}
