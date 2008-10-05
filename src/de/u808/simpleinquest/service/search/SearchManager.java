package de.u808.simpleinquest.service.search;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;

import de.u808.simpleinquest.domain.Search;
import de.u808.simpleinquest.indexer.Indexer;

public class SearchManager {
	
	private IndexSearchBean indexSearchBean;
	
	public Search search(String searchString) throws ParseException, IOException{
		Search search = new Search();
		search.setSearchString(searchString);
		Query query = new QueryParser(Indexer.CONTENT_FIELD_NAME, new StandardAnalyzer()).parse(searchString);
		search.setHits(indexSearchBean.getIndexSearcher().search(query));
		return search;
	}

	public IndexSearchBean getIndexSearchBean() {
		return indexSearchBean;
	}

	public void setIndexSearchBean(IndexSearchBean indexSearchBean) {
		this.indexSearchBean = indexSearchBean;
	}

}
