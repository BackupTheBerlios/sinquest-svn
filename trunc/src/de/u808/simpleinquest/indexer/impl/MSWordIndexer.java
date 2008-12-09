package de.u808.simpleinquest.indexer.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.poi.hwpf.extractor.WordExtractor;

import de.u808.simpleinquest.indexer.Indexer;

public class MSWordIndexer extends AbstractIndexer {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	private WordExtractor wordExtractor;
	
	@Override
	public void setContentsFild(File file) throws IndexerException {
		try {
			this.document.add(new Field(Indexer.CONTENT_FIELD_NAME, this.extractContent(file), Store.NO, Index.TOKENIZED));
		} catch (Exception e) {
			throw new IndexerException(e.getMessage(), e);
		}
	}
	
	public String extractContent(File file) throws Exception {
		try {
			//WordTextExtractorFactory fab = new WordTextExtractorFactory();
			wordExtractor = new WordExtractor(new FileInputStream(file));
			//TextExtractor extractor = fab.textExtractor();
			return wordExtractor.getText();			
		} catch (Exception e) {
			throw new IndexerException(e.getMessage(), e);
		} finally {
			if(wordExtractor != null){
				wordExtractor = null;
			}
		}
	}

	public void dispose() {
		wordExtractor = null;
	}
}
