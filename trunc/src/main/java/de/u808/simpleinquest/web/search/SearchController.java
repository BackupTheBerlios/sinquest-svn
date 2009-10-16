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

package de.u808.simpleinquest.web.search;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.queryParser.ParseException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import de.u808.simpleinquest.domain.SearchResult;
import de.u808.simpleinquest.service.search.SearchManager;

public class SearchController extends SimpleFormController {

	private static final String PAGE_INDEX = "pageIndex";

	private static final String SEARCH_STRING = "searchString";

	private SearchManager searchManager;
	
	private static Log log = LogFactory.getLog(SearchController.class);

	protected boolean isFormSubmission(HttpServletRequest request) {
		if (StringUtils.isNotEmpty(request.getParameter(SEARCH_STRING)) || StringUtils.isNotEmpty(request.getParameter(PAGE_INDEX)) ){
			return true;
		}
		return false;
	}

	protected ModelAndView onSubmit(Object command) {
		SearchResult search = (SearchResult) command;		
		SearchResult searchResult = new SearchResult();
		searchResult.setSearchString(search.getSearchString());
		searchResult.setSearchPerformed(true);
		try {
			searchResult = searchManager.search(search.getSearchString());
			if(searchResult != null && searchResult.getPageIndex() != search.getPageIndex()){
				searchResult.setPageIndex(search.getPageIndex());
				searchResult.getErrors().clear();
			}			
		} catch (ParseException e) {
			log.error("Can�t execute search", e);
			if(e.getMessage() != null && e.getMessage().contains("not allowed as first")){
				searchResult.addErrorMessage("search.ParseException", "error.search.invalidFirstCharacter");
			}
		} catch (IOException e) {
			log.error("Can�t execute search", e);
		} 
		return new ModelAndView("searchForm", "search", searchResult);
	}

	public SearchManager getSearchManager() {
		return searchManager;
	}

	public void setSearchManager(SearchManager searchManager) {
		this.searchManager = searchManager;
	}

}
