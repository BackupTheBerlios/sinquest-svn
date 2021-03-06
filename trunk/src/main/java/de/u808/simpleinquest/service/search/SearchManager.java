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

package de.u808.simpleinquest.service.search;

import java.io.IOException;
import java.util.LinkedHashMap;

import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Query;

import de.u808.common.GlobalSearchCache;
import de.u808.common.SessionSearchCache;
import de.u808.simpleinquest.domain.SearchResult;
import de.u808.simpleinquest.indexer.Indexer;

public class SearchManager {
	
	protected final static Log log = LogFactory.getLog(SearchManager.class);

	private IndexSearchBean indexSearchBean;

	private SessionSearchCache<String, SearchResult> searchCach;

	private GlobalSearchCache globalSearchCache;

	public SearchResult search(String searchString) throws ParseException, IOException{
		Hits hits = null;
		SearchResult searchResult = new SearchResult();
		searchResult.setSearchString(searchString);
		searchResult.setSearchPerformed(true);
		if(StringUtils.isNotEmpty(searchString)){
			if(searchCach.containsKey(searchString)){
				return searchCach.get(searchString);
			}
			else{ 
				Element element = globalSearchCache.getCache().get(searchString);
				if(element != null ){
					hits = (Hits) element.getObjectValue();										
					searchResult.setHits(hits);
					searchCach.put(searchString, searchResult);
				}
				else{
					// Query query = new QueryParser(Indexer.CONTENT_FIELD_NAME, new
					// StandardAnalyzer()).parse(searchString);
					// search.setHits(indexSearchBean.getIndexSearcher().search(query));
					
					//TEST
					if(indexSearchBean.getIndexSearcher() != null){
						BooleanQuery.setMaxClauseCount(Integer.MAX_VALUE);
			
						String[] fields = {Indexer.AUTOR_FIELD_NAME, Indexer.CONTENT_FIELD_NAME, Indexer.TITLE_FIELD_NAME};
						Analyzer analyzer = new StandardAnalyzer();
						QueryParser qp = new MultiFieldQueryParser(fields, analyzer);
						qp.setDefaultOperator(QueryParser.Operator.AND);
						Query query = qp.parse(searchString);
						hits = indexSearchBean.getIndexSearcher().search(query);						
						searchResult.setHits(hits);
						searchCach.put(searchString, searchResult);
						globalSearchCache.getCache().put(new Element(searchString, hits));
					}
					else{
						//TODO check lang
						log.warn("Index dos not exist! Returning null!");
						//TODO display Info
					}
				}
			}
		}
		return searchResult;
	}

	public IndexSearchBean getIndexSearchBean() {
		return indexSearchBean;
	}

	public void setIndexSearchBean(IndexSearchBean indexSearchBean) {
		this.indexSearchBean = indexSearchBean;
	}

	public SessionSearchCache<String, SearchResult> getSearchCach() {
		return searchCach;
	}

	public void setSearchCach(SessionSearchCache<String, SearchResult> searchCach) {
		this.searchCach = searchCach;
	}

	public GlobalSearchCache getGlobalSearchCache() {
		return globalSearchCache;
	}

	public void setGlobalSearchCache(GlobalSearchCache globalSearchCache) {
		this.globalSearchCache = globalSearchCache;
	}

}
