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
