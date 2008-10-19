package de.u808.simpleinquest.service.search;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Query;

import de.u808.common.CachMap;
import de.u808.simpleinquest.domain.Search;
import de.u808.simpleinquest.indexer.Indexer;

public class SearchManager {
	
	private IndexSearchBean indexSearchBean;
	
	/**
	 * The searchCach caches the result of 1000 queries;
	 */
	//TODO invalidate after IndexSearcher update;
	private CachMap<String, Hits> searchCach = new CachMap<String, Hits>(1000);
	
	public Hits search(String searchString) throws ParseException, IOException{
		Hits hits = null;
		if(StringUtils.isNotEmpty(searchString)){
			if(searchCach.containsKey(searchString)){
				return searchCach.get(searchString);
			}
			else{
				//Query query = new QueryParser(Indexer.CONTENT_FIELD_NAME, new StandardAnalyzer()).parse(searchString);
				//search.setHits(indexSearchBean.getIndexSearcher().search(query));
			
				String[] fields = {Indexer.AUTOR_FIELD_NAME, Indexer.CONTENT_FIELD_NAME, Indexer.TITLE_FIELD_NAME};
				Analyzer analyzer = new StandardAnalyzer();
				QueryParser qp = new MultiFieldQueryParser(fields, analyzer);
				qp.setDefaultOperator(QueryParser.Operator.AND);
				Query query = qp.parse(searchString);
				hits = indexSearchBean.getIndexSearcher().search(query);
				searchCach.put(searchString, hits);
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

}
