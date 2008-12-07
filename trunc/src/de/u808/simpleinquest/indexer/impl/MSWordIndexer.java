package de.u808.simpleinquest.indexer.impl;

import java.io.File;
import java.io.StringReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Field;

import de.u808.simpleinquest.indexer.Indexer;

public class MSWordIndexer extends AbstractIndexer {
	
	Log log = LogFactory.getLog(this.getClass());
	
	private WordContentHandler wordContentHandler = new WordContentHandler();
	
	@Override
	public void setContentsFild(File file) throws IndexerException {
		try {
			this.document.add(new Field(Indexer.CONTENT_FIELD_NAME, new StringReader(wordContentHandler.extractContent(file))));
			wordContentHandler.dispose();
		} catch (Exception e) {
			throw new IndexerException(e.getMessage(), e);
		}
	}
}
