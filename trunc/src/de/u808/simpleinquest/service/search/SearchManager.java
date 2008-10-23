package de.u808.simpleinquest.service.search;

import java.io.IOException;

import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Query;

import de.u808.common.GlobalSearchCache;
import de.u808.common.SessionSearchCache;
import de.u808.simpleinquest.indexer.Indexer;

public class SearchManager {

	private IndexSearchBean indexSearchBean;

	// TODO invalidate after IndexSearcher update;
	private SessionSearchCache<String, Hits> searchCach;

	private GlobalSearchCache globalSearchCache;

	public Hits search(String searchString) throws ParseException, IOException{
		Hits hits = null;
		if(StringUtils.isNotEmpty(searchString)){
			if(searchCach.containsKey(searchString)){
				return searchCach.get(searchString);
			}
			else{ 
				Element element = globalSearchCache.getCache().get(searchString);
				if(element != null ){
					hits = (Hits) element.getObjectValue();
					searchCach.put(searchString, hits);
				}
				else{
					// Query query = new QueryParser(Indexer.CONTENT_FIELD_NAME, new
					// StandardAnalyzer()).parse(searchString);
					// search.setHits(indexSearchBean.getIndexSearcher().search(query));
			
					String[] fields = {Indexer.AUTOR_FIELD_NAME, Indexer.CONTENT_FIELD_NAME, Indexer.TITLE_FIELD_NAME};
					Analyzer analyzer = new StandardAnalyzer();
					QueryParser qp = new MultiFieldQueryParser(fields, analyzer);
					qp.setDefaultOperator(QueryParser.Operator.AND);
					Query query = qp.parse(searchString);
					hits = indexSearchBean.getIndexSearcher().search(query);
					searchCach.put(searchString, hits);
					globalSearchCache.getCache().put(new Element(searchString, hits));
				}
			}
		}
		return hits;
	}

	public IndexSearchBean getIndexSearchBean() {
		return indexSearchBean;
	}

	public void setIndexSearchBean(IndexSearchBean indexSearchBean) {
		this.indexSearchBean = indexSearchBean;
	}

	public SessionSearchCache<String, Hits> getSearchCach() {
		return searchCach;
	}

	public void setSearchCach(SessionSearchCache<String, Hits> searchCach) {
		this.searchCach = searchCach;
	}

	public GlobalSearchCache getGlobalSearchCache() {
		return globalSearchCache;
	}

	public void setGlobalSearchCache(GlobalSearchCache globalSearchCache) {
		this.globalSearchCache = globalSearchCache;
	}

}
